package com.assignment.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.entity.AddRequest;
import com.assignment.entity.Fee;
import com.assignment.entity.Transaction;
import com.assignment.entity.TransferRequest;
import com.assignment.entity.User;
import com.assignment.entity.Wallet;
import com.assignment.repository.TransactionRepository;
import com.assignment.repository.UserRepository;
import com.assignment.repository.WalletRepository;
import com.assignment.util.ComputeCharges;
import com.assignment.util.GenerateTransID;

import static com.assignment.util.Constants.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class TransactionService {
	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	WalletRepository walletRepository;

	public Fee computeCharges(double transactionAmount) {
		return ComputeCharges.calculate(transactionAmount);
	}

	public Transaction transerMoney(TransferRequest req) throws Exception {
		Transaction trans = new Transaction();
		List<User> dataExists = userRepository.findByIdIn(Arrays.asList(req.getFromWalletId(), req.getToWalletId()));

		if (dataExists.size() == 2) {
			Fee feeDetails = computeCharges(req.getTransactionAmount());
			updateWallets(req, feeDetails);

		} else {
			throw new Exception("Please check the wallet ids");
		}

		System.out.println(" TransferRequest " + req.toString());
		return trans;

	}

	private Transaction updateWallets(TransferRequest req, Fee feeDetails) {
		Wallet sourceWallet, destWallet;
		Transaction sourceResponse = null, destResponse;
		sourceWallet = walletRepository.findByWalletId(req.getFromWalletId());
		destWallet = walletRepository.findByWalletId(req.getToWalletId());
		double netSourceBalance = sourceWallet.getBalance() - req.getTransactionAmount();
		System.out.println(" source feeDetails.getNetAmount() : "+ feeDetails.getNetAmount());
		String transId = GenerateTransID.generateTransId(req.getFromWalletId(), req.getToWalletId());
		if (netSourceBalance >= 0) {
			sourceResponse = updateSourceWallet(sourceWallet, netSourceBalance, transId);
			destResponse = updateDestWallet(destWallet, feeDetails.getNetAmount(), transId);
		}

		return sourceResponse;
	}

	private Transaction updateDestWallet(Wallet destWallet, double transAmount, String transId) {
		Fee destFee = computeCharges(transAmount);
		double netDestBalance = destWallet.getBalance() + destFee.getNetAmount();
		Transaction updateTransaction = new Transaction(transId, destWallet.getWalletId(), transAmount,
				destFee.getNetAmount(), "CREDIT", netDestBalance, new Date(), "N");
		AddRequest updateUserWAllet = new AddRequest(netDestBalance, destWallet.getWalletId());
		updateWallet(updateUserWAllet);
		return transactionRepository.save(updateTransaction);
	}

	private Transaction updateSourceWallet(Wallet sourceWallet, double netBalance, String transId) {
		Transaction updateTransaction = new Transaction(transId, sourceWallet.getWalletId(), sourceWallet.getBalance(),
				netBalance, "DEBIT", netBalance, new Date(), "N");
		AddRequest updateUserWAllet = new AddRequest(netBalance, sourceWallet.getWalletId());
		updateSourceWallet(updateUserWAllet);
		return transactionRepository.save(updateTransaction);
	}

	private void updateSourceWallet(AddRequest sourceWallet) {
		Wallet walletExists = findWalletById(sourceWallet.getToWalletId());
		if (walletExists != null) {
			walletExists.setBalance(sourceWallet.getTransactionAmount());
			walletRepository.save(walletExists);
		}
	}

	public Transaction addMoney(@Valid AddRequest request) throws Exception {
		Transaction trans = new Transaction();

		User dataExists = findUserById(request.getToWalletId());
		if (dataExists == null) {
			throw new Exception("Invalid Wallet Id");
		} else {
			Transaction transExists = findTransExists(dataExists.getId());
			trans.setWalletId(request.getToWalletId());
			trans.setTransType(CREDIT);
			trans.setTransAmount(request.getTransactionAmount());
			trans.setNetAmount(request.getTransactionAmount()); // change logic after getting clarification
			trans.setDate(new Date());
			trans.setTransId(GenerateTransID.generateTransId(request.getToWalletId(), request.getToWalletId()));
			trans.setReversed("N");
			if (transExists == null) {
				trans.setBalance(request.getTransactionAmount());
			} else {
				trans.setBalance(fetchNewBalance(transExists.getBalance(), request.getTransactionAmount()));
			}
		}

		updateWallet(request);
		return transactionRepository.save(trans);
	}

	private void updateWallet(@Valid AddRequest request) {
		Wallet walletExists = findWalletById(request.getToWalletId());
		if (walletExists != null) {
			double updatedBalance = walletExists.getBalance() + request.getTransactionAmount();
			walletExists.setBalance(updatedBalance);
			walletRepository.save(walletExists);
		}
	}

	private Transaction findTransExists(long walletId) {
		return transactionRepository.findTopByWalletIdOrderByDateDesc(walletId);
	}

	private double fetchNewBalance(double existingBalance, double amount) {
		return existingBalance + amount;
	}

	public User findUserById(long walletId) {
		return userRepository.findById(walletId).orElse(null);
	}

	public Wallet findWalletById(long walletId) {
		return walletRepository.findById(walletId).orElse(null);
	}

	public List<Transaction> fetchAllTransactions(@Valid long walletId) {
		return transactionRepository.findAllByWalletIdOrderByDateDesc(walletId);
	}

}

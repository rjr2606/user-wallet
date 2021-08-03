package com.assignment.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.entity.AddRequest;
import com.assignment.entity.Fee;
import com.assignment.entity.Transaction;
import com.assignment.entity.TransferRequest;
import com.assignment.entity.Wallet;
import com.assignment.repository.TransactionRepository;
import com.assignment.repository.WalletRepository;
import static com.assignment.util.Constants.*;

import java.util.Date;
import java.util.List;

@Service
public class TransactionService {

	@Autowired
	TransactionRepository transactionRepository;
	
	@Autowired
	WalletRepository walletRepository;

	public Fee computeCharges(double transactionAmount) {
		double commission, charge;
		Fee feeDetails = new Fee();

		commission = transactionAmount * 0.05;
		charge = transactionAmount * 0.2;

		feeDetails.setCharge(charge);
		feeDetails.setCommission(commission);
		feeDetails.setTransactionAmount(transactionAmount);

		return feeDetails;
	}
	
	/* ----------------------------------------------------------------------------------------*/
	
	public Transaction transerMoney(TransferRequest req) {
		Transaction trans = new Transaction();
		Fee feeDetails = computeCharges(req.getTransactionAmount());

		fetchBalance(req.getFromWalletId());

		System.out.println(" TransferRequest " + req.toString());
		return trans;

	}

	private double fetchBalance(long fromWalletId) {
		return fromWalletId;
	}

	/* ----------------------------------------------------------------------------------------*/

	public Transaction addMoney(@Valid AddRequest request) {
		Transaction trans = new Transaction();

		Wallet dataExists = findWalletById(request.getToWalletId());
		if (dataExists == null) {
			return null;
		} else {
			Transaction transExists = findTransExists(dataExists.getId());
			trans.setWalletId(request.getToWalletId());
			trans.setTransType(CREDIT);
			trans.setTransAmount(request.getTransactionAmount());
			trans.setNetAmount(request.getTransactionAmount()); // change logic after getting clarification
			trans.setDate(new Date());
			if(transExists == null) {
				trans.setBalance(request.getTransactionAmount());
			}else {
				trans.setBalance(fetchNewBalance(transExists.getBalance(),request.getTransactionAmount()));
			}
		}
		return transactionRepository.save(trans);
	}

	private Transaction findTransExists(long walletId) {
		return transactionRepository.findTopByWalletIdOrderByDateDesc(walletId);
	}

	private double fetchNewBalance(double existingBalance, double amount) {
		return existingBalance + amount;
	}

	public Wallet findWalletById(long walletId) {
		return walletRepository.findById(walletId).orElse(null);
	}

	public List<Transaction> fetchAllTransactions(@Valid long walletId) {
		return transactionRepository.findAllByWalletIdOrderByDateDesc(walletId);
	}

}

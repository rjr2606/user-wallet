package com.assignment.serviceImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.entity.AddRequest;
import com.assignment.entity.Fee;
import com.assignment.entity.Transaction;
import com.assignment.entity.TransactionDetails;
import com.assignment.entity.TransferRequest;
import com.assignment.entity.User;
import com.assignment.entity.Wallet;
import com.assignment.exception.ResourceNotFoundException;
import com.assignment.repository.TransactionRepository;
import com.assignment.repository.UserRepository;
import com.assignment.repository.WalletRepository;
import com.assignment.service.TransactionService;
import com.assignment.util.ComputeCharges;
import com.assignment.util.GenerateTransID;

import static com.assignment.util.Constants.*;

@Service
public class TransactionServiceImpl implements TransactionService {
	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	WalletRepository walletRepository;

	Transaction recieverTrans, senderTrans;
	  private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);
	public Fee computeCharges(double transactionAmount) {
		return ComputeCharges.senderFee(transactionAmount);
	}

	@Transactional
	public TransactionDetails transerMoney(TransferRequest req) throws Exception {
		Optional<Wallet> sourceWallet, destWallet;
		TransactionDetails transResponse = null;
		sourceWallet = walletRepository.findByWalletId(req.getFromWalletId());
		destWallet = walletRepository.findByWalletId(req.getToWalletId());
		if (sourceWallet.isPresent() && destWallet.isPresent()) {
			Fee senderFee = ComputeCharges.senderFee(req.getTransactionAmount());
			LOGGER.info(" senderFee {} ",senderFee.toString());
			Fee recieverFee = ComputeCharges.recieverFee(req.getTransactionAmount());
			LOGGER.info(" recieverFee {} ",recieverFee.toString());
			double netSourceBalance = sourceWallet.get().getBalance() - senderFee.getNetAmount();
			LOGGER.info(" netSourceBalance {} ",netSourceBalance);
			if (netSourceBalance >= 0) {
				String transId = GenerateTransID.generateTransId(req.getFromWalletId(), req.getToWalletId());
				updateSourceWallet(sourceWallet.get(), senderFee, netSourceBalance, transId, req);
				updateDestWallet(destWallet.get(), recieverFee.getNetAmount(), transId, req);
				transResponse = fetchTransactionDetails(transId);
			}
		}
		return transResponse;

	}

	private Transaction updateDestWallet(Wallet destWallet, double netAmount, String transId, TransferRequest req) {
		double netDestBalance = destWallet.getBalance() + netAmount;
		Transaction updateTransaction = new Transaction(transId, destWallet.getWalletId(), req.getTransactionAmount(),
				netAmount, CREDIT, netDestBalance, new Date(), NO);
		AddRequest updateUserWAllet = new AddRequest(netDestBalance, destWallet.getWalletId());
		updateWallet(updateUserWAllet);
		return transactionRepository.save(updateTransaction);
	}

	private Transaction updateSourceWallet(Wallet sourceWallet, Fee senderFee, double netBalance, String transId,
			TransferRequest req) {
		Transaction updateTransaction = new Transaction(transId, sourceWallet.getWalletId(), req.getTransactionAmount(),
				senderFee.getNetAmount(), DEBIT, netBalance, new Date(), NO);
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

	
	@Transactional
	public Transaction addMoney(@Valid AddRequest request) throws Exception {
		Transaction trans = new Transaction();

		User dataExists = findUserById(request.getToWalletId());
		if (dataExists == null) {
			throw new ResourceNotFoundException(INVALID_WALLED_ID);
		} else {
			Transaction transExists = findTransExists(dataExists.getId());
			trans.setWalletId(request.getToWalletId());
			trans.setTransType(CREDIT);
			trans.setTransAmount(request.getTransactionAmount());
			trans.setNetAmount(request.getTransactionAmount()); // change logic after getting clarification
			trans.setDate(new Date());
			trans.setTransId(GenerateTransID.generateTransId(request.getToWalletId(), request.getToWalletId()));
			trans.setReversed(NO);
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
			walletExists.setBalance(request.getTransactionAmount());
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

	@Transactional
	public String reverse(@Valid String transId) throws Exception {

		List<Transaction> transList = transactionRepository.findByTransId(transId);
		if (transList.size() == 2) {
			for (Transaction transObj : transList) {
				if (transObj.getTransType().equals("DEBIT")) {
					senderTrans = transObj;
				} else {
					recieverTrans = transObj;
				}
			}
			String transReverseId = GenerateTransID.generateTransId(recieverTrans.getWalletId(),
					senderTrans.getWalletId());
			reverseDestTrans(recieverTrans, transReverseId);
			reverseSourceTrans(senderTrans, recieverTrans, transReverseId);
		} else {
			throw new ResourceNotFoundException(INVALID_TRANSACTION);
		}
		return REVERSE_SUCCESS;
	}

	private void reverseSourceTrans(Transaction senderTrans, Transaction recieverTrans, String transReverseId) {
		Wallet sourceExists = findWalletById(senderTrans.getWalletId());
		if (sourceExists != null) {
			double updatedBalance = new BigDecimal(senderTrans.getBalance() + recieverTrans.getNetAmount())
					.setScale(2, RoundingMode.DOWN).doubleValue();
			sourceExists.setBalance(updatedBalance);
			walletRepository.save(sourceExists);

			Transaction reverseTrans = new Transaction(transReverseId, senderTrans.getWalletId(),
					recieverTrans.getNetAmount(), recieverTrans.getNetAmount(), CREDIT, updatedBalance, new Date(),
					YES);
			transactionRepository.save(reverseTrans);
		}
	}

	private void reverseDestTrans(Transaction recieverTrans, String transReverseId) {
		Wallet destExists = findWalletById(recieverTrans.getWalletId());
		if (destExists != null) {
			double updatedBalance = new BigDecimal(recieverTrans.getBalance() - recieverTrans.getNetAmount())
					.setScale(2, RoundingMode.DOWN).doubleValue();
			LOGGER.info(" updatedBalance {} ",updatedBalance);
			destExists.setBalance(updatedBalance);
			walletRepository.save(destExists);

			Transaction reverseTrans = new Transaction(transReverseId, recieverTrans.getWalletId(),
					recieverTrans.getNetAmount(), recieverTrans.getNetAmount(), DEBIT, updatedBalance, new Date(), YES);
			transactionRepository.save(reverseTrans);
		}
	}

	public TransactionDetails fetchTransactionDetails(@Valid String transId) {
		List<Transaction> transList = transactionRepository.findByTransId(transId);
		TransactionDetails transDetails = new TransactionDetails();
		Map<String, Transaction> transMap = new HashMap<>();

		for (Transaction transaction : transList) {
			if (transaction.getTransType().equals(DEBIT)) {
				transMap.put(SENDER, transaction);
			} else {
				transMap.put(RECIEVER, transaction);
			}
		}
		transDetails.setTransactionDetails(transMap);
		return transDetails;
	}

}

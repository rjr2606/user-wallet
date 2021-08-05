package com.assignment.service;

import java.util.List;

import javax.validation.Valid;

import com.assignment.entity.AddRequest;
import com.assignment.entity.Fee;
import com.assignment.entity.Transaction;
import com.assignment.entity.TransactionDetails;
import com.assignment.entity.TransferRequest;

public interface TransactionService {

	public TransactionDetails transerMoney(TransferRequest req) throws Exception;
	
	public Transaction addMoney(@Valid AddRequest request) throws Exception;
	
	public Fee computeCharges(double transactionAmount);
	
	public String reverse(@Valid String transId) throws Exception;
	
	public List<Transaction> fetchAllTransactions(@Valid long walletId);
	
	public TransactionDetails fetchTransactionDetails(@Valid String transId);
}

package com.assignment.entity;

import java.util.Map;

public class TransactionDetails {

	private Map<String,Transaction> transactionDetails;

	public Map<String, Transaction> getTransactionDetails() {
		return transactionDetails;
	}

	public void setTransactionDetails(Map<String, Transaction> transactionDetails) {
		this.transactionDetails = transactionDetails;
	}
	
	
}

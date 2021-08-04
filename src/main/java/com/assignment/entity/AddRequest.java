package com.assignment.entity;

import javax.validation.constraints.NotBlank;

public class AddRequest {
	private @NotBlank double transactionAmount;
	private @NotBlank long toWalletId;

	
	
	public AddRequest(@NotBlank double transactionAmount, @NotBlank long toWalletId) {
		super();
		this.transactionAmount = transactionAmount;
		this.toWalletId = toWalletId;
	}

	public double getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public long getToWalletId() {
		return toWalletId;
	}

	public void setToWalletId(long toWalletId) {
		this.toWalletId = toWalletId;
	}

	@Override
	public String toString() {
		return "AddRequest [transactionAmount=" + transactionAmount + ", toWalletId=" + toWalletId + "]";
	}

	
}

package com.assignment.entity;

import javax.validation.constraints.NotBlank;

public class TransferRequest {
	private @NotBlank double transactionAmount;
	private @NotBlank long fromWalletId;
	private @NotBlank long toWalletId;

	public double getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public long getFromWalletId() {
		return fromWalletId;
	}

	public void setFromWalletId(long fromWalletId) {
		this.fromWalletId = fromWalletId;
	}

	public long getToWalletId() {
		return toWalletId;
	}

	public void setToWalletId(long toWalletId) {
		this.toWalletId = toWalletId;
	}

}

package com.assignment.entity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

public class AddRequest {
	
	@Positive(message = "Transfer amount must be positive")
    @Min(value = 1, message = "Amount must be larger than 1")
	@NotBlank (message = "transactionAmount code is mandatory")
	@Pattern(regexp = "{0-9}*")
	private double transactionAmount;
	
	@Min(value = 1, message = "Wallet Id cannot be negative")
	@NotBlank(message = "toWalletId code is mandatory")
	@Pattern(regexp = "{0-9}*")
	private  long toWalletId;

	
	
	public AddRequest(@NotNull double transactionAmount, @NotNull long toWalletId) {
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

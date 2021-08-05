package com.assignment.entity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.springframework.validation.annotation.Validated;

@Validated
public class TransferRequest {
	
	@Positive(message = "Transfer amount must be positive")
    @Min(value = 1, message = "Amount must be larger than 1")
	@NotNull(message = "transactionAmount code is mandatory")
	@Pattern(regexp = "{0-9}*")
	private double transactionAmount;
	
	@Min(value = 1, message = "Wallet Id cannot be negative")
	@NotNull(message = "fromWalletId is mandatory")
	@Pattern(regexp = "{0-9}*")
	private long fromWalletId;
	
	@Min(value = 1, message = "Wallet Id cannot be negative")
	@NotNull(message = "toWalletId is mandatory")
	@Pattern(regexp = "{0-9}*")
	private long toWalletId;

	
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

	@Override
	public String toString() {
		return "TransferRequest [transactionAmount=" + transactionAmount + ", fromWalletId=" + fromWalletId
				+ ", toWalletId=" + toWalletId + "]";
	}

}

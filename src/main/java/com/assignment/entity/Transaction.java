package com.assignment.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "transaction")
public class Transaction {
	private @Id @GeneratedValue long transactionId;

	private @NotBlank long fromWalletId;
	private @NotBlank long toWalletId;
	private @NotBlank long transferAmount;
	private @NotBlank long transactionFee;
	private @NotBlank long commissionFee;
	private @NotBlank long totaldeduction;
	private @NotBlank String type;
	private @NotBlank String action;
	public Transaction() {
	}

	

	public Transaction(long transactionId, @NotBlank long fromWalletId, @NotBlank long toWalletId,
			@NotBlank long transferAmount, @NotBlank long transactionFee, @NotBlank long commissionFee,
			@NotBlank long totaldeduction, @NotBlank String type, @NotBlank String action) {
		this.transactionId = transactionId;
		this.fromWalletId = fromWalletId;
		this.toWalletId = toWalletId;
		this.transferAmount = transferAmount;
		this.transactionFee = transactionFee;
		this.commissionFee = commissionFee;
		this.totaldeduction = totaldeduction;
		this.type = type;
		this.action = action;
	}



	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
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

	public long getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(long transferAmount) {
		this.transferAmount = transferAmount;
	}

	public long getTransactionFee() {
		return transactionFee;
	}

	public void setTransactionFee(long transactionFee) {
		this.transactionFee = transactionFee;
	}

	public long getCommissionFee() {
		return commissionFee;
	}

	public void setCommissionFee(long commissionFee) {
		this.commissionFee = commissionFee;
	}

	public long getTotaldeduction() {
		return totaldeduction;
	}

	public void setTotaldeduction(long totaldeduction) {
		this.totaldeduction = totaldeduction;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}



	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", fromWalletId=" + fromWalletId + ", toWalletId="
				+ toWalletId + ", transferAmount=" + transferAmount + ", transactionFee=" + transactionFee
				+ ", commissionFee=" + commissionFee + ", totaldeduction=" + totaldeduction + ", type=" + type
				+ ", action=" + action + "]";
	}
	
	
}

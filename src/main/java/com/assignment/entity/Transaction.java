package com.assignment.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "transaction")
public class Transaction {

	private @Id @GeneratedValue long id;
	private @NotNull String transId;
	private @NotNull long walletId;
	private @NotNull double transAmount;
	private @NotNull double netAmount;
	private @NotNull String transType;
	private @NotNull double balance;
	private @NotNull Date date;
	private @NotNull String reversed;

	public Transaction() {
	}

	
	
	public Transaction( @NotNull String transId, @NotNull long walletId, @NotNull double transAmount,
			@NotNull double netAmount, @NotNull String transType, @NotNull double balance, @NotNull Date date,
			@NotNull String reversed) {
		this.transId = transId;
		this.walletId = walletId;
		this.transAmount = transAmount;
		this.netAmount = netAmount;
		this.transType = transType;
		this.balance = balance;
		this.date = date;
		this.reversed = reversed;
	}



	public String getTransId() {
		return transId;
	}

	public void setTransId(String string) {
		this.transId = string;
	}

	public long getWalletId() {
		return walletId;
	}

	public void setWalletId(long walletId) {
		this.walletId = walletId;
	}

	public double getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(double transAmount) {
		this.transAmount = transAmount;
	}

	public double getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(double netAmount) {
		this.netAmount = netAmount;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getReversed() {
		return reversed;
	}

	public void setReversed(String reversed) {
		this.reversed = reversed;
	}



	@Override
	public String toString() {
		return "Transaction [id=" + id + ", transId=" + transId + ", walletId=" + walletId + ", transAmount="
				+ transAmount + ", netAmount=" + netAmount + ", transType=" + transType + ", balance=" + balance
				+ ", date=" + date + ", reversed=" + reversed + "]";
	}

	
}

package com.assignment.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "transaction")
public class Transaction {

	private @Id @GeneratedValue long id;
	private @NotBlank String transId;
	private @NotBlank long walletId;
	private @NotBlank double transAmount;
	private @NotBlank double netAmount;
	private @NotBlank String transType;
	private @NotBlank double balance;
	private @NotBlank Date date;
	private @NotBlank String reversed;

	public Transaction() {
	}

	
	
	public Transaction( @NotBlank String transId, @NotBlank long walletId, @NotBlank double transAmount,
			@NotBlank double netAmount, @NotBlank String transType, @NotBlank double balance, @NotBlank Date date,
			@NotBlank String reversed) {
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


}

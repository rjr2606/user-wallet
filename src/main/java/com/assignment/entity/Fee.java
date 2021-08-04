package com.assignment.entity;

public class Fee {
	private double transactionAmount;
	private double charge;
	private double commission;
	private double netAmount;
	
	public double getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public double getCharge() {
		return charge;
	}

	public void setCharge(double charge) {
		this.charge = charge;
	}

	public double getCommission() {
		return commission;
	}

	public void setCommission(double commission) {
		this.commission = commission;
	}

	public double getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(double netAmount) {
		this.netAmount = netAmount;
	}

	@Override
	public String toString() {
		return "Fee [transactionAmount=" + transactionAmount + ", charge=" + charge + ", commission=" + commission
				+ ", netAmount=" + netAmount + "]";
	}
	
	

}

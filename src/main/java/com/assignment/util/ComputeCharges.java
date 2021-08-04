package com.assignment.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.assignment.entity.Fee;

public class ComputeCharges {

	public static Fee calculate(double transactionAmount) {
		double commission, charge, netAmount;
		Fee feeDetails = new Fee();

		commission =new BigDecimal((transactionAmount * 0.05) / 100).setScale(2,RoundingMode.DOWN).doubleValue();
		charge = new BigDecimal((transactionAmount * 0.2) / 100).setScale(2,RoundingMode.DOWN).doubleValue();
		netAmount = new BigDecimal(transactionAmount-commission-charge).setScale(2,RoundingMode.DOWN).doubleValue();
		
		feeDetails.setCharge(charge);
		feeDetails.setCommission(commission);
		feeDetails.setTransactionAmount(transactionAmount);
		feeDetails.setNetAmount(netAmount);
		
		return feeDetails;
		
	}
}

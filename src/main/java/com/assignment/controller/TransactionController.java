package com.assignment.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.entity.AddRequest;
import com.assignment.entity.Fee;
import com.assignment.entity.Transaction;
import com.assignment.entity.TransferRequest;
import com.assignment.service.TransactionService;


@RestController
public class TransactionController {
	@Autowired
	TransactionService transcationService;

	@GetMapping("/wallet/charges/{amount}")
	public Fee computeCharges(@Valid @PathVariable double amount) {
		return transcationService.computeCharges(amount);
	}
	
	@GetMapping("/wallet/history/{walletId}")
	public List<Transaction> fetchAllTransactions(@Valid @PathVariable long walletId) {
		return transcationService.fetchAllTransactions(walletId);
	}
	
	@PostMapping("/wallet/transfer")
	public Transaction transfer(@Valid @RequestBody TransferRequest request) throws Exception {
		return transcationService.transerMoney(request);
	}
	
	
	@PostMapping("/wallet/addMoney")
	public Transaction addMoney(@Valid @RequestBody AddRequest request) throws Exception {
		return transcationService.addMoney(request);
	}
}

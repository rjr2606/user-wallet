package com.assignment.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.entity.AddRequest;
import com.assignment.entity.TransferRequest;
import com.assignment.service.TransactionService;

@RestController
@Validated
public class TransactionController {
	@Autowired
	TransactionService transcationService;

	@GetMapping("/wallet/charges/{amount}")
	public ResponseEntity<?> computeCharges(@Valid @PathVariable @NotNull double amount) {
          return new ResponseEntity<>(transcationService.computeCharges(amount), HttpStatus.OK);
	}
	
	@PostMapping("/wallet/transfer" )
	public ResponseEntity<?> transfer(@Valid @RequestBody TransferRequest request) throws Exception {
		return new ResponseEntity<>(transcationService.transerMoney(request), HttpStatus.OK);
	}
	
	@GetMapping("/wallet/reverse/{transId}")
	public ResponseEntity<?> reverse(@Valid @PathVariable String transId) throws Exception {
		return new ResponseEntity<>(transcationService.reverse(transId), HttpStatus.OK);
	}
	
	@PostMapping("/wallet/addMoney")
	public ResponseEntity<?> addMoney(@Valid @RequestBody AddRequest request) throws Exception {
		return new ResponseEntity<>(transcationService.addMoney(request), HttpStatus.OK);
	}
	
	@GetMapping("/wallet/history/{walletId}")
	public ResponseEntity<?> fetchAllTransactions(@Valid @PathVariable long walletId) {
		return new ResponseEntity<>(transcationService.fetchAllTransactions(walletId), HttpStatus.OK);
	}
	
	@GetMapping("/wallet/inquiry/{transId}")
	public ResponseEntity<?> fetchTransaction(@Valid @PathVariable String transId) {
		return new ResponseEntity<>(transcationService.fetchTransactionDetails(transId), HttpStatus.OK);
	}
	
}

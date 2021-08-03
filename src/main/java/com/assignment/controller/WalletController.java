package com.assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.assignment.entity.Status;
import com.assignment.entity.Wallet;
import com.assignment.service.WalletService;

import javax.validation.Valid;

@RestController
public class WalletController {
	@Autowired
	WalletService walletService;

	@PostMapping("/wallet/register")
	public Status registerUser(@Valid @RequestBody Wallet newUser) {
		return walletService.registerUser(newUser);
	}

	@PostMapping("/wallet/signIn")
	public Status signInUser(@Valid @RequestBody Wallet user) {
		return walletService.signInUser(user);
	}

	@PostMapping("/wallet/signOut")
	public Status signOutUser(@Valid @RequestBody Wallet user) {
		return walletService.signOutUser(user);
	}

	@DeleteMapping("/wallet/purgeAll")
	public Status deleteAllWallets() {
		return walletService.deleteAllWallets();
	}
}
package com.assignment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.entity.Status;
import com.assignment.entity.Wallet;
import com.assignment.repository.WalletRepository;

@Service
public class WalletService {

	@Autowired
	WalletRepository walletRepository;

	public Status registerUser(Wallet newUser) {
		List<Wallet> wallets = walletRepository.findAll();
		System.out.println("New user: " + newUser.toString());
		for (Wallet user : wallets) {
			System.out.println("Registered user: " + newUser.toString());
			if (user.equals(newUser)) {
				System.out.println("Wallet Already exists!");
				return Status.USER_ALREADY_EXISTS;
			}
		}
		walletRepository.save(newUser);
		return Status.SUCCESS;
	}

	public Status signInUser(Wallet user) {
		List<Wallet> wallets = walletRepository.findAll();
		for (Wallet other : wallets) {
			if (other.equals(user)) {
				user.setLoggedIn(true);
				walletRepository.save(user);
				return Status.SUCCESS;
			}
		}
		return Status.FAILURE;
	}

	public Status signOutUser(Wallet user) {
		List<Wallet> wallets = walletRepository.findAll();
		for (Wallet other : wallets) {
			if (other.equals(user)) {
				user.setLoggedIn(false);
				walletRepository.save(user);
				return Status.SUCCESS;
			}
		}
		return Status.FAILURE;
	}

	public Status deleteAllWallets() {
		walletRepository.deleteAll();
		return Status.SUCCESS;
	}
}

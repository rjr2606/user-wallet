package com.assignment.service;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.entity.Status;
import com.assignment.entity.User;
import com.assignment.entity.Wallet;
import com.assignment.repository.UserRepository;
import com.assignment.repository.WalletRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	WalletRepository walletRepository;

	public Status registerUser(User newUser) {
		User user = userRepository.findByUsername(newUser.getUsername()); // search for particular user
		System.out.println("New user: " + newUser.toString());
		if (user != null) {
			System.out.println("User Already exists!");
			return Status.USER_ALREADY_EXISTS;
		} else {
			User createdUser = userRepository.save(newUser);
			Wallet newWallet = new Wallet(createdUser.getId(), 0, new Date());
			walletRepository.save(newWallet);

		}
		return Status.SUCCESS;
	}

	public Status signInUser(User user) {
		User userExists = userRepository.findByUsername(user.getUsername()); // particular user
		if (userExists.equals(user)) {
			user.setLoggedIn(true);
			userRepository.save(user);
			return Status.SUCCESS;
		}
		return Status.FAILURE;
	}

	public Status signOutUser(User user) {
		User userSignedIn = userRepository.findByUsername(user.getUsername()); // particular user
		if (userSignedIn.equals(user)) {
			user.setLoggedIn(false);
			userRepository.save(user);
			return Status.SUCCESS;
		}
		return Status.FAILURE;
	}

	public Status deleteAllUsersAndWallets() {
		userRepository.deleteAll();
		walletRepository.deleteAll();
		return Status.SUCCESS;
	}
}

package com.assignment.serviceImpl;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.entity.Status;
import com.assignment.entity.User;
import com.assignment.entity.Wallet;
import com.assignment.exception.ResourceNotFoundException;
import com.assignment.repository.UserRepository;
import com.assignment.repository.WalletRepository;
import com.assignment.service.UserService;

import static com.assignment.util.Constants.*;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	WalletRepository walletRepository;

	@Transactional
	public User registerUser(User newUser) {
		User user = userRepository.findByUsername(newUser.getUsername()); 
		if (user != null) {
			throw new ResourceNotFoundException(USER_EXISTS);
		} 
			User createdUser = userRepository.save(newUser);
			Wallet newWallet = new Wallet(createdUser.getId(), 0, new Date());
			walletRepository.save(newWallet);
		return createdUser;
	}

	public User signInUser(User user) {
		User userExists = userRepository.findByUsername(user.getUsername());
		if (userExists.equals(user)) {
			user.setLoggedIn(true);
			return userRepository.save(user);
		}
		throw new ResourceNotFoundException(USER_NOT_FOUND);
	}

	public User signOutUser(User user) {
		User userSignedIn = userRepository.findByUsername(user.getUsername()); 
		if (userSignedIn.equals(user)) {
			user.setLoggedIn(false);
			return userRepository.save(user);
		}
		throw new ResourceNotFoundException(USER_NOT_FOUND);
	}

	public Status deleteAllUsersAndWallets() {
		userRepository.deleteAll();
		walletRepository.deleteAll();
		return Status.SUCCESS;
	}
}

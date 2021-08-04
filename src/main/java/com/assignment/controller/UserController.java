package com.assignment.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.entity.Status;
import com.assignment.entity.User;
import com.assignment.service.UserService;

@RestController
public class UserController {
	@Autowired
	UserService userService;

	@PostMapping("/wallet/register")
	public Status registerUser(@Valid @RequestBody User newUser) {
		return userService.registerUser(newUser);
	}

	@PostMapping("/wallet/signIn")
	public Status signInUser(@Valid @RequestBody User user) {
		return userService.signInUser(user);
	}

	@PostMapping("/wallet/signOut")
	public Status signOutUser(@Valid @RequestBody User user) {
		return userService.signOutUser(user);
	}

	@DeleteMapping("/wallet/purgeAll")
	public Status deleteAll() {
		return userService.deleteAllUsersAndWallets();
	}
}
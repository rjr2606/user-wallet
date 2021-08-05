package com.assignment.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.entity.User;
import com.assignment.service.UserService;

@RestController
@Validated
public class UserController {
	
	@Autowired
	UserService userService;

	@PostMapping("/wallet/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody User newUser) {
		return new ResponseEntity<>(userService.registerUser(newUser) ,HttpStatus.OK);
	}

	@PostMapping("/wallet/signIn")
	public  ResponseEntity<?> signInUser(@Valid @RequestBody User user) {
		return new ResponseEntity<>(userService.signInUser(user) ,HttpStatus.OK);
	}

	@PostMapping("/wallet/signOut")
	public  ResponseEntity<?> signOutUser(@Valid @RequestBody User user) {
		return new ResponseEntity<>(userService.signOutUser(user),HttpStatus.OK);
	}

	@DeleteMapping("/wallet/purgeAll")
	public  ResponseEntity<?> deleteAll() {
		return new ResponseEntity<>(userService.deleteAllUsersAndWallets(),HttpStatus.OK);
	}
	
}
package com.assignment.service;

import com.assignment.entity.Status;
import com.assignment.entity.User;

public interface UserService {

	public User registerUser(User newUser);

	public User signInUser(User user);

	public User signOutUser(User user);

	public Status deleteAllUsersAndWallets();

}

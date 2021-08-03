package com.assignment.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Table(name = "wallet")
public class Wallet {
	private @Id @GeneratedValue long id;
	private @NotBlank String username;
	private @NotBlank String password;
	private @NotBlank long balance;
	private @NotBlank boolean loggedIn;

	public Wallet() {
	}

	public Wallet(@NotBlank String username, @NotBlank String password, @NotBlank long balance) {
		this.username = username;
		this.password = password;
		this.balance = balance;
		this.loggedIn = false;
	}

	public long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Wallet))
			return false;
		Wallet user = (Wallet) o;
		return Objects.equals(username, user.username) && Objects.equals(password, user.password);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username, password, loggedIn);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", balance=" + balance
				+ ", loggedIn=" + loggedIn + "]";
	}
	

}

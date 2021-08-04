package com.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.entity.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

	Wallet findByWalletId(long fromWalletId);
	
	
}
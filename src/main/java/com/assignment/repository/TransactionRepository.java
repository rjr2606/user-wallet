package com.assignment.repository;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.assignment.entity.Fee;
import com.assignment.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	Transaction findByWalletId(long walletId);

	Transaction findTopByWalletIdOrderByDateDesc(long walletId);

	List<Transaction> findAllByWalletIdOrderByDateDesc(@Valid long walletId);

}
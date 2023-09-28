package com.bank.bank_account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface transactionRepository extends JpaRepository<transactions, Integer> {
    List<transactions> findBySender(String FirstName);
    List<transactions> findByRecipient(String FirstName);

}

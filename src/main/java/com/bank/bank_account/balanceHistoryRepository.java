package com.bank.bank_account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface balanceHistoryRepository extends JpaRepository<balanceHistory, Integer> {

}
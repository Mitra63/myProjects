package com.sadad.ib.repository;

import com.sadad.ib.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query(value = "SELECT ACCOUNT_SEQUENCE_ID.nextval FROM dual", nativeQuery = true)
    Long getLastAccountId();

    Optional<Account> findByAccountNumber(Long accountNumber);

    List<Account> findByUserId(Long userId);
    @Query(value = "SELECT a FROM Account a where a.blocked=false")
    List<Account> findAll();
}

package com.sadad.ib.repository;

import com.sadad.ib.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Long> {
    @Query(value = "SELECT TRANSACTION_SEQUENCE_ID.nextval FROM dual", nativeQuery = true)
    Long getLastTransactionId();

    Page<Transaction> findAll(Pageable pageable);

    Page<Transaction> findByTransactionOwnerId(Pageable pageable,Long transactionOwnerId);
}

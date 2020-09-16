package com.sadad.ib.service;

import com.sadad.ib.entity.Account;
import com.sadad.ib.entity.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionService {
    Transaction save(Transaction transaction) throws Exception;
    List<Transaction> findAll(int page,int size,Long userId);
}

package com.sadad.ib.service.impl;

import com.sadad.ib.Exception.AccountIsBlockedException;
import com.sadad.ib.Exception.AccountNumberNotFoundException;
import com.sadad.ib.Exception.InvalidDestinationException;
import com.sadad.ib.entity.Account;
import com.sadad.ib.entity.Transaction;
import com.sadad.ib.repository.AccountRepository;
import com.sadad.ib.repository.TransactionRepository;
import com.sadad.ib.service.AccountService;
import com.sadad.ib.service.TransactionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }


    @Override
    @Transactional
    public Transaction save(Transaction transaction) throws Exception {
        accountService.checkBalance(transaction.getSourceAccount().getAccountNumber(), transaction.getAmount());
        if (transaction.getSourceAccount().isBlocked()) {
            throw new AccountIsBlockedException("Account is Blocked");
        }
        Account sourceAccount = transaction.getSourceAccount();
        Account destinationAccount = transaction.getDestinationAccount();
        if (sourceAccount.getUserId().equals(destinationAccount.getUserId())) {
            throw new InvalidDestinationException("Destination account should not belong to you");
        }
        Long newTransactionId = transactionRepository.getLastTransactionId();
        transaction.setTransactionId(newTransactionId);
        transaction.setOperationType(Transaction.OperationType.WITHDRAWAL);
        transaction.setDestinationAccountId(destinationAccount.getAccountID());
        transaction.setSourceAccountId(sourceAccount.getAccountID());
        transactionRepository.save(transaction);

        sourceAccount.setBalance(sourceAccount.getBalance() - transaction.getAmount());
        accountService.save(sourceAccount);

        destinationAccount.setBalance(destinationAccount.getBalance() + transaction.getAmount());
        accountService.save(destinationAccount);

        return transaction;
    }


    //@PostFilter and pageable  can not be used together, findAll(pageReq).getContent() returned unmodifiableRandomAccessList collection
    /*  @PostFilter("hasRole('ADMIN') or filterObject.user.username == authentication.name")*/
    @Override
    @Transactional(readOnly = true)
    public List<Transaction> findAll(int page, int size, Long userId) {
        PageRequest pageReq = PageRequest.of(page, size);
        Optional<Long> optionalUserId = Optional.ofNullable(userId);
        if (optionalUserId.isEmpty()) {
            return transactionRepository.findAll(pageReq).getContent();
        } else {
            return transactionRepository.findByTransactionOwnerId(pageReq,userId).getContent();
        }
    }
}

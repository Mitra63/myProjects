package com.sadad.ib.service.impl;

import com.sadad.ib.Exception.AccountNumberNotFoundException;
import com.sadad.ib.Exception.InvalidBalanceException;
import com.sadad.ib.entity.Account;
import com.sadad.ib.repository.AccountRepository;
import com.sadad.ib.service.AccountService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> findByUser(Long userId) {
        return accountRepository.findByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    @PostFilter("hasRole('ADMIN') or filterObject.user.username == authentication.name")
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    @Transactional
    public void blockAccount(Long accountNumber, boolean isBlocked) {
        Account account=findByAccountNumber(accountNumber);
        account.setBlocked(isBlocked);
        accountRepository.save(account);
       /* accountOptional.ifPresentOrElse(
                (value)
                        -> {
                    accountOptional.get().setBlocked(isBlocked);
                    accountRepository.save(accountOptional.get());
                },
                ()
                        -> {
                    throw new AccountNumberNotFoundException("account number not found");
                });*/
    }


    @Override
    @Transactional(readOnly = true)
    public Account findByAccountNumber(Long accountNumber) {
        Optional<Account> accountOptional = accountRepository.findByAccountNumber(accountNumber);
        if (accountOptional.isEmpty()) {
            throw new AccountNumberNotFoundException("account number not found");
        }
        return accountOptional.get();
    }

    @Override
    @Transactional
    public Account save(Account account) {
        Optional<Account> accountOptional = accountRepository.findByAccountNumber(account.getAccountNumber());
        if(accountOptional.isEmpty()){
            Long newAccountId = accountRepository.getLastAccountId();
            account.setAccountID(newAccountId);
            account.setAccountNumber(10000 + newAccountId);
        }
        return accountRepository.save(account);
    }

    public void checkBalance(Long accountNumber, Long balance) {
        Account account = findByAccountNumber(accountNumber);
        if(account.getBalance()<balance){
           throw  new InvalidBalanceException("The available balance for your account is less than transfer amount");
        }
    }

}

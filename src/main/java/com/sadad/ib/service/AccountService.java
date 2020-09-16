package com.sadad.ib.service;

import com.sadad.ib.entity.Account;
import com.sadad.ib.entity.RestUser;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    Account save(Account account) throws Exception;
    List<Account> findByUser(Long userId);
    List<Account> findAll();
    void blockAccount(Long accountNumber,boolean isBlocked);
    Account findByAccountNumber(Long accountNumber);
    void checkBalance(Long accountNumber, Long balance);
}

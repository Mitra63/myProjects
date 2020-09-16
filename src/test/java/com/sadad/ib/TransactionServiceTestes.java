package com.sadad.ib;

import com.sadad.ib.Exception.AccountIsBlockedException;
import com.sadad.ib.Exception.InvalidBalanceException;
import com.sadad.ib.Exception.InvalidDestinationException;
import com.sadad.ib.entity.Account;
import com.sadad.ib.entity.Transaction;
import com.sadad.ib.repository.TransactionRepository;
import com.sadad.ib.service.AccountService;
import com.sadad.ib.service.TransactionService;
import com.sadad.ib.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest

public class TransactionServiceTestes {


    TransactionRepository transactionRepository;
    TransactionService transactionService;
    AccountService accountService;


    private static final Transaction SAVED_TRANSACTION = new Transaction(12l, 10001l,
            10002l, 1200l, Transaction.OperationType.WITHDRAWAL,
            new Date(), "10000L", 1l);


    private static final Transaction SAVED_TRANSACTION2 = new Transaction(12l, 10003l,
            10002l, 1200l, Transaction.OperationType.WITHDRAWAL,
            new Date(), "10000L", 1l);

    private static final Account SAVED_ACCOUNT = new Account(123456789L, 1L, 10000L, true);
    private static final Account TEST_SOURCE_ACCOUNT = new Account(10001L, 2L, 14000L, false);
    private static final Account TEST_BLOCKED_ACCOUNT = new Account(10003L, 2L, 14000L, true);
    private static final Account TEST_DESTINATION_ACCOUNT = new Account(10002L, 3L, 10000L, false);
    private final Long TRANSACTION_AMOUNT = 200L;

    @BeforeEach
    public void init() throws Exception {
        transactionRepository = createTransactionRepository();
        accountService = createAccountService();
        transactionService = new TransactionServiceImpl(transactionRepository, accountService);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void blockedAccount_addThrow() throws Exception {
        Transaction testTransaction = SAVED_TRANSACTION2;
        testTransaction.setAmount(TRANSACTION_AMOUNT);
        testTransaction.setSourceAccount(TEST_BLOCKED_ACCOUNT);
        testTransaction.setDestinationAccount(TEST_DESTINATION_ACCOUNT);
        doNothing().when(accountService).checkBalance(TEST_BLOCKED_ACCOUNT.getAccountNumber(), TRANSACTION_AMOUNT);
        Assertions.assertThrows(AccountIsBlockedException.class, () -> {
            transactionService.save(testTransaction);
        });
    }

    @Test
    public void lessBalance_addThrow() throws Exception {
        Transaction testTransaction=SAVED_TRANSACTION;
        testTransaction.setSourceAccount(TEST_SOURCE_ACCOUNT);
        testTransaction.setSourceAccountId(TEST_SOURCE_ACCOUNT.getAccountID());
        testTransaction.setDestinationAccountId(TEST_DESTINATION_ACCOUNT.getAccountID());
        testTransaction.setAmount(15000L);
        doThrow(new InvalidBalanceException("The available balance for your account is less than transfer amount"))
                .when(accountService).checkBalance(TEST_SOURCE_ACCOUNT.getAccountNumber(), 15000L);
        Assertions.assertThrows(InvalidBalanceException.class, () -> {
            transactionService.save(testTransaction);
        });
    }

    @Test
    public void sourceAndDestinationAccountTheSame_addThrow() throws Exception {
        Transaction testTransaction = SAVED_TRANSACTION2;
        testTransaction.setAmount(TRANSACTION_AMOUNT);
        testTransaction.setSourceAccount(TEST_SOURCE_ACCOUNT);
        testTransaction.setDestinationAccount(TEST_SOURCE_ACCOUNT);
        doNothing().when(accountService).checkBalance(TEST_SOURCE_ACCOUNT.getAccountNumber(), TRANSACTION_AMOUNT);
        Assertions.assertThrows(InvalidDestinationException.class, () -> {
            transactionService.save(testTransaction);
        });
    }


    @Test
    public void saveTransaction_ok() throws Exception {
        Transaction testTransaction = SAVED_TRANSACTION;
        doNothing().when(accountService).checkBalance(TEST_SOURCE_ACCOUNT.getAccountNumber(), TRANSACTION_AMOUNT);
        testTransaction.setSourceAccount(TEST_SOURCE_ACCOUNT);
        testTransaction.setAmount(TRANSACTION_AMOUNT);
        testTransaction.setDestinationAccount(TEST_DESTINATION_ACCOUNT);
        doReturn(testTransaction).when(transactionRepository).save(any());

        Transaction returnedTransaction = transactionService.save(testTransaction);

        Assertions.assertNotNull(returnedTransaction, "The saved transaction should not be null");

    }

    private TransactionRepository createTransactionRepository() {
        TransactionRepository mockRepository = mock(TransactionRepository.class);
        return mockRepository;
    }


    private AccountService createAccountService() throws Exception {
        AccountService mockService = mock(AccountService.class);
        return mockService;
    }
}

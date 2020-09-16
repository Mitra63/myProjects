package com.sadad.ib;


import com.sadad.ib.controller.AccountController;
import com.sadad.ib.entity.Account;
import com.sadad.ib.repository.AccountRepository;
import com.sadad.ib.service.AccountService;
import com.sadad.ib.service.impl.AccountServiceImpl;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Date;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class AccountControllerTests {



    private static final Account BLOCKED_ACCOUNT = new Account(123456789L,1L,10000L, true);
    private static final Account ENABLED_ACCOUNT =  new Account(123456789L,1L,10000L, false);



    AccountRepository accountRepository;

    AccountService accountService;

    @BeforeEach
    public void init() {
        accountRepository=createAccountRepository();
        accountService=new AccountServiceImpl(accountRepository);
        MockitoAnnotations.initMocks(this);
    }


@Test
    public void createAccountTest() throws Exception{
        Account account=new Account();
        account.setAccountID(1L);
        account.setAccountNumber(123456L);
        account.setAccountType(Account.AccountType.SAVINGS);
        account.setBalance(100000L);
        account.setCreateDate(new Date());
        account.setUserId(1L);

    accountService.save(account);
    verify(accountRepository).save(any(Account.class));
}


    private AccountRepository createAccountRepository() {
        AccountRepository mockRepository = mock(AccountRepository.class);
        when(mockRepository.save(any(Account.class))).thenReturn(ENABLED_ACCOUNT);
        return mockRepository;
    }

}

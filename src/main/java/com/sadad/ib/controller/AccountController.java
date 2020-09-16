package com.sadad.ib.controller;

import com.sadad.ib.Exception.RequestFormatException;
import com.sadad.ib.dto.AccountRequestDTO;
import com.sadad.ib.dto.AccountResponseDTO;
import com.sadad.ib.entity.Account;
import com.sadad.ib.entity.Customer;
import com.sadad.ib.service.AccountService;
import com.sadad.ib.service.CustomerService;
import com.sadad.ib.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/account")
public class AccountController extends BaseController {

    private final CustomerService customerService;
    private final AccountService accountService;
    private final UsersService usersService;

    @Autowired
    public AccountController(CustomerService customerService, AccountService accountService, UsersService usersService) {
        super(accountService, usersService);
        this.customerService = customerService;
        this.accountService = accountService;
        this.usersService = usersService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<Account> createAccount(@Valid @RequestBody AccountRequestDTO accountDto,
                                                 BindingResult bindingResult) throws Exception {

        if (bindingResult.hasErrors()) {
            throw new RequestFormatException("request is invalid");
        }

        Customer customer = customerService.findByNationalCode(accountDto.getNationalCode());
        Account account = new Account();
        account.setUserId(customer.getUserId());
        account.setBalance(accountDto.getBalance());
        account.setCreateDate(new Date());
        if (accountDto.getAccountType().equalsIgnoreCase("c")) {
            account.setAccountType(Account.AccountType.CURRENT);
        } else if (accountDto.getAccountType().equalsIgnoreCase("s")) {
            account.setAccountType(Account.AccountType.SAVINGS);
        }
        accountService.save(account);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PutMapping("/block/{accountNumber}")
    public ResponseEntity<String> blockAccount(@PathVariable("accountNumber") Long accountNumber,
                                               Authentication authentication) {

        checkUserAccessAccount(authentication, accountService.findByAccountNumber(accountNumber));
        accountService.blockAccount(accountNumber, true);
        String message = "Account successfully blocked";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PutMapping("/nonBlocking/{accountNumber}")
    public ResponseEntity<String> nonBlockingAccount(@PathVariable("accountNumber") Long accountNumber,
                                                     Authentication authentication) {

        checkUserAccessAccount(authentication, accountService.findByAccountNumber(accountNumber));
        accountService.blockAccount(accountNumber, false);
        String message = "Account successfully non blocking";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping(value = "/getAccounts")
    public ResponseEntity<List<AccountResponseDTO>> getAccountList() {
        List<Account> accounts = accountService.findAll();
        List<AccountResponseDTO> accountResponseDtoS = new ArrayList<>();
        accounts.forEach(account -> {
            AccountResponseDTO accountResponse = AccountResponseDTO.builder()
                    .accountNumber(account.getAccountNumber())
                    .accountType(account.getAccountType().toString())
                    .balance(account.getBalance())
                    .username(account.getUser().getUsername())
                    .build();

            accountResponseDtoS.add(accountResponse);
        });
        return new ResponseEntity<>(accountResponseDtoS, HttpStatus.OK);
    }

}

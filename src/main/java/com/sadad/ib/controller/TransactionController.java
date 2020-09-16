package com.sadad.ib.controller;

import com.sadad.ib.Exception.RequestFormatException;
import com.sadad.ib.dto.TransactionResponseDTO;
import com.sadad.ib.dto.TransferMoneyRequestDTO;
import com.sadad.ib.entity.Account;
import com.sadad.ib.entity.RestUser;
import com.sadad.ib.entity.Transaction;
import com.sadad.ib.service.AccountService;
import com.sadad.ib.service.TransactionService;
import com.sadad.ib.service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/transaction")
public class TransactionController extends BaseController {

    private TransactionService transactionService;
    private AccountService accountService;
    private UsersService usersService;

    public TransactionController(AccountService accountService,
                                 UsersService usersService, TransactionService transactionService) {
        super(accountService, usersService);
        this.transactionService = transactionService;
        this.accountService = accountService;
        this.usersService = usersService;
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PostMapping("/transfer")
    public ResponseEntity<String> transferMoney(@Valid @RequestBody TransferMoneyRequestDTO transferMoneyRequestDto,
                                                BindingResult bindingResult, Authentication authentication) throws Exception {

        if (bindingResult.hasErrors()) {
            throw new RequestFormatException("request is invalid");
        }
        Transaction transaction = new Transaction();
        Account sourceAccount = accountService.findByAccountNumber(transferMoneyRequestDto.getSourceAccountNumber());
        checkUserAccessAccount(authentication, sourceAccount);
        Account destinationAccount = accountService.findByAccountNumber(transferMoneyRequestDto.getDestinationAccountNumber());
        transaction.setSourceAccount(sourceAccount);
        transaction.setDestinationAccount(destinationAccount);
        transaction.setAmount(transferMoneyRequestDto.getAmount());
        transaction.setDescription(transferMoneyRequestDto.getDescription());
        transaction.setTransactionDate(new Date());
        RestUser user = usersService.findByUsername(authentication.getName());
        transaction.setTransactionOwnerId(user.getUserId());

        transactionService.save(transaction);
        String message = "Transfer money successfully done";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @RequestMapping(value = "/getTransactions", method = GET)
    public ResponseEntity<List<TransactionResponseDTO>> getTransactionList(@RequestParam(value = "pageNo") Integer pageNo,
                                                                           @RequestParam(value = "pageSize") Integer pageSize,
                                                                           Authentication authentication) {
        List<Transaction> transactions=null;

        if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
           transactions = transactionService.findAll(pageNo, pageSize,null);
        }else{
            Long userId=usersService.findByUsername(authentication.getName()).getUserId();
            transactions = transactionService.findAll(pageNo, pageSize,userId);
        }
        //List<Transaction> transactions = transactionService.findAll(pageNo, pageSize,false,userId);
        List<TransactionResponseDTO> transactionsList = new ArrayList<>();
        transactions.forEach(transaction -> {
            TransactionResponseDTO transactionResponseDto = TransactionResponseDTO.builder()
                    .sourceAccountNumber(transaction.getSourceAccount().getAccountNumber())
                    .destinationAccountNumber(transaction.getDestinationAccount().getAccountNumber())
                    .operationType(transaction.getOperationType().toString())
                    .transactionDate(transaction.getTransactionDate())
                    .balance(transaction.getAmount())
                    .description(transaction.getDescription())
                    .build();

            transactionsList.add(transactionResponseDto);
        });
        return new ResponseEntity<>(transactionsList, HttpStatus.OK);
    }

}

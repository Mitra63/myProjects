package com.sadad.ib.controller;

import com.sadad.ib.Exception.AccountNumberNotFoundException;
import com.sadad.ib.Exception.ResourceNotAccessException;
import com.sadad.ib.entity.Account;
import com.sadad.ib.entity.RestUser;
import com.sadad.ib.service.AccountService;
import com.sadad.ib.service.UsersService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


public class BaseController {

    private final AccountService accountService;
    private final UsersService usersService;

    public BaseController(AccountService accountService, UsersService usersService) {
        this.accountService = accountService;
        this.usersService = usersService;
    }

    protected void checkUserAccessAccount(Authentication authentication, Account account) {
        RestUser user = usersService.findByUsername(authentication.getName());
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))
                && !account.getUser().getUsername().equals(authentication.getName())) {
            throw new ResourceNotAccessException("You not permission for this operation");
        }
    }
}

package com.sadad.ib.service.impl;

import com.sadad.ib.Exception.UserNotFoundException;
import com.sadad.ib.entity.RestUser;
import com.sadad.ib.repository.UsersRepository;
import com.sadad.ib.service.UsersService;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public RestUser findByUsername(String username) {
        Optional<RestUser> users = usersRepository.findByUsername(username);
        if (!users.isPresent()) {
            throw new UserNotFoundException("user not found");
        }
        return users.get();
    }
}


package com.sadad.ib.service;

import com.sadad.ib.entity.RestUser;

public interface UsersService {
    RestUser findByUsername(String username);
}

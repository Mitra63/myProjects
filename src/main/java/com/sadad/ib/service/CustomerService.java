package com.sadad.ib.service;

import com.sadad.ib.entity.Customer;

public interface CustomerService {
    Customer findByNationalCode(String nationalCode);
}

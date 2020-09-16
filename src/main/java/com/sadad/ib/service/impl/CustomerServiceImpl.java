package com.sadad.ib.service.impl;

import com.sadad.ib.Exception.UserNotFoundException;
import com.sadad.ib.entity.Customer;
import com.sadad.ib.repository.CustomerRepository;
import com.sadad.ib.service.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Customer findByNationalCode(String nationalCode) {
        Optional<Customer> customer = customerRepository.findByNationalCode(nationalCode);
        if (!customer.isPresent()) {
            throw new UserNotFoundException("user not found");
        }
        return customer.get();
    }
}

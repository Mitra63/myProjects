package com.sadad.ib.repository;

import com.sadad.ib.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByNationalCode(String nationalCode);
}

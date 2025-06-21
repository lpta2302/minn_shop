package com.thienan.account_service.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thienan.account_service.customer.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
    
}

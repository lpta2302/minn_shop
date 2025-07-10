package com.thienan.account_service.customer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.thienan.account_service.customer.dto.CustomerDetail;
import com.thienan.account_service.customer.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
    @Query("""
        select new com.thienan.account_service.customer.dto.CustomerDetail(
            c.id,
            a.email,
            concat(c.firstName, " ", c.lastName),
            c.phoneNumber,
            c.shippingAddress
        )
        from Customer c
        join c.account a
        where c.id = :customerId   
    """)
    Optional<CustomerDetail> findDetailCustomerById(Long customerId);
    
}

package com.thienan.account_service.customer.service;

import org.springframework.stereotype.Service;

import com.thienan.account_service.account.dto.AccountProfileRequest;
import com.thienan.account_service.customer.dto.CustomerDetail;
import com.thienan.account_service.customer.entity.Customer;
import com.thienan.account_service.customer.mapper.CustomerMapper;
import com.thienan.account_service.customer.repository.CustomerRepository;
import com.thienan.account_service.handler.exceptions.common.EntityNotFoundByIDException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerMapper customerMapper;

    private final CustomerRepository customerRepository;

    public Long createAndSave(AccountProfileRequest request){
        Customer customer = customerMapper.convertToCustomer(request);
        return customerRepository.save(customer)
            .getId();
    }

    public Customer createAndSave(Customer customer) {
        return customerRepository.save(customer);
    }

    public CustomerDetail findCustomerDetailById(Long customerId) {
        return customerRepository.findDetailCustomerById(customerId)
            .orElseThrow(()->new EntityNotFoundByIDException("Customer", customerId.toString()));
    }
}

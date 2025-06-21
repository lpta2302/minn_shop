package com.thienan.account_service.customer.service;

import org.springframework.stereotype.Service;
import com.thienan.account_service.account.entity.Account;
import com.thienan.account_service.customer.dto.CustomerRequest;
import com.thienan.account_service.customer.entity.Customer;
import com.thienan.account_service.customer.mapper.CustomerMapper;
import com.thienan.account_service.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerMapper customerMapper;

    private final CustomerRepository customerRepository;

    public Long createAndSave(CustomerRequest request){
        return customerRepository.save(
            customerMapper.convertToCustomer(request))
            .getId();
    }

    public Customer createAndSave(Customer customer) {
        return customerRepository.save(customer);
    }
}

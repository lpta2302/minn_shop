package com.thienan.account_service.customer.mapper;

import org.springframework.stereotype.Component;
import com.thienan.account_service.customer.dto.CustomerRequest;
import com.thienan.account_service.customer.entity.Customer;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomerMapper {
    public Customer convertToCustomer(CustomerRequest request){
        return Customer.builder()
            .fullname(request.fullname())
            .dateOfBirth(request.dateOfBirth())
            .build();
    }
}

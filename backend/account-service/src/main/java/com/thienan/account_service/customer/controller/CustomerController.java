package com.thienan.account_service.customer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thienan.account_service.customer.dto.CustomerDetail;
import com.thienan.account_service.customer.service.CustomerService;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDetail> findCustomerById(
        @PathVariable Long customerId
    ){
        return ResponseEntity.ok(customerService.findCustomerDetailById(customerId));
    }

    @GetMapping("/current-user")
    public ResponseEntity<CustomerDetail> getCurrentCustomer(
        @RequestHeader("userId") 
        @Parameter(hidden=true)
        Long userId
    ) {
        return ResponseEntity.ok(customerService.findCustomerDetailById(userId));
    }
    
}

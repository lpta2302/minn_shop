package com.thienan.auth_service.authentication;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping
    public String getMethodName() {
        return "Hello";
    }
        
}

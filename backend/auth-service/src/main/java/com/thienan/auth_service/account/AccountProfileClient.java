package com.thienan.auth_service.account;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@FeignClient(
    name="${application.openFeign.account-profile-service.name}",
    url="${application.openFeign.account-profile-service.url}"
)
public interface AccountProfileClient {
    @PostMapping
    Long createProfile(@RequestBody AccountProfileRequest request);
}

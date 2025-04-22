package com.thienan.product_service.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;

public class ApplicationAuditAware implements AuditorAware<String>{

    @Override
    @NonNull
    public Optional<String> getCurrentAuditor() {
        return Optional.of("");
    }
    
}

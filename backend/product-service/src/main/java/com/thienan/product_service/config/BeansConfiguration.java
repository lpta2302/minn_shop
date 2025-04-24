package com.thienan.product_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
@SuppressWarnings("unused")
public class BeansConfiguration {
    @Bean
    AuditorAware<String> auditorAware() {
        return new ApplicationAuditAware();
    }
}

package com.thienan.product_service.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SuppressWarnings("unused")
public class SwaggerConfiguration {
    @Bean
    GroupedOpenApi weightTypeApi(){
        return GroupedOpenApi.builder()
            .group("weight type")
            .pathsToMatch("/weight-types/**")
            .build();
    }
}
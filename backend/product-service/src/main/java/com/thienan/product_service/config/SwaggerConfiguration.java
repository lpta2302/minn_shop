package com.thienan.product_service.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public GroupedOpenApi weightTypeApi(){
        return GroupedOpenApi.builder()
            .group("weight type")
            .pathsToMatch("/weight-types/**")
            .build();
    }

    @Bean
    public GroupedOpenApi stockOptionApi(){
        return GroupedOpenApi.builder()
            .group("stock option")
            .pathsToMatch("/stock-options/**")
            .build();
    }

    @Bean
    public GroupedOpenApi stockApi(){
        return GroupedOpenApi.builder()
            .group("stock")
            .pathsToMatch("/stocks/**")
            .build();
    }

    @Bean
    public GroupedOpenApi productApi(){
        return GroupedOpenApi.builder()
            .group("product")
            .pathsToMatch("/products/**")
            .build();
    }

    @Bean
    public GroupedOpenApi productVariantApi(){
        return GroupedOpenApi.builder()
            .group("product variant")
            .pathsToMatch("/product-variants/**")
            .build();
    }
}
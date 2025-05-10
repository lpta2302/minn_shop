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

    @Bean
    GroupedOpenApi stockOptionApi(){
        return GroupedOpenApi.builder()
            .group("stock option")
            .pathsToMatch("/stock-options/**")
            .build();
    }

    @Bean
    GroupedOpenApi stockApi(){
        return GroupedOpenApi.builder()
            .group("stock")
            .pathsToMatch("/stocks/**")
            .build();
    }

    @Bean
    GroupedOpenApi productApi(){
        return GroupedOpenApi.builder()
            .group("product")
            .pathsToMatch("/products/**")
            .build();
    }

    @Bean
    GroupedOpenApi productVariantApi(){
        return GroupedOpenApi.builder()
            .group("product variant")
            .pathsToMatch("/product-variants/**")
            .build();
    }
}
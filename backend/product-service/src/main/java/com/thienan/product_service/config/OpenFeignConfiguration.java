package com.thienan.product_service.config;

import com.thienan.product_service.handler.FeignErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenFeignConfiguration {
    @Bean
    public ErrorDecoder errorDecoder(){
        return new FeignErrorDecoder();
    }
}

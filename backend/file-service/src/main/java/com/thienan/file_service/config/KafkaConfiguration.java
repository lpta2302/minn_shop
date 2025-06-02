package com.thienan.file_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class KafkaConfiguration {
    @Value("${kafka.topics.created-product-variant-image}")
    private String createdProductVariantImageTopicName;

    @Bean
    public NewTopic topics(){
        return TopicBuilder
            .name(createdProductVariantImageTopicName)
            .build();
    }
}

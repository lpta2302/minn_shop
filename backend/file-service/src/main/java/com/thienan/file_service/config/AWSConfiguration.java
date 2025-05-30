package com.thienan.file_service.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class AWSConfiguration {
    @Value("${aws.region}")
    private String region;

    @Value("${aws.profile}")
    private String profile;

    private Region getRegion(){
        return Region.of(region);
    }

    private ProfileCredentialsProvider getProfileCredentialsProvider(){
        return ProfileCredentialsProvider.create(profile);
    }

    @Bean
    S3Client amazonS3Client() {
        return S3Client
            .builder()
            .region(getRegion())
            .credentialsProvider(getProfileCredentialsProvider())
            .build();
    }

    @Bean
    S3Presigner s3Presigned() {
        return S3Presigner
            .builder()
            .region(getRegion())
            .credentialsProvider(getProfileCredentialsProvider())
            .build();
    }

    @Bean
    BucketConfig bucketConfig(){
        BucketConfig bucketConfig = BucketConfig.builder().build();
        Map<String, String> newFoldersMap = new HashMap<>();
        newFoldersMap.put("product-variant", "/product-variant");

        bucketConfig.addFolders(newFoldersMap);
        return bucketConfig;
    }
}

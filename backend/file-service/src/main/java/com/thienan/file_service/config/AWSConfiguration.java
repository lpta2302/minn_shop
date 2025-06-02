package com.thienan.file_service.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.thienan.file_service.core.enumeration.ObjectTypes;
import static com.thienan.file_service.core.enumeration.ObjectTypes.PRODUCT_VARIANT;

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
    public S3Client s3Client() {
        return S3Client
            .builder()
            .region(getRegion())
            .credentialsProvider(getProfileCredentialsProvider())
            .build();
    }

    @Bean
    public S3Presigner s3Presigned() {
        return S3Presigner
            .builder()
            .region(getRegion())
            .credentialsProvider(getProfileCredentialsProvider())
            .build();
    }

    @Bean
    public BucketConfig bucketConfig(){
        BucketConfig bucketConfig = BucketConfig.builder().build();
        Map<ObjectTypes, String> newFoldersMap = new HashMap<>();
        newFoldersMap.put(PRODUCT_VARIANT, "/product-variant");

        bucketConfig.addFolders(newFoldersMap);
        return bucketConfig;
    }
}

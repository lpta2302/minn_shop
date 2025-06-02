package com.thienan.file_service.core.service;

import static java.lang.String.format;
import java.util.List;

import org.springframework.stereotype.Service;

import com.thienan.file_service.config.BucketConfig;
import com.thienan.file_service.core.enumeration.FileAccess;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectAclRequest;
import software.amazon.awssdk.services.s3.model.GetObjectAclResponse;
import software.amazon.awssdk.services.s3.model.Grant;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;

@Service
@Slf4j
@RequiredArgsConstructor
public class AWSObjectService {
    private final S3Client s3Client;
    private final BucketConfig bucketConfig;

    public HeadObjectResponse getHeadObject(String key){
        HeadObjectRequest request = HeadObjectRequest
            .builder()
            .bucket(bucketConfig.getBucketName())
            .key(key)
            .build();
        return s3Client.headObject(request);
    }

    public GetObjectAclResponse getObjectAclResponse(String key){
        GetObjectAclRequest request = GetObjectAclRequest
            .builder()
            .bucket(bucketConfig.getBucketName())
            .key(key)
            .build();

        return s3Client.getObjectAcl(request);
    }

    public List<Grant> getPublicUserGrants(String key) {
        return getObjectAclResponse(key)
            .grants()
            .stream()
            .filter(this::isPublicGrant)
            .toList();
    }

    private boolean isPublicGrant(Grant grant) {
        final String publicGrantType = "Group";
        final String publicGrantUrl = "http://acs.amazonaws.com/groups/global/AllUsers";

        return grant.grantee() != null &&
            publicGrantType.equals(grant.grantee().typeAsString()) &&
            publicGrantUrl.equals(grant.grantee().uri());
    }

    public FileAccess getFileAccess(String key) {
        List<Grant> grants = getPublicUserGrants(key);

        for (Grant grant : grants) {
            switch (grant.permission()) {
                case WRITE -> {
                    return FileAccess.WRITE;
                }
                case READ -> {
                    return FileAccess.READ;
                }
                case FULL_CONTROL -> {
                    return FileAccess.FULL_CONTROL;
                }
                default -> {
                    return FileAccess.PRIVATE;
                }
            }
        }

        return FileAccess.PRIVATE;
    }

    public String createUrl(String key) {
        return format(
            "https://%s.s3.%s.amazonaws.com/%s", 
            bucketConfig.getBucketName(), 
            bucketConfig.getRegion(), key);
    }
}

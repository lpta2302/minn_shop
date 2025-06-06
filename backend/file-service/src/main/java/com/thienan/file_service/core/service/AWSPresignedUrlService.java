package com.thienan.file_service.core.service;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.thienan.file_service.config.BucketConfig;
import com.thienan.file_service.core.dto.FilePropertiesRequest;
import com.thienan.file_service.core.dto.GenerateUploadPresignedUrlRequest;
import com.thienan.file_service.core.dto.PresignedRequestParams;
import com.thienan.file_service.core.dto.PresignedUrl;
import com.thienan.file_service.core.dto.UploadPresignedUrlResponse;
import com.thienan.file_service.core.enumeration.FileAccess;
import com.thienan.file_service.core.enumeration.PresignedUrlHeaders;
import com.thienan.file_service.core.validator.FileInfoValidator;
import com.thienan.file_service.handler.exceptions.common.ExistedS3FileKeyException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.awscore.AwsRequestOverrideConfiguration;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Service
@Slf4j
@RequiredArgsConstructor
public class AWSPresignedUrlService {

    private final FileInfoValidator fileInfoValidator;
    private final FileAccessService fileAccessService;
    private final BucketConfig bucketConfig;
    private final S3Presigner s3Presigner;

    @Value("${app.aws-generate-key.maxRetries}")
    private int maxRetries;
    @Value("${app.aws-generate-key.retryDelayMillis}")
    private long retryDelayMillis;
    
    public UploadPresignedUrlResponse generatePresignedUploadUrl(GenerateUploadPresignedUrlRequest request) {
        String folderName = bucketConfig.getFolderName(request.objectType());
        FileAccess fileAccess = request.fileAccess();

        if (request.files() != null) {
            var files = request.files();

            List<PresignedUrl> presignedUrls = 
                files.stream()
                    .map(file -> generatePresignedUrl(file, folderName, fileAccess))
                    .toList();

            return UploadPresignedUrlResponse
                .builder()
                .presignedUrls(presignedUrls)
                .build();
        } else if(request.file() != null){
            var file = request.file();
            PresignedUrl presignedUrl = generatePresignedUrl(file, folderName, fileAccess);

            return UploadPresignedUrlResponse
                .builder()
                .presignedUrl(presignedUrl)
                .build();
        } else {
            throw new IllegalArgumentException("Don't have info of any files");
        }
    }

    private PresignedUrl generatePresignedUrl(FilePropertiesRequest file, String folderPath, FileAccess fileAccess){
        String keyName = generateKeyName(folderPath, file);
        String ACL = fileAccessService.getACL(fileAccess);
        PresignedRequestParams params = new PresignedRequestParams(file, keyName, ACL);
        var presignedPutUrl = generatePresignedPutUrl(params);

        return new PresignedUrl(
            file.id(),
            keyName,
            presignedPutUrl
        );
    }

    public String generateKeyName(String folder, FilePropertiesRequest file) {
        int attempts = 0;
        while (attempts < maxRetries) {
            String uuid = UUID.randomUUID().toString();
            String key = folder == null ?
                    String.format("%s_%s", uuid, file.fileName()) :
                    String.format("%s/%s_%s", folder, uuid, file.fileName());

            try {
                fileInfoValidator.validateUniqueKey(key);
                return key;
            } catch (ExistedS3FileKeyException e) {
                attempts++;
                log.warn("Key collision detected: {}. Retrying (attempt {}/{})", key, attempts, maxRetries);
                try {
                    TimeUnit.MILLISECONDS.sleep(retryDelayMillis);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Thread interrupted during retry delay", ie);
                }
            }
        }

        throw new RuntimeException("Failed to generate a unique key after " + maxRetries + " attempts");
    }

    public String generatePresignedGetUrl(FilePropertiesRequest file, String keyName){
        String bucketName = bucketConfig.getBucketName();

        GetObjectRequest objectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(10))  // The URL will expire in 10 minutes.
                .getObjectRequest(objectRequest)
                .build();

        PresignedGetObjectRequest presignedRequest = s3Presigner.presignGetObject(presignRequest);
        log.info("Presigned URL: [{}]", presignedRequest.url().toString());
        log.info("HTTP method: [{}]", presignedRequest.httpRequest().method());

        return presignedRequest.url().toExternalForm();
    }
    
    public String generatePresignedPutUrl(PresignedRequestParams presignedRequestParams){
        String keyName = presignedRequestParams.keyName();
        String ACL = presignedRequestParams.ACL();
        String bucketName = bucketConfig.getBucketName();

        AwsRequestOverrideConfiguration override = AwsRequestOverrideConfiguration
            .builder()
            .putRawQueryParameter(PresignedUrlHeaders.ACL.value(), ACL)
            .build();


        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                .overrideConfiguration(override)
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(10))  // The URL expires in 10 minutes.
                .putObjectRequest(objectRequest)
                .build();


        PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(presignRequest);
        String myURL = presignedRequest.url().toString();
        log.info("Presigned URL to upload a file to: [{}]", myURL);
        log.info("HTTP method: [{}]", presignedRequest.httpRequest().method());

        return presignedRequest.url().toExternalForm();
    }

}

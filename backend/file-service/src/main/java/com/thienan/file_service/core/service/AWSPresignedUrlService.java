package com.thienan.file_service.core.service;

import static java.lang.String.format;
import java.time.Duration;
import java.util.List;

import org.springframework.stereotype.Service;

import com.thienan.file_service.config.BucketConfig;
import com.thienan.file_service.core.dto.FileInfoRequest;
import com.thienan.file_service.core.dto.PresignedRequestParams;
import com.thienan.file_service.core.dto.PresignedUrl;
import com.thienan.file_service.core.dto.UploadPresignedUrlResponse;
import com.thienan.file_service.core.dto.UploadRequest;
import com.thienan.file_service.core.enumeration.FileAccess;
import com.thienan.file_service.core.enumeration.PresignedUrlHeaders;

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
    private final FileAccessService fileAccessService;
    private final BucketConfig bucketConfig;
    private final S3Presigner s3Presigner;
    
    public UploadPresignedUrlResponse generatePresignedUploadUrl(UploadRequest request) {
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

    private PresignedUrl generatePresignedUrl(FileInfoRequest file, String folderPath, FileAccess fileAccess){
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

    private String generateKeyName(String folder, FileInfoRequest file){
        if (file.key() == null) {
            return 
                folder == null ?
                file.fileName() :
                format("%s/%s", folder, file.fileName());
        }

        return
            folder == null ?
            file.key() :
            format("%s/%s", folder, file.key());
    }

    public String generatePresignedGetUrl(FileInfoRequest file, String keyName){
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

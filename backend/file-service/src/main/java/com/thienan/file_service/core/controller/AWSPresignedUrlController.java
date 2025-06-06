package com.thienan.file_service.core.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thienan.file_service.core.dto.GenerateUploadPresignedUrlRequest;
import com.thienan.file_service.core.dto.UploadPresignedUrlResponse;
import com.thienan.file_service.core.service.AWSPresignedUrlService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;



@RestController
@RequiredArgsConstructor
@RequestMapping("/presigned-urls")
public class AWSPresignedUrlController {
    private final AWSPresignedUrlService awsPresignedUrlService;
    
    @PostMapping
    public ResponseEntity<UploadPresignedUrlResponse> getUploadPresignedUrl(
        @RequestBody @Valid GenerateUploadPresignedUrlRequest request
    ) { 
        return ResponseEntity.ok(awsPresignedUrlService.generatePresignedUploadUrl(request));
    }
}

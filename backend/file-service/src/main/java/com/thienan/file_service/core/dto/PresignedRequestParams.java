package com.thienan.file_service.core.dto;

public record PresignedRequestParams(
    FileInfoRequest fileInfo,
    String keyName,
    String ACL
){}

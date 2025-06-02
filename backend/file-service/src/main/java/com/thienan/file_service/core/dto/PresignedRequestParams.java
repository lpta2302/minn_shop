package com.thienan.file_service.core.dto;

public record PresignedRequestParams(
    FilePropertiesRequest fileInfo,
    String keyName,
    String ACL
){}

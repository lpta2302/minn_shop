package com.thienan.file_service.core.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;

@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public record UploadPresignedUrlResponse(
    List<PresignedUrl> presignedUrls,
    PresignedUrl presignedUrl
) { }

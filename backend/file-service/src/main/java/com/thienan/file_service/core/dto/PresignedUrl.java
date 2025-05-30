package com.thienan.file_service.core.dto;

public record PresignedUrl(
    int id,
    String key,
    String url
) {
}

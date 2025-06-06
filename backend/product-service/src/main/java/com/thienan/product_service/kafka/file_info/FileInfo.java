package com.thienan.product_service.kafka.file_info;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FileInfo(
    Long id,
    String fileName,
    String key,
    Long size,
    String mimeType,
    String objectUrl
) {}

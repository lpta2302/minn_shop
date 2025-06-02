package com.thienan.file_service.core.dto;

import java.util.List;

public record FilePropertiesRequest(
    int id,
    String key,
    String fileName,
    List<Metadata> metadata
) {}

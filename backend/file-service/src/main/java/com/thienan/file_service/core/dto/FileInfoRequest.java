package com.thienan.file_service.core.dto;

import java.util.List;

public record FileInfoRequest(
    int id,
    String key,
    String fileName,
    List<Metadata> metadata
) {}

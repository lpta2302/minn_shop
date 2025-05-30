package com.thienan.file_service.core.dto;

import java.util.List;

import com.thienan.file_service.core.enumeration.FileAccess;

public record UploadRequest(
    String objectType,
    FileAccess fileAccess,
    List<FileInfoRequest> files,
    FileInfoRequest file
) {}

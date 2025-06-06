package com.thienan.file_service.core.dto;

import java.util.List;

import com.thienan.file_service.core.enumeration.FileAccess;
import com.thienan.file_service.core.enumeration.ObjectTypes;

public record GenerateUploadPresignedUrlRequest(
    ObjectTypes objectType,
    FileAccess fileAccess,
    List<FilePropertiesRequest> files,
    FilePropertiesRequest file
) {}

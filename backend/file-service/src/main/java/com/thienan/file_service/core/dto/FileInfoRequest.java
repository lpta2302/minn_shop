package com.thienan.file_service.core.dto;

import com.thienan.file_service.core.enumeration.ObjectTypes;

import jakarta.validation.constraints.NotBlank;
public record FileInfoRequest(
    ObjectTypes objectType,
    @NotBlank(message="Must have key for save file info")
    String key
) {
    
}

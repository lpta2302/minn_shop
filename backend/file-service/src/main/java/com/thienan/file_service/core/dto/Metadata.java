package com.thienan.file_service.core.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record Metadata(
    @NotNull
    @NotBlank
    String key,
    @NotNull
    @NotBlank
    String value
) {

}

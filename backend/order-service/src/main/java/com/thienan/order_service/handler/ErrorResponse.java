package com.thienan.order_service.handler;

import java.time.LocalDateTime;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse{
    private int status;
    private String errorCode;
    private String error;
    private String message;
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
    private String path;
    private Map<String, String> details;
}

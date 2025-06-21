package com.thienan.account_service.common;

import java.util.List;

import org.springframework.data.domain.Page;
import lombok.Builder;

@Builder
public record PageResponse<T>(
    int page,
    int totalPages,
    long totalElements,
    List<T> content,
    boolean isFirst,
    boolean isLast
) {
    public static <T> PageResponse<T> fromPage(Page<T> page){
        return new PageResponse<>(
            page.getNumber(), 
            page.getTotalPages(), 
            page.getTotalElements(), 
            page.getContent(), 
            page.isFirst(), 
            page.isLast());
    }

    public static <T> PageResponse<T> fromPage(Page<?> page, List<T> content){
        return new PageResponse<>(
            page.getNumber(), 
            page.getTotalPages(), 
            page.getTotalElements(), 
            content, 
            page.isFirst(), 
            page.isLast());
    }
}

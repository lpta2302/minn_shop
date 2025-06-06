package com.thienan.product_service.core.category;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
public final class Category {
    private Long id;
    private String code;
    private String name;
}

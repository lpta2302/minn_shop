package com.thienan.account_service.product;

import jakarta.persistence.Embeddable;

@Embeddable
public record ProductOption(
    Long id,
    String name
) {
    
}

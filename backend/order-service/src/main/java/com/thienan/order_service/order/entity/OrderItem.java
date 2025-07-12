package com.thienan.order_service.order.entity;

import com.thienan.order_service.common.BaseEntity;
import com.thienan.order_service.product.Stock;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItem extends BaseEntity{
    @Embedded
    private Stock stock;
    private int quantity;
}

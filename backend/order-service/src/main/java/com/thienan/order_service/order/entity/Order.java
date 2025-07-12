package com.thienan.order_service.order.entity;

import java.util.List;

import com.thienan.order_service.common.BaseEntity;
import com.thienan.order_service.customer.Customer;
import com.thienan.order_service.order.enumeration.OrderStatus;
import com.thienan.order_service.payment.PaymentMethod;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import static jakarta.persistence.CascadeType.ALL;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
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
@Table(name = "orders")
public class Order extends BaseEntity {
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="id", column=@Column(name="customer_id")),
        @AttributeOverride(name="email", column=@Column(name="customer_email")),
        @AttributeOverride(name="phoneNumber", column=@Column(name="customer_phone_number")),
        @AttributeOverride(name="fullName", column=@Column(name="customer_full_name"))
    })
    private Customer customer;
    private String shippingAddress;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @OneToMany(fetch=FetchType.EAGER, cascade=ALL)
    private List<OrderItem> items;

    @Default
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;
}

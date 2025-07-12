package com.thienan.order_service.order.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.thienan.order_service.order.entity.Order;
import com.thienan.order_service.order.enumeration.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findAllByCustomerId(Long customerId, Pageable pageable);

    @Modifying
    @Query("""
       update Order o
       set o.status = :orderStatus
       where o.id = :orderId     
    """)
    void updateStatusById(OrderStatus orderStatus, Long orderId);
    
}

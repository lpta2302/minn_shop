package com.thienan.order_service.order.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.thienan.order_service.common.PageResponse;
import com.thienan.order_service.customer.CustomerClient;
import com.thienan.order_service.handler.exceptions.common.EntityNotFoundByIDException;
import com.thienan.order_service.order.dto.OrderItemRequest;
import com.thienan.order_service.order.dto.OrderItemsAvailabilityResponse;
import com.thienan.order_service.order.dto.OrderRequest;
import com.thienan.order_service.order.entity.Order;
import com.thienan.order_service.order.entity.OrderItem;
import com.thienan.order_service.order.enumeration.OrderStatus;
import com.thienan.order_service.order.repository.OrderRepository;
import com.thienan.order_service.product.Stock;
import com.thienan.order_service.product.StockClient;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final StockClient stockClient;
    private final CustomerClient customerClient;
    private final OrderRepository orderRepository;

    public Long createOrder(Long customerId, OrderRequest request) {
        // Get customer
        var customer = customerClient.findCustomerById(customerId);
        
        OrderItemsAvailabilityResponse checkResponse = stockClient.checkOrderItemAvailability(request.items());
        if (!checkResponse.isAvailable()) {
            throw new IllegalArgumentException(checkResponse.message());
        }

        List<Stock> stocks = stockClient.findStocksWithBriefDetail(request.items());

        List<OrderItem> items = 
            request.items()
                .stream()
                .map(item-> OrderItem
                    .builder()
                    .stock(
                        stocks
                            .stream()
                            .filter(stock->stock.productVariantId().equals(item.productVariantId()) 
                                && stock.stockOptionValueId().equals(item.stockOptionValueId()))
                            .findFirst()
                            .orElseThrow(()->new EntityNotFoundByIDException("Stock", 
                                String.format("product variant id: %d stock option value id: %d", 
                                item.productVariantId(), item.stockOptionValueId()))))
                    .quantity(item.quantity())
                    .build())
                .collect(Collectors.toList());
                
        Order newOrder = 
            Order
            .builder()
            .shippingAddress(request.shippingAddress())
            .customer(customer)
            .paymentMethod(request.paymentMethod())
            .items(items)
            .build();
        
        return orderRepository.save(newOrder).getId();
    }

    public PageResponse<Order> getOwnOrders(Long customerId, Pageable pageable) {
        return PageResponse.fromPage(
            orderRepository.findAllByCustomerId(customerId, pageable)
        );
    }
    
    @Transactional
    public Long changeOrderStatus(Long orderId, OrderStatus orderStatus){
        if (orderStatus == OrderStatus.ACCEPTED) {
            var order = findById(orderId);
            List<OrderItemRequest> itemRequests = 
                order.getItems()
                    .stream()
                    .map(item->
                        OrderItemRequest
                            .builder()
                            .productVariantId(item.getStock().productVariantId())
                            .stockOptionValueId(item.getStock().stockOptionValueId())
                            .quantity(item.getQuantity())
                            .build())
                    .collect(Collectors.toList());
            
            var deductStockResponse = stockClient.deductStock(itemRequests);
            if (!deductStockResponse.isAvailable()) {
                throw new IllegalArgumentException(deductStockResponse.message());
            }
        }
        orderRepository.updateStatusById(orderStatus, orderId);

        return orderId;
    }

    public Order findById(Long orderId){
        return orderRepository.findById(orderId)
            .orElseThrow(()-> new EntityNotFoundByIDException("Order", orderId.toString()));
    }
}

package com.delivery.logistics.order.controller;

import com.delivery.logistics.order.dto.CreateOrderRequest;
import com.delivery.logistics.order.dto.OrderResponse;
import com.delivery.logistics.order.dto.UpdateOrderStatusRequest;
import com.delivery.logistics.order.mapper.OrderMapper;
import com.delivery.logistics.order.model.Order;
import com.delivery.logistics.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static com.delivery.logistics.order.mapper.OrderMapper.toResponse;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/customers/{customerId}/orders")
    public OrderResponse createOrder(@PathVariable UUID customerId, @RequestBody @Valid CreateOrderRequest createOrderRequest) {
        return toResponse(orderService.createOrder(customerId, createOrderRequest));

    }

    @PatchMapping("/customers/orders/{orderId}/status")
    public OrderResponse updateOrder(@PathVariable UUID orderId, @RequestBody @Valid UpdateOrderStatusRequest updateOrderStatusRequest) {
        return toResponse(orderService.updateOrderStatus(orderId, updateOrderStatusRequest));
    }

    @GetMapping("/customers/orders/{orderId}")
    public OrderResponse getOrderById(@PathVariable UUID orderId) {
        return  toResponse(orderService.getOrderById(orderId));
    }

    @GetMapping("/customers/{customerId}/orders")
    public List<OrderResponse> getOrderByCustomer(@PathVariable UUID customerId) {

        return orderService.getOrdersByCustomer(customerId)
                .stream()
                .map(OrderMapper::toResponse)
                .toList();
    }

    @GetMapping("/customers/orders/number/{orderNumber}")
    public OrderResponse getOrderByOrderNumber(@PathVariable String orderNumber) {
        return  toResponse(orderService.getOrderByOrderNumber(orderNumber));
    }
}

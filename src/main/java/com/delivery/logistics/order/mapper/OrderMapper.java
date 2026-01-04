package com.delivery.logistics.order.mapper;

import com.delivery.logistics.order.dto.OrderResponse;
import com.delivery.logistics.order.model.Order;


public class OrderMapper {

    public static OrderResponse toResponse(Order order) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(order.getId());
        orderResponse.setOrderNumber(order.getOrderNumber());
        orderResponse.setCustomerId(order.getCustomer().getId());
        orderResponse.setDeliveryCity(order.getDeliveryCity());
        orderResponse.setDeliveryCountry(order.getDeliveryCountry());
        orderResponse.setDeliveryState(order.getDeliveryState());
        orderResponse.setDeliveryLatitude(order.getDeliveryLatitude());
        orderResponse.setDeliveryLongitude(order.getDeliveryLongitude());
        orderResponse.setDeliveryStreet(order.getDeliveryStreet());
        orderResponse.setCreatedAt(order.getCreatedAt());
        orderResponse.setUpdatedAt(order.getUpdatedAt());
        orderResponse.setStatus(order.getStatus());
        orderResponse.setDeliveryPincode(order.getDeliveryPincode());
        return orderResponse;
    }
}

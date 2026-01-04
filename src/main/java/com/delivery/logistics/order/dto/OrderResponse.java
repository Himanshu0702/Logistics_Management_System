package com.delivery.logistics.order.dto;


import com.delivery.logistics.order.model.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private UUID id;

    private String orderNumber;

    private UUID customerId;

    private String deliveryStreet;

    private String deliveryCity;

    private String deliveryState;

    private String deliveryPincode;

    private String deliveryCountry;

    private Double deliveryLatitude;

    private Double deliveryLongitude;

    private OrderStatus status;

    private Instant createdAt;

    private Instant updatedAt;
}

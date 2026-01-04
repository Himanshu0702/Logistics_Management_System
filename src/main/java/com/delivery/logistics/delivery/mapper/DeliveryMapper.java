package com.delivery.logistics.delivery.mapper;

import com.delivery.logistics.delivery.dto.DeliveryResponse;
import com.delivery.logistics.delivery.model.Delivery;

public class DeliveryMapper {

    public static DeliveryResponse toResponse(Delivery delivery) {
        DeliveryResponse deliveryResponse = new DeliveryResponse();
        deliveryResponse.setId(delivery.getId());
        deliveryResponse.setOrderId(delivery.getOrder().getId());
        deliveryResponse.setCreatedAt(delivery.getCreatedAt());
        deliveryResponse.setUpdatedAt(delivery.getUpdatedAt());
        deliveryResponse.setStatus(delivery.getStatus());
        deliveryResponse.setDeliveryAgent(delivery.getDeliveryAgent());
        return deliveryResponse;
    }
}

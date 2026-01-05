package com.delivery.logistics.notification.event;


import java.util.UUID;

public record OrderConfirmedEvent(UUID customerId, UUID orderId, String orderNumber) {
}

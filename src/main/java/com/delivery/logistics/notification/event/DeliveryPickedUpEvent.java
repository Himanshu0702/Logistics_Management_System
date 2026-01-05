package com.delivery.logistics.notification.event;

import java.util.UUID;

public record DeliveryPickedUpEvent(UUID customerId, UUID deliveryId) {
}

package com.delivery.logistics.notification.event;

import java.util.UUID;

public record DeliveryAssignedEvent(UUID agentId, UUID deliveryId, UUID orderId) {
}

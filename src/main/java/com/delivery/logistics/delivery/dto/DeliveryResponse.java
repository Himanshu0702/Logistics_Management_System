package com.delivery.logistics.delivery.dto;


import com.delivery.logistics.agent.model.DeliveryAgent;
import com.delivery.logistics.delivery.model.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryResponse {

    private UUID id;

    private UUID orderId;

    private DeliveryStatus status;

    private DeliveryAgent deliveryAgent;

    private Instant createdAt;

    private Instant updatedAt;
}

package com.delivery.logistics.delivery.dto;

import com.delivery.logistics.delivery.model.DeliveryStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDeliveryStatusRequest {

    @NotNull
    private DeliveryStatus status;

    private UUID deliveryAgentId;


}

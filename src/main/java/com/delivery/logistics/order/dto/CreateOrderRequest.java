package com.delivery.logistics.order.dto;

import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {

    @NotNull
    private UUID addressId;
}

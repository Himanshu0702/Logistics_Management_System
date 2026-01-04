package com.delivery.logistics.customer.address.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponse {

    private UUID id;

    private String street;

    private String city;

    private String state;

    private String pincode;

    private String country;

    private Boolean isDefault;

    private Double latitude;

    private Double longitude;

    private Instant createdAt;
}

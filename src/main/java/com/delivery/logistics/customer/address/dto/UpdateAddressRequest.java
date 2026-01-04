package com.delivery.logistics.customer.address.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAddressRequest {

    private String street;

    private String city;

    private String state;

    private String pincode;

    private String country;

    private Boolean isDefault;

    private Double latitude;

    private Double longitude;
}

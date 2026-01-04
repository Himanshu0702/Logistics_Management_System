package com.delivery.logistics.customer.address.mapper;

import com.delivery.logistics.customer.address.dto.AddressResponse;
import com.delivery.logistics.customer.address.model.Address;

public class AddressMapper {

    public static AddressResponse toResponse(Address address) {
        AddressResponse response = new AddressResponse();
        response.setId(address.getId());
        response.setCity(address.getCity());
        response.setCountry(address.getCountry());
        response.setStreet(address.getStreet());
        response.setState(address.getState());
        response.setPincode(address.getPincode());
        response.setCreatedAt(address.getCreatedAt());
        response.setLatitude(address.getLatitude());
        response.setLongitude(address.getLongitude());
        response.setIsDefault(address.isDefault());
        return response;
    }
}

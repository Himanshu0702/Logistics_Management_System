package com.delivery.logistics.customer.address.controller;

import com.delivery.logistics.common.exception.AddressNotFoundException;
import com.delivery.logistics.customer.address.dto.AddressResponse;
import com.delivery.logistics.customer.address.dto.CreateAddressRequest;
import com.delivery.logistics.customer.address.dto.UpdateAddressRequest;
import com.delivery.logistics.customer.address.mapper.AddressMapper;
import com.delivery.logistics.customer.address.model.Address;
import com.delivery.logistics.customer.address.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.delivery.logistics.customer.address.mapper.AddressMapper.toResponse;

@RestController
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/customers/{customerId}/addresses")
    public AddressResponse createAddress(@PathVariable UUID customerID,@RequestBody @Valid CreateAddressRequest createAddressRequest) {
        Address address = addressService.createAddress(customerID, createAddressRequest);
        return toResponse(address);
    }

    @PatchMapping("/customers/addresses/{addressId}")
    public AddressResponse updateAddress(@PathVariable UUID addressID,@RequestBody @Valid UpdateAddressRequest updateAddressRequest) {
        Address address = addressService.updateAddress(addressID, updateAddressRequest);
        return toResponse(address);
    }

    @GetMapping("/customers/{customerId}/addresses")
    public List<AddressResponse> getAllAddresses(@PathVariable UUID customerID) {
        List<Address> addresses = addressService.findAllAddressesByCustomer(customerID);
        return addressService.findAllAddressesByCustomer(customerID)
                .stream()
                .map(AddressMapper::toResponse)
                .toList();
    }

    @DeleteMapping("/customers/addresses/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable UUID addressID) {
        addressService.deleteAddress(addressID);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/customers/{customerID}/addresses/default")
    public AddressResponse getDefaultAddress(@PathVariable UUID customerID) {
        Address address = addressService.getDefaultAddress(customerID);
        if(address == null) {
            throw new AddressNotFoundException("Address not found");
        }
        return toResponse(address);
    }

}

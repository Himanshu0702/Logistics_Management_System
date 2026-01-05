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
    public AddressResponse createAddress(@PathVariable UUID customerId,@RequestBody @Valid CreateAddressRequest createAddressRequest) {
        Address address = addressService.createAddress(customerId, createAddressRequest);
        return toResponse(address);
    }

    @PatchMapping("/customers/addresses/{addressId}")
    public AddressResponse updateAddress(@PathVariable UUID addressId,@RequestBody @Valid UpdateAddressRequest updateAddressRequest) {
        Address address = addressService.updateAddress(addressId, updateAddressRequest);
        return toResponse(address);
    }

    @GetMapping("/customers/{customerId}/addresses")
    public List<AddressResponse> getAllAddresses(@PathVariable UUID customerId) {
        List<Address> addresses = addressService.findAllAddressesByCustomer(customerId);
        return addressService.findAllAddressesByCustomer(customerId)
                .stream()
                .map(AddressMapper::toResponse)
                .toList();
    }

    @DeleteMapping("/customers/addresses/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable UUID addressId) {
        addressService.deleteAddress(addressId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/customers/{customerId}/addresses/default")
    public AddressResponse getDefaultAddress(@PathVariable UUID customerId) {
        Address address = addressService.getDefaultAddress(customerId);
        if(address == null) {
            throw new AddressNotFoundException("Address not found");
        }
        return toResponse(address);
    }

}

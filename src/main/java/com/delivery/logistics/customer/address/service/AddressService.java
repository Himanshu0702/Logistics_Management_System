package com.delivery.logistics.customer.address.service;

import com.delivery.logistics.common.exception.AddressNotFoundException;
import com.delivery.logistics.common.exception.CustomerNotFoundException;
import com.delivery.logistics.customer.address.dto.CreateAddressRequest;
import com.delivery.logistics.customer.address.dto.UpdateAddressRequest;
import com.delivery.logistics.customer.address.model.Address;
import com.delivery.logistics.customer.address.repository.AddressRepository;

import com.delivery.logistics.customer.model.Customer;
import com.delivery.logistics.customer.repository.CustomerRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;

    public AddressService(AddressRepository addressRepository,  CustomerRepository customerRepository) {
        this.addressRepository = addressRepository;
        this.customerRepository = customerRepository;
    }

    public List<Address> findAllAddressesByCustomer(UUID customerID) {
        Customer customer = customerRepository.findById(customerID).orElse(null);
        if(customer == null) {
            throw new CustomerNotFoundException("Customer not found");
        }
        return addressRepository.findByCustomerId(customerID);
    }

    public Address getDefaultAddress(UUID customerID) {
        Customer customer = customerRepository.findById(customerID).orElse(null);
        if(customer == null) {
            throw new CustomerNotFoundException("Customer not found");
        }
        if(addressRepository.findByCustomerIdAndIsDefaultTrue(customerID) == null) {
            throw new AddressNotFoundException("Address not found");
        }
        return addressRepository.findByCustomerIdAndIsDefaultTrue(customerID);
    }

    @Transactional
    public Address createAddress(UUID customerID ,CreateAddressRequest createAddressRequest) {
        
        Customer customer = customerRepository.findById(customerID).orElse(null);
        if(customer == null) {
             throw new CustomerNotFoundException("Customer not found");
        }

        if(Boolean.TRUE.equals(createAddressRequest.getIsDefault())) {
            Address address = addressRepository.findByCustomerIdAndIsDefaultTrue(customerID);
            if(address != null){
                address.setDefault(false);
                addressRepository.save(address);
            }
        }
        Address newAddress = buildAddress(createAddressRequest, customer);

        return addressRepository.save(newAddress);
    }

    private static @NonNull Address buildAddress(CreateAddressRequest createAddressRequest, Customer customer) {
        Address newAddress = new Address();
        newAddress.setState(createAddressRequest.getState());
        newAddress.setStreet(createAddressRequest.getStreet());
        newAddress.setCity(createAddressRequest.getCity());
        newAddress.setCountry(createAddressRequest.getCountry());
        newAddress.setLatitude(createAddressRequest.getLatitude());
        newAddress.setLongitude(createAddressRequest.getLongitude());
        newAddress.setPincode(createAddressRequest.getPincode());
        newAddress.setDefault(createAddressRequest.getIsDefault());
        newAddress.setCustomer(customer);
        return newAddress;
    }

    @Transactional
    public Address updateAddress(UUID addressID , UpdateAddressRequest updateAddressRequest) {
        Address address = addressRepository.findById(addressID).orElseThrow(() -> new AddressNotFoundException("Address not found")) ;
        if(Boolean.TRUE.equals(updateAddressRequest.getIsDefault())){

            Address defaultAddress = addressRepository.findByCustomerIdAndIsDefaultTrue(address.getCustomer().getId());
            if (defaultAddress != null && !defaultAddress.getId().equals(address.getId())) {
                defaultAddress.setDefault(false);
                addressRepository.save(defaultAddress);
            }
        }
        if (updateAddressRequest.getStreet() != null && !updateAddressRequest.getStreet().isEmpty()) {
            address.setStreet(updateAddressRequest.getStreet());
        }
        if (updateAddressRequest.getCity() != null && !updateAddressRequest.getCity().isEmpty()) {
            address.setCity(updateAddressRequest.getCity());
        }
        if (updateAddressRequest.getCountry() != null && !updateAddressRequest.getCountry().isEmpty()) {
            address.setCountry(updateAddressRequest.getCountry());
        }
        if(updateAddressRequest.getLatitude() != null){
            address.setLatitude(updateAddressRequest.getLatitude());
        }
        if(updateAddressRequest.getLongitude() != null){
            address.setLongitude(updateAddressRequest.getLongitude());
        }
        if(updateAddressRequest.getPincode() != null){
            address.setPincode(updateAddressRequest.getPincode());
        }
        if(updateAddressRequest.getIsDefault() != null){
            address.setDefault(updateAddressRequest.getIsDefault());
        }

        return addressRepository.save(address);
    }

    @Transactional
    public void deleteAddress(UUID addressID){
        if (!addressRepository.existsById(addressID)) {
            throw new AddressNotFoundException("Address not found");
        }
        addressRepository.deleteById(addressID);
    }

}

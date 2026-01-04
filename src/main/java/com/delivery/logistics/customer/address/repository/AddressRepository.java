package com.delivery.logistics.customer.address.repository;

import com.delivery.logistics.customer.address.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {

    List<Address> findByCustomerId(UUID customerId);

    Address findByCustomerIdAndIsDefaultTrue(UUID customerId);

}

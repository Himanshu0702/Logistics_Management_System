package com.delivery.logistics.customer.mapper;


import com.delivery.logistics.customer.dto.CustomerResponse;
import com.delivery.logistics.customer.model.Customer;

public class CustomerMapper {

        public static CustomerResponse toResponse(Customer customer) {
            CustomerResponse response = new CustomerResponse();
            response.setId(customer.getId());
            response.setFirstName(customer.getFirstName());
            response.setLastName(customer.getLastName());
            response.setEmail(customer.getEmail());
            response.setPhone(customer.getPhone());
            response.setDateOfBirth(customer.getDateOfBirth());
            response.setCreatedAt(customer.getCreatedAt());
            return response;
        }

}

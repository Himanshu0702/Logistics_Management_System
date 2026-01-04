package com.delivery.logistics.customer.dto;


import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateCustomerRequest {


    private String firstName;
    private String lastName;
    private String phone;
    @Past
    private LocalDate dateOfBirth;

}

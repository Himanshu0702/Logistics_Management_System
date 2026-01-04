package com.delivery.logistics.customer.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateCustomerRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Past
    private LocalDate dateOfBirth;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min=8)
    private String password;

    private String phone;

}

package com.delivery.logistics.customer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Customer {

    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private LocalDate dateOfBirth;

    @Email
    @NotBlank
    @Column(unique = true, nullable = false)
    private String email;

    @JsonIgnore
    private String password;

    private String phone;

    @CreationTimestamp
    private Instant createdAt;
}

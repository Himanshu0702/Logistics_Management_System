package com.delivery.logistics.agent.repository;

import com.delivery.logistics.agent.model.DeliveryAgent;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;
import java.util.UUID;

public interface AgentRepository extends JpaRepository<DeliveryAgent, UUID> {

    Optional<DeliveryAgent> findByEmail(String email);

    boolean existsByEmail(@Email String email);

    boolean existsByPhone(@NotBlank String phone);
}

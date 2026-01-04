package com.delivery.logistics.delivery.repository;

import com.delivery.logistics.delivery.model.Delivery;
import com.delivery.logistics.delivery.model.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeliveryRepository extends JpaRepository<Delivery, UUID> {

    Optional<Delivery> findByOrderId(UUID orderId);

    Boolean existsByOrderId(UUID orderId);

    List<Delivery> findByDeliveryAgent_Id(UUID agentId);

}

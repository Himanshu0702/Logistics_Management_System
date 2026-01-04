package com.delivery.logistics.order.repository;

import com.delivery.logistics.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID>{

    List<Order> findByCustomerId(UUID customerId);

    boolean existsByOrderNumber(String orderNumber);

    Order findByOrderNumber(String orderNumber);
}

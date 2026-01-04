package com.delivery.logistics.delivery.model;


import com.delivery.logistics.agent.model.DeliveryAgent;
import com.delivery.logistics.order.model.Order;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="deliveries")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @NotNull
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    @ManyToOne
    @JoinColumn(name = "delivery_agent_id")
    private DeliveryAgent deliveryAgent;


    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;
}

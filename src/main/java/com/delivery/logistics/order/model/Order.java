package com.delivery.logistics.order.model;

import com.delivery.logistics.customer.model.Customer;
import jakarta.persistence.*;
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
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String orderNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(nullable = false)
    private String deliveryStreet;

    @Column(nullable = false)
    private String deliveryCity;

    @Column(nullable = false)
    private String deliveryState;

    @Column(nullable = false)
    private String deliveryPincode;

    @Column(nullable = false)
    private String deliveryCountry;

    @Column(nullable = false)
    private Double deliveryLatitude;

    @Column(nullable = false)
    private Double deliveryLongitude;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

}

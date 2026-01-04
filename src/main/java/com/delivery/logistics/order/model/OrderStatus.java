package com.delivery.logistics.order.model;

import java.util.EnumSet;
import java.util.Set;


public enum OrderStatus {
    CREATED,
    CONFIRMED,
    ASSIGNED,
    OUT_FOR_DELIVERY,
    DELIVERED,
    CANCELLED;

    static {
        CREATED.allowedNextStatuses =
                EnumSet.of(CONFIRMED, CANCELLED);

        CONFIRMED.allowedNextStatuses =
                EnumSet.of(ASSIGNED, CANCELLED);

        ASSIGNED.allowedNextStatuses =
                EnumSet.of(OUT_FOR_DELIVERY);

        OUT_FOR_DELIVERY.allowedNextStatuses =
                EnumSet.of(DELIVERED);

        DELIVERED.allowedNextStatuses =
                EnumSet.noneOf(OrderStatus.class);

        CANCELLED.allowedNextStatuses =
                EnumSet.noneOf(OrderStatus.class);
    }


    private Set<OrderStatus> allowedNextStatuses;

    public boolean canTransitionTo(OrderStatus targetStatus) {
        return allowedNextStatuses.contains(targetStatus);
    }
}



package com.delivery.logistics.delivery.model;

import java.util.EnumSet;
import java.util.Set;


public enum DeliveryStatus{

    CREATED,
    FAILED,
    ASSIGNED,
    PICKED_UP,
    DELIVERED,
    CANCELLED,
    OUT_FOR_DELIVERY;

    static {
        CREATED.allowedNextStatuses =
                EnumSet.of(ASSIGNED, CANCELLED);

        ASSIGNED.allowedNextStatuses =
                EnumSet.of(PICKED_UP, CANCELLED);

        PICKED_UP.allowedNextStatuses =
                EnumSet.of(OUT_FOR_DELIVERY);

        OUT_FOR_DELIVERY.allowedNextStatuses =
                EnumSet.of(DELIVERED, FAILED);

        DELIVERED.allowedNextStatuses =
                EnumSet.noneOf(DeliveryStatus.class);

        CANCELLED.allowedNextStatuses =
                EnumSet.noneOf(DeliveryStatus.class);

        FAILED.allowedNextStatuses =
                EnumSet.noneOf(DeliveryStatus.class);
    }


    private Set<DeliveryStatus> allowedNextStatuses;

    public boolean canTransitionTo(DeliveryStatus targetStatus) {
        return allowedNextStatuses != null && allowedNextStatuses.contains(targetStatus);
    }
}


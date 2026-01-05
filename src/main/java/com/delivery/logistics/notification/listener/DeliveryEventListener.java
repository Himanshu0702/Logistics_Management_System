package com.delivery.logistics.notification.listener;

import com.delivery.logistics.notification.dto.NotificationRequest;
import com.delivery.logistics.notification.enums.NotificationType;
import com.delivery.logistics.notification.event.DeliveryAssignedEvent;
import com.delivery.logistics.notification.event.DeliveryPickedUpEvent;
import com.delivery.logistics.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryEventListener {

    private final  NotificationService notificationService;

    @EventListener
    void handleDeliveryAssigned(DeliveryAssignedEvent event)
    {
        NotificationRequest notificationRequest = NotificationRequest.builder()
                .userId(event.agentId())
                .type(NotificationType.DELIVERY_ASSIGNED)
                .message("A new delivery has been assigned to you.")
                .build();

        notificationService.send(notificationRequest);
    }

    @EventListener
    void handleDeliveryPickedUp(DeliveryPickedUpEvent event)
    {
        NotificationRequest notificationRequest = NotificationRequest.builder()
                .userId(event.customerId())
                .type(NotificationType.DELIVERY_PICKED_UP)
                .message("Your order has been picked up and is on the way.")
                .build();

        notificationService.send(notificationRequest);
    }
}

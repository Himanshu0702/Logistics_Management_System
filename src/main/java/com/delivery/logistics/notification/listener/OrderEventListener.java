package com.delivery.logistics.notification.listener;

import com.delivery.logistics.notification.dto.NotificationRequest;
import com.delivery.logistics.notification.enums.NotificationType;
import com.delivery.logistics.notification.event.OrderConfirmedEvent;
import com.delivery.logistics.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventListener {

    private final NotificationService notificationService;

    @EventListener
    void handleOrderConfirmed(OrderConfirmedEvent orderConfirmedEvent) {
        NotificationRequest notificationRequest = NotificationRequest.builder()
                        .userId(orderConfirmedEvent.customerId())
                                .type(NotificationType.ORDER_CONFIRMED)
                                        .message("Your order " + orderConfirmedEvent.orderNumber() + " has been confirmed.")
                                                .build();

        notificationService.send(notificationRequest);
    }
}

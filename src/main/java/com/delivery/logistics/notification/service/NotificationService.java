package com.delivery.logistics.notification.service;

import com.delivery.logistics.notification.dto.NotificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationService {

    public void send(NotificationRequest notificationRequest) {
        log.info("NOTIFICATION | {}", notificationRequest);
    }
}

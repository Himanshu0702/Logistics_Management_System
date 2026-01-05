package com.delivery.logistics.notification.dto;

import com.delivery.logistics.notification.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationRequest {

    private UUID userId;

    private NotificationType type;

    private String message;
}

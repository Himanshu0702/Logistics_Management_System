package com.delivery.logistics.common.exception;

public class BadDeliveryRequestException extends RuntimeException {
    public BadDeliveryRequestException(String message) {
        super(message);
    }
}

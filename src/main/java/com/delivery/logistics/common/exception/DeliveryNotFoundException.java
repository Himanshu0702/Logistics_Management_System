package com.delivery.logistics.common.exception;

public class DeliveryNotFoundException extends  RuntimeException {
    public DeliveryNotFoundException(String message) {
        super(message);
    }
}

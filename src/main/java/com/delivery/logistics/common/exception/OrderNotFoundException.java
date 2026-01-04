package com.delivery.logistics.common.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String orderDoesNotExist) {
        super(orderDoesNotExist);
    }
}

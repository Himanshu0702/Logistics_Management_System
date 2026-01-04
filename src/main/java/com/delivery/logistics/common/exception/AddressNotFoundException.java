package com.delivery.logistics.common.exception;

public class AddressNotFoundException extends RuntimeException {
    public AddressNotFoundException(String message) {
        super(message);
    }
}

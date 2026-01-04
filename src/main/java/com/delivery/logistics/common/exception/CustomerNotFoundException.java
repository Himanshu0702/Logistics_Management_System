package com.delivery.logistics.common.exception;


public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(String message) {
        super(message);
    }
}

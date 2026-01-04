package com.delivery.logistics.common.exception;

public class PhoneNumberAlreadyExistsException extends RuntimeException {
    public PhoneNumberAlreadyExistsException(String phoneAlreadyExists) {
        super(phoneAlreadyExists);
    }
}

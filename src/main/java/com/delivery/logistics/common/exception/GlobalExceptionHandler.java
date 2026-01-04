package com.delivery.logistics.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<?> handleCustomerNotFound(CustomerNotFoundException ex) {
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<?> handleEmailExists(EmailAlreadyExistsException ex) {
        return buildError(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<?> handleAddressNotFound(AddressNotFoundException ex) {
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> handleUnauthorized(UnauthorizedException ex) {
        return buildError(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<?> handleOrderNotFound(OrderNotFoundException ex) {
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(BadOrderRequestException.class)
    public ResponseEntity<?> handleBadOrderRequest(BadOrderRequestException ex) {
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(BadDeliveryRequestException.class)
    public ResponseEntity<?> handleBadDeliveryRequest(BadDeliveryRequestException ex) {
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(DeliveryNotFoundException.class)
    public ResponseEntity<?> handleDeliveryNotFound(DeliveryNotFoundException ex) {
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(AgentAlreadyExistsException.class)
    public ResponseEntity<?> handleAgentAlreadyExists(AgentAlreadyExistsException ex) {
        return buildError(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(AgentNotFoundException.class)
    public ResponseEntity<?> handleAgentNotFound(AgentNotFoundException ex) {
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(BadAgentRequestException.class)
    public ResponseEntity<?> handleBadAgentRequest(BadAgentRequestException ex) {
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(PhoneNumberAlreadyExistsException.class)
    public ResponseEntity<?> handlePhoneNumberAlreadyExists(PhoneNumberAlreadyExistsException ex) {
        return buildError(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage())
                );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }

    private ResponseEntity<?> buildError(HttpStatus status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);

        return ResponseEntity.status(status).body(body);
    }
}

package com.delivery.logistics.delivery.controller;

import com.delivery.logistics.delivery.dto.CreateDeliveryRequest;
import com.delivery.logistics.delivery.dto.DeliveryResponse;
import com.delivery.logistics.delivery.dto.UpdateDeliveryStatusRequest;
import com.delivery.logistics.delivery.mapper.DeliveryMapper;
import com.delivery.logistics.delivery.service.DeliveryService;
import jakarta.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.delivery.logistics.delivery.mapper.DeliveryMapper.toResponse;

@RestController
public class DeliveryController {

    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping("/customers/deliveries")
    public DeliveryResponse createDelivery(@RequestBody @Valid CreateDeliveryRequest createDeliveryRequest) {
          return toResponse(deliveryService.createDelivery(createDeliveryRequest));
    }

    @PatchMapping("/agents/me/orders/{orderId}/delivery/status")
    public DeliveryResponse updateDeliveryStatus(@PathVariable UUID orderId,@RequestBody @Valid UpdateDeliveryStatusRequest updateDeliveryStatusRequest) {

        UUID agentId = (UUID) Objects.requireNonNull(SecurityContextHolder.getContext()
                        .getAuthentication())
                .getPrincipal();

        return  toResponse(deliveryService.updateDeliveryStatus(orderId, updateDeliveryStatusRequest, agentId));
    }

    @GetMapping("/customers/orders/{orderId}/delivery")
    public DeliveryResponse getDeliveryByOrderId(@PathVariable UUID orderId) {
        return toResponse(deliveryService.getDeliveryByOrderId(orderId));
    }

    @GetMapping("/agents/deliveries/{deliveryId}")
    public DeliveryResponse getDeliveryById(@PathVariable UUID deliveryId) {
        return toResponse(deliveryService.getDeliveryById(deliveryId));
    }

    @GetMapping("/agents/{agentId}/deliveries")
    public List<DeliveryResponse> getDeliveryByAgent(@PathVariable UUID agentId) {
        return  deliveryService.getDeliveriesByAgent(agentId)
                .stream()
                .map(DeliveryMapper::toResponse)
                .toList();
    }

}

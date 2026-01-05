package com.delivery.logistics.delivery.service;


import com.delivery.logistics.agent.model.AgentStatus;
import com.delivery.logistics.agent.model.DeliveryAgent;
import com.delivery.logistics.agent.repository.AgentRepository;
import com.delivery.logistics.common.exception.*;
import com.delivery.logistics.delivery.dto.CreateDeliveryRequest;
import com.delivery.logistics.delivery.dto.UpdateDeliveryStatusRequest;
import com.delivery.logistics.delivery.model.Delivery;
import com.delivery.logistics.delivery.model.DeliveryStatus;
import com.delivery.logistics.delivery.repository.DeliveryRepository;
import com.delivery.logistics.notification.event.DeliveryAssignedEvent;
import com.delivery.logistics.notification.event.DeliveryPickedUpEvent;
import com.delivery.logistics.order.model.Order;
import com.delivery.logistics.order.model.OrderStatus;
import com.delivery.logistics.order.repository.OrderRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final OrderRepository orderRepository;
    private final AgentRepository agentRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public DeliveryService(DeliveryRepository deliveryRepository, OrderRepository orderRepository,  AgentRepository agentRepository,  ApplicationEventPublisher applicationEventPublisher) {
        this.deliveryRepository = deliveryRepository;
        this.orderRepository = orderRepository;
        this.agentRepository = agentRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Transactional
    public Delivery createDelivery(CreateDeliveryRequest createDeliveryRequest) {
        Order order =  orderRepository.findById(createDeliveryRequest.getOrderId()).orElseThrow(()-> new OrderNotFoundException("Order not found"));

        Delivery delivery = new Delivery();

        if(order.getStatus() != OrderStatus.CONFIRMED) {
            throw new BadDeliveryRequestException("Delivery can be created only for CONFIRMED orders");
        }
        if(deliveryRepository.existsByOrderId(createDeliveryRequest.getOrderId())) {
            throw new BadDeliveryRequestException("Delivery already exists");
        }
        delivery.setOrder(order);
        delivery.setStatus(DeliveryStatus.CREATED);

        return deliveryRepository.save(delivery);
    }

    @Transactional
    public Delivery updateDeliveryStatus(UUID orderId, UpdateDeliveryStatusRequest  updateDeliveryStatusRequest, UUID agentIdFromToken) {

        Delivery delivery =  deliveryRepository.findByOrderId(orderId).orElseThrow(()-> new DeliveryNotFoundException("Delivery not found"));

        if ((delivery.getDeliveryAgent() == null ||
                !delivery.getDeliveryAgent().getId().equals(agentIdFromToken)) && !delivery.getStatus().equals(DeliveryStatus.CREATED)) {
            throw new UnauthorizedException("You are not allowed to update this delivery");
        }

        DeliveryStatus newStatus = updateDeliveryStatusRequest.getStatus();
        DeliveryStatus currentStatus = delivery.getStatus();

        if(newStatus.equals(currentStatus)) {
            throw new BadDeliveryRequestException(STR."Delivery is already in status: \{currentStatus}");
        }
        if (!currentStatus.canTransitionTo(newStatus)) {
            throw new BadDeliveryRequestException(
                    STR."Cannot change delivery status from \{currentStatus} to \{newStatus}"
            );
        }
        if(newStatus.equals(DeliveryStatus.ASSIGNED)) {
            if(updateDeliveryStatusRequest.getDeliveryAgentId() == null)
                throw new BadDeliveryRequestException("Delivery agent id cannot be null");
            DeliveryAgent deliveryAgent = agentRepository.findById(updateDeliveryStatusRequest.getDeliveryAgentId()).orElseThrow(()-> new AgentNotFoundException("Agent not found"));
            if(!deliveryAgent.getStatus().equals(AgentStatus.ACTIVE))
                throw new BadDeliveryRequestException("Delivery agent is not in ACTIVE status");
            delivery.setDeliveryAgent(deliveryAgent);

        }
        delivery.setStatus(newStatus);
        Delivery saveDelivery =  deliveryRepository.save(delivery);

        if(newStatus == DeliveryStatus.ASSIGNED) {
            applicationEventPublisher.publishEvent(
                    new DeliveryAssignedEvent(saveDelivery.getDeliveryAgent().getId(),
                            saveDelivery.getId(),
                            saveDelivery.getOrder().getId())
            );
        }

        if(newStatus == DeliveryStatus.PICKED_UP) {
            applicationEventPublisher.publishEvent(
                    new DeliveryPickedUpEvent(saveDelivery.getOrder().getCustomer().getId(),
                            saveDelivery.getId())
            );
        }

        return saveDelivery;
    }

    public Delivery getDeliveryByOrderId(UUID orderId) {
        return deliveryRepository.findByOrderId(orderId).orElseThrow(()-> new DeliveryNotFoundException("Delivery not found"));
    }

    public Delivery getDeliveryById(UUID deliveryId) {
        return deliveryRepository.findById(deliveryId).orElseThrow(()-> new DeliveryNotFoundException("Delivery not found"));
    }

    public List<Delivery> getDeliveriesByAgent(UUID agentId) {
        return deliveryRepository.findByDeliveryAgent_Id(agentId);
    }

}

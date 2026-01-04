package com.delivery.logistics.order.service;

import com.delivery.logistics.common.exception.*;
import com.delivery.logistics.customer.address.model.Address;
import com.delivery.logistics.customer.address.repository.AddressRepository;
import com.delivery.logistics.order.dto.CreateOrderRequest;
import com.delivery.logistics.order.dto.UpdateOrderStatusRequest;
import com.delivery.logistics.order.model.Order;
import com.delivery.logistics.order.model.OrderStatus;
import com.delivery.logistics.order.repository.OrderRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;

    public OrderService(OrderRepository orderRepository, AddressRepository addressRepository) {
        this.orderRepository = orderRepository;
        this.addressRepository = addressRepository;
    }

    @Transactional
    public Order createOrder(UUID customerID, CreateOrderRequest createOrderRequest) throws UnauthorizedException {


        Address address = addressRepository.findById(createOrderRequest.getAddressId()).orElseThrow(() -> new AddressNotFoundException("Address not found"));

        if (!address.getCustomer().getId().equals(customerID)) {
            throw new UnauthorizedException("Address does not belong to customer");
        }

        Order order = new Order();

        order.setCustomer(address.getCustomer());
        order.setDeliveryCity(address.getCity());
        order.setDeliveryState(address.getState());
        order.setDeliveryLatitude(address.getLatitude());
        order.setDeliveryLongitude(address.getLongitude());
        order.setDeliveryCountry(address.getCountry());
        order.setDeliveryPincode(address.getPincode());
        order.setDeliveryStreet(address.getStreet());
        order.setStatus(OrderStatus.CREATED);
        order.setOrderNumber(generateOrderNumber());

        return orderRepository.save(order);
    }

    public String generateOrderNumber() {
        String shortUuid = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return "ORD-" + Year.now().getValue() + "-" + shortUuid;
    }

    public Order updateOrderStatus(UUID orderId, UpdateOrderStatusRequest updateOrderStatusRequest) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order does not exist"));

        OrderStatus currentStatus = order.getStatus();
        OrderStatus newStatus = updateOrderStatusRequest.getOrderStatus();

        if(currentStatus == newStatus)
        {
            throw new BadOrderRequestException("Order is already in status: " + currentStatus);
        }
        if (!currentStatus.canTransitionTo(newStatus)) {
            throw new BadOrderRequestException(
                    "Cannot change order status from " + currentStatus + " to " + newStatus
            );
        }
        order.setStatus(newStatus);
        return orderRepository.save(order);
    }

    public Order getOrderById(UUID orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order does not exist"));
    }

    public List<Order> getOrdersByCustomer(UUID customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    public Order getOrderByOrderNumber(String orderNumber)
    {
        Order order = orderRepository.findByOrderNumber(orderNumber);
        if (order == null) {
            throw new OrderNotFoundException("Order does not exist");
        }
        return order;
    }

}

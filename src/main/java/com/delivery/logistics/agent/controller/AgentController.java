package com.delivery.logistics.agent.controller;

import com.delivery.logistics.agent.dto.AgentResponse;
import com.delivery.logistics.agent.dto.CreateAgentRequest;
import com.delivery.logistics.agent.dto.UpdateAgentStatusRequest;
import com.delivery.logistics.agent.mapper.AgentMapper;
import com.delivery.logistics.agent.model.AgentStatus;
import com.delivery.logistics.agent.service.AgentService;
import com.delivery.logistics.delivery.dto.DeliveryResponse;
import com.delivery.logistics.delivery.mapper.DeliveryMapper;
import com.delivery.logistics.delivery.service.DeliveryService;
import com.delivery.logistics.order.dto.UpdateOrderStatusRequest;
import jakarta.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.delivery.logistics.agent.mapper.AgentMapper.toResponse;

@RestController
public class AgentController {

    private final AgentService agentService;
    private final DeliveryService deliveryService;

    public AgentController(AgentService agentService,  DeliveryService deliveryService) {
        this.agentService = agentService;
        this.deliveryService = deliveryService;
    }

    @PostMapping("/agents")
    public AgentResponse createAgent(@RequestBody @Valid CreateAgentRequest createAgentRequest) {
        return toResponse(agentService.createAgent(createAgentRequest));
    }

    @PatchMapping("/agents/{agentId}/status")
    public AgentResponse updateAgentStatus(@PathVariable UUID agentId, @RequestBody @Valid UpdateAgentStatusRequest updateAgentStatusRequest) {
        return toResponse(agentService.updateAgentStatus(agentId, updateAgentStatusRequest));
    }

    @GetMapping("/agents/{agentId}")
    public AgentResponse getAgentById(@PathVariable UUID agentId) {
        return toResponse(agentService.getAgentById(agentId));
    }

    @GetMapping("/agents")
    public List<AgentResponse> getAllAgents() {

        return agentService.getAllAgents()
                .stream()
                .map(AgentMapper::toResponse)
                .toList();
    }

    @GetMapping("/agents/me/deliveries")
    public List<DeliveryResponse> getMyDeliveries() {
        UUID agentId = (UUID) Objects.requireNonNull(SecurityContextHolder.getContext()
                        .getAuthentication())
                .getPrincipal();

        return deliveryService.getDeliveriesByAgent(agentId)
                .stream()
                .map(DeliveryMapper::toResponse)
                .toList();
    }

}

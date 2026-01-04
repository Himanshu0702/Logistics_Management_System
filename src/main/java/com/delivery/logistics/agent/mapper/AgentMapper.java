package com.delivery.logistics.agent.mapper;

import com.delivery.logistics.agent.dto.AgentResponse;
import com.delivery.logistics.agent.model.DeliveryAgent;


public class AgentMapper {

    public static AgentResponse toResponse(DeliveryAgent deliveryAgent) {
        AgentResponse agentResponse = new AgentResponse();
        agentResponse.setId(deliveryAgent.getId());
        agentResponse.setEmail(deliveryAgent.getEmail());
        agentResponse.setPhone(deliveryAgent.getPhone());
        agentResponse.setFirstName(deliveryAgent.getFirstName());
        agentResponse.setLastName(deliveryAgent.getLastName());
        agentResponse.setStatus(deliveryAgent.getStatus());
        return agentResponse;
    }
}

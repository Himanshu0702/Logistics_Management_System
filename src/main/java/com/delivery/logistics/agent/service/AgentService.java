package com.delivery.logistics.agent.service;

import com.delivery.logistics.agent.dto.CreateAgentRequest;
import com.delivery.logistics.agent.dto.UpdateAgentStatusRequest;
import com.delivery.logistics.agent.model.AgentStatus;
import com.delivery.logistics.agent.model.DeliveryAgent;
import com.delivery.logistics.agent.repository.AgentRepository;
import com.delivery.logistics.common.exception.AgentNotFoundException;
import com.delivery.logistics.common.exception.BadAgentRequestException;
import com.delivery.logistics.common.exception.EmailAlreadyExistsException;
import com.delivery.logistics.common.exception.PhoneNumberAlreadyExistsException;
import com.delivery.logistics.common.security.PasswordHasher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AgentService {

    private final AgentRepository agentRepository;
    private final PasswordHasher passwordHasher;

    public AgentService(AgentRepository agentRepository,  PasswordHasher passwordHasher) {
        this.agentRepository = agentRepository;
        this.passwordHasher = passwordHasher;
    }

    public DeliveryAgent createAgent(CreateAgentRequest request) {

        boolean isEmailExist = agentRepository.existsByEmail(request.getEmail());
        if(isEmailExist)
            throw new EmailAlreadyExistsException("Agent with this email already exists");

        boolean isPhoneExist = agentRepository.existsByPhone(request.getPhone());
        if(isPhoneExist)
            throw new PhoneNumberAlreadyExistsException("Phone already exists");

        DeliveryAgent deliveryAgent = new DeliveryAgent();
        deliveryAgent.setEmail(request.getEmail());
        deliveryAgent.setFirstName(request.getFirstName());
        deliveryAgent.setLastName(request.getLastName());
        deliveryAgent.setStatus(AgentStatus.ACTIVE);
        deliveryAgent.setPhone(request.getPhone());
        deliveryAgent.setPassword(passwordHasher.hashPassword(request.getPassword()));

        return agentRepository.save(deliveryAgent);
    }

    public DeliveryAgent getAgentById(UUID agentId){
        return agentRepository.findById(agentId).orElseThrow(() -> new AgentNotFoundException("Agent not found"));
    }

    public List<DeliveryAgent> getAllAgents(){
        return agentRepository.findAll();
    }

    public DeliveryAgent updateAgentStatus(UUID agentId, UpdateAgentStatusRequest  updateAgentStatusRequest) {

        DeliveryAgent deliveryAgent = agentRepository.findById(agentId).orElseThrow(() -> new AgentNotFoundException("Agent not found"));

        if(deliveryAgent.getStatus().equals(updateAgentStatusRequest.getStatus())){
            throw new BadAgentRequestException(STR."Agent is already in status: \{updateAgentStatusRequest.getStatus()}");
        }
        deliveryAgent.setStatus(updateAgentStatusRequest.getStatus());
        return agentRepository.save(deliveryAgent);
    }


}

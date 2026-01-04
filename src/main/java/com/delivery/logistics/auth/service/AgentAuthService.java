package com.delivery.logistics.auth.service;

import com.delivery.logistics.agent.model.AgentStatus;
import com.delivery.logistics.agent.model.DeliveryAgent;
import com.delivery.logistics.agent.repository.AgentRepository;
import com.delivery.logistics.auth.dto.AgentLoginRequest;
import com.delivery.logistics.auth.dto.AgentLoginResponse;
import com.delivery.logistics.common.enums.Role;
import com.delivery.logistics.common.exception.UnauthorizedException;
import com.delivery.logistics.common.security.JwtUtil;
import com.delivery.logistics.common.security.PasswordHasher;
import org.springframework.stereotype.Service;

@Service
public class AgentAuthService {

    private final AgentRepository  agentRepository;
    private final PasswordHasher passwordHasher;
    private final JwtUtil jwtUtil;

    public AgentAuthService(AgentRepository agentRepository, PasswordHasher passwordHasher,  JwtUtil jwtUtil) {
        this.agentRepository = agentRepository;
        this.passwordHasher = passwordHasher;
        this.jwtUtil = jwtUtil;
    }

     public AgentLoginResponse login(AgentLoginRequest agentLoginRequest) {

         DeliveryAgent deliveryAgent = agentRepository.findByEmail(agentLoginRequest.getEmail()).orElseThrow(() -> new UnauthorizedException("Invalid credentials"));

         if(!passwordHasher.checkPassword(agentLoginRequest.getPassword(), deliveryAgent.getPassword()))
             throw new UnauthorizedException("Invalid credentials");

         if(deliveryAgent.getStatus().equals(AgentStatus.INACTIVE) || deliveryAgent.getStatus().equals(AgentStatus.SUSPENDED))
             throw new UnauthorizedException("Invalid credentials");



         String token = jwtUtil.generateJwtToken(deliveryAgent.getId(), agentLoginRequest.getEmail(), Role.AGENT);

         AgentLoginResponse agentLoginResponse = new AgentLoginResponse();

         agentLoginResponse.setToken(token);
         agentLoginResponse.setAgentId(deliveryAgent.getId());
         return agentLoginResponse;
     }
}

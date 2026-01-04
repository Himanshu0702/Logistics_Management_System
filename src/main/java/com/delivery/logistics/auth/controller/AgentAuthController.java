package com.delivery.logistics.auth.controller;

import com.delivery.logistics.auth.dto.AgentLoginRequest;
import com.delivery.logistics.auth.dto.AgentLoginResponse;
import com.delivery.logistics.auth.service.AgentAuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AgentAuthController {

    private final AgentAuthService agentAuthService;

    public AgentAuthController(AgentAuthService agentAuthService) {
        this.agentAuthService = agentAuthService;
    }

    @PostMapping("/auth/agents/login")
    public AgentLoginResponse login(AgentLoginRequest agentLoginRequest) {
        return agentAuthService.login(agentLoginRequest);
    }
}

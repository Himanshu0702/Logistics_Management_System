package com.delivery.logistics.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentLoginResponse {

    private String token;

    private UUID agentId;

}

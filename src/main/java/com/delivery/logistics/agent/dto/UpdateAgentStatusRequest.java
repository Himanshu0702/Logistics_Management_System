package com.delivery.logistics.agent.dto;

import com.delivery.logistics.agent.model.AgentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAgentStatusRequest {

    private AgentStatus status;
}


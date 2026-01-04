package com.delivery.logistics.agent.dto;

import com.delivery.logistics.agent.model.AgentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentResponse {

    private UUID id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private AgentStatus status;
}

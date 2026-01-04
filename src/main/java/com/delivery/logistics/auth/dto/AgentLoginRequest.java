package com.delivery.logistics.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentLoginRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}

package com.group3.projectmanagementapi.projectstatus.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectStatusRequest {
    
    @NotEmpty(message = "The name of the status is required")
    private String name;

}

package com.group3.projectmanagementapi.projectstatus.model.dto;

import com.group3.projectmanagementapi.status.model.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectStatusResponse {
  
    private long id;

    private Status status;
}

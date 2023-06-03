package com.group3.projectmanagementapi.memberproject.models.dto;

import com.group3.projectmanagementapi.customeruser.model.dto.CustomeruserResponse;
import com.group3.projectmanagementapi.project.model.dto.ProjectResponse;

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
public class MemberProjectResponse {
    private long id;
    private CustomeruserResponse customeruser;
    private ProjectResponse project;
}

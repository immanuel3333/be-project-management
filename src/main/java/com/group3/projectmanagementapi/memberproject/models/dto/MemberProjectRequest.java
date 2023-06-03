package com.group3.projectmanagementapi.memberproject.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberProjectRequest {
    private Long idCustomeruser;
    private Long idProject;
}

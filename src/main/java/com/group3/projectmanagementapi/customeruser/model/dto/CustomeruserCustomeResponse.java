package com.group3.projectmanagementapi.customeruser.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomeruserCustomeResponse {
    private int status;
    private String message;
    private Object data;
}

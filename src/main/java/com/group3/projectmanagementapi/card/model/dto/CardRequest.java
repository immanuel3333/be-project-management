package com.group3.projectmanagementapi.card.model.dto;

import com.group3.projectmanagementapi.card.model.Card;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardRequest {
    private String title;
    private String description;
    private Long statusId;
    private Long projectId;

    public Card convertToEntity() {
        return Card.builder().title(title).description(description).build();
    }
}

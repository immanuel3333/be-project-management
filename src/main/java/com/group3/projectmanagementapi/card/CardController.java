package com.group3.projectmanagementapi.card;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.group3.projectmanagementapi.card.model.Card;
import com.group3.projectmanagementapi.card.model.dto.CardDeleteResponse;
import com.group3.projectmanagementapi.card.model.dto.CardRequest;
import com.group3.projectmanagementapi.card.model.dto.CardResponse;
import com.group3.projectmanagementapi.project.model.Project;
import com.group3.projectmanagementapi.status.model.Status;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @PostMapping("/projects/{idProject}/cards")
    public ResponseEntity<CardResponse> createCard(@PathVariable("idProject") Long projectId,
            @Valid @RequestBody CardRequest cardRequest) {
        Card newCard = cardRequest.convertToEntity();

        Project project = new Project();
        project.setId(projectId);

        Status status = new Status();
        status.setId(cardRequest.getStatusId());

        newCard.setProject(project);
        newCard.setStatus(status);

        Card createdCard = cardService.createCard(newCard);
        CardResponse cardResponse = createdCard.convertToResponse();
        return ResponseEntity.status(HttpStatus.CREATED).body(cardResponse);
    }

    @PutMapping("/projects/{idProject}/cards/{idCard}")
    public ResponseEntity<CardResponse> updateCard(@PathVariable("idProject") Long projectId,
            @PathVariable("idCard") Long cardId, @Valid @RequestBody CardRequest cardRequest) {
        Card card = cardRequest.convertToEntity();

        card.setId(cardId);
        Project project = new Project();
        project.setId(projectId);

        Status status = new Status();
        status.setId(cardRequest.getStatusId());

        card.setProject(project);
        card.setStatus(status);

        Card updatedCard = cardService.updateCard(card);
        CardResponse cardResponse = updatedCard.convertToResponse();

        return ResponseEntity.status(HttpStatus.OK).body(cardResponse);
    }

    @DeleteMapping("/projects/{idProject}/cards/{idCard}")
    public ResponseEntity<CardDeleteResponse> deleteCard(@PathVariable("idProject") Long projectId,
            @PathVariable("idCard") Long cardId) {
        try {
            cardService.deleteCard(cardId, projectId);
            CardDeleteResponse response = new CardDeleteResponse();
            response.setStatus(200);
            response.setMessage("Card Deleted Successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            CardDeleteResponse response = new CardDeleteResponse();
            response.setStatus(500);
            response.setMessage("An error occurred while deleting the card.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

}

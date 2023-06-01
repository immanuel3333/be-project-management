package com.group3.projectmanagementapi.card;

import org.springframework.stereotype.Service;

import com.group3.projectmanagementapi.card.exception.CardNotFoundException;
import com.group3.projectmanagementapi.card.model.Card;
import com.group3.projectmanagementapi.project.Project;
import com.group3.projectmanagementapi.project.ProjectRepository;
import com.group3.projectmanagementapi.project.exception.ProjectNotFoundException;
import com.group3.projectmanagementapi.status.Status;
import com.group3.projectmanagementapi.status.StatusRepository;
import com.group3.projectmanagementapi.status.exception.StatusNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CardService {
    private final CardRepository cardRepository;
    private final StatusRepository statusRepository;
    private final ProjectRepository projectRepository;

    public Card createCard(Card card) {
        Status status = statusRepository.findById(card.getStatus().getId())
                .orElseThrow(() -> new StatusNotFoundException());

        Project project = projectRepository.findById(card.getProject().getId())
                .orElseThrow(() -> new ProjectNotFoundException());

        card.setProject(project);
        card.setStatus(status);
        return cardRepository.save(card);

    }

    public Card updateCard(Card card) {
        Card existingCard = findOneByIdAndProjectId(card.getId(), card.getProject().getId());
        return cardRepository.save(existingCard);
    }

    private Card findOneByIdAndProjectId(Long cardId, Long projectId) {
        Card existingCard = cardRepository.findByIdAndProjectId(cardId, projectId)
                .orElseThrow(() -> new CardNotFoundException());
        return existingCard;
    }

    public void deleteCard(Long cardId, Long projectId) {
        Card existingCard = findOneByIdAndProjectId(cardId, projectId);
        cardRepository.delete(existingCard);
    }

}

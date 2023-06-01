package com.group3.projectmanagementapi.card;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group3.projectmanagementapi.card.model.Card;

public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findByIdAndProjectId(Long cardId, Long projectId);
}

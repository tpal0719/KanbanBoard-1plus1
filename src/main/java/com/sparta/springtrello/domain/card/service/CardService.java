package com.sparta.springtrello.domain.card.service;

import com.sparta.springtrello.domain.card.dto.CardCreateRequestDto;
import com.sparta.springtrello.domain.card.entity.Card;
import com.sparta.springtrello.domain.card.repository.CardAdapter;
import com.sparta.springtrello.domain.column.entity.TaskColumn;
import com.sparta.springtrello.domain.column.repository.TaskColumnAdapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
public class CardService {
    private final CardAdapter cardAdapter;
    private final TaskColumnAdapter taskColumnAdapter;

    @Transactional
    public void createCard(Long columnId, CardCreateRequestDto requestDto) {
        TaskColumn taskColumn = taskColumnAdapter.findById(columnId);

        int cardOrder = cardAdapter.countByTaskColumn(taskColumn) + 1;

        Card card = Card.builder()
                .cardName(requestDto.getCardName())
                .cardOrder(cardOrder)
                .taskColumn(taskColumn)
                .build();

        cardAdapter.save(card);
    }
}

package com.sparta.springtrello.domain.card.repository;

import com.sparta.springtrello.domain.card.entity.Card;
import com.sparta.springtrello.domain.column.entity.TaskColumn;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CardAdapter {
    private final CardRepository cardRepository;


    public void save(Card card) {
        cardRepository.save(card);
    }

    public int countByTaskColumn(TaskColumn taskColumn) {
        return cardRepository.countByTaskColumn(taskColumn);
    }
}

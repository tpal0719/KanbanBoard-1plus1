package com.sparta.springtrello.domain.card.service;

import com.sparta.springtrello.domain.card.dto.CardCreateRequestDto;
import com.sparta.springtrello.domain.card.dto.CardResponseDto;
import com.sparta.springtrello.domain.card.entity.Card;
import com.sparta.springtrello.domain.card.repository.CardAdapter;
import com.sparta.springtrello.domain.column.entity.TaskColumn;
import com.sparta.springtrello.domain.column.repository.TaskColumnAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardAdapter cardAdapter;
    private final TaskColumnAdapter taskColumnAdapter;


    // 카드 생성
    @Transactional
    public void createCard(Long columnId, CardCreateRequestDto requestDto) {
        TaskColumn taskColumn = taskColumnAdapter.findById(columnId);
        int cardOrder = taskColumn.getCards().size() + 1;
        Card card = Card.builder()
                .cardName(requestDto.getCardName())
                .cardOrder(cardOrder)
                .taskColumn(taskColumn)
                .build();
        cardAdapter.save(card);
    }

    // 카드 조회(보드별)
    @Transactional(readOnly = true)
    public List<CardResponseDto> getCardsByBoardId(Long boardId) {
        List<Card> cards = cardAdapter.findAllByBoardId(boardId);
        return CardResponseDto.fromEntities(cards);
    }

    // 카드 조회(칼럼별)
    @Transactional(readOnly = true)
    public List<CardResponseDto> getCardsByColumnId(Long columnId) {
        List<Card> cards = cardAdapter.findAllByColumnId(columnId);
        return CardResponseDto.fromEntities(cards);
    }

    // 카드 조회(유저별)
    @Transactional(readOnly = true)
    public List<CardResponseDto> getCardsByUserId(Long userId) {
        List<Card> cards = cardAdapter.findAllByUserId(userId);
        return CardResponseDto.fromEntities(cards);
    }
}

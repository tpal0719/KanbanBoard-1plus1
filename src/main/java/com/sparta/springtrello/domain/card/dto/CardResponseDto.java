package com.sparta.springtrello.domain.card.dto;

import com.sparta.springtrello.domain.card.entity.Card;
import com.sparta.springtrello.domain.column.entity.TaskColumn;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CardResponseDto {
    private Long id;
    private String cardName;
    private String cardDescription;
    private LocalDateTime dueDate;
    private Integer cardOrder;
    private TaskColumnDto taskColumn;

    public CardResponseDto(Card card) {
        this.id = card.getId();
        this.cardName = card.getCardName();
        this.cardDescription = card.getCardDescription();
        this.dueDate = card.getDueDate();
        this.cardOrder = card.getCardOrder();
        this.taskColumn = new TaskColumnDto(card.getTaskColumn());
    }

    @Getter
    public static class TaskColumnDto {
        private Long id;
        private String columnName;

        public TaskColumnDto(TaskColumn taskColumn) {
            this.id = taskColumn.getId();
            this.columnName = taskColumn.getColumnName();
        }
    }

    public static List<CardResponseDto> fromEntities(List<Card> cards) {
        return cards.stream().map(CardResponseDto::new).collect(Collectors.toList());
    }
}
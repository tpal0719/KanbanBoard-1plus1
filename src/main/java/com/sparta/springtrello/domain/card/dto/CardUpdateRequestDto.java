package com.sparta.springtrello.domain.card.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CardUpdateRequestDto {
    private String cardName;
    private String cardDescription;
    private LocalDateTime dueDate;
    private Integer cardOrder;
}

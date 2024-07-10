package com.sparta.springtrello.domain.card.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardAssigneeRequestDto {

    @NotNull
    private Long cardId;

    @NotNull
    private Long userId;
}

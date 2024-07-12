package com.sparta.springtrello.domain.card.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardAssigneeRequestDto {

    @NotBlank
    private Long cardId;

    @NotBlank
    private Long userId;
}

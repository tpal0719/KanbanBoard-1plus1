package com.sparta.springtrello.domain.card.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CardCreateRequestDto {

    @NotNull
    private String cardName;
}

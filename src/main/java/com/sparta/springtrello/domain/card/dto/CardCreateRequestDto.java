package com.sparta.springtrello.domain.card.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CardCreateRequestDto {

    @NotBlank(message = "카드이름은 필수 입력 값입니다.")
    private String cardName;
}

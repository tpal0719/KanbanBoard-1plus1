package com.sparta.springtrello.domain.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardCreateRequestDto {

    @NotBlank(message = "보드 이름은 필수 입력 값입니다.")
    private String boardName;

    private String boardDescription;
}

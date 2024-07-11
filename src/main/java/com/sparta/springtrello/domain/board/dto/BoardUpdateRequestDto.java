package com.sparta.springtrello.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardUpdateRequestDto {

    @NotBlank(message = "보드 이름은 필수 입력 값입니다.")
    private String boardName;

    private String boardDescription;
}

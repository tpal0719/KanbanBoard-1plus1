package com.sparta.springtrello.domain.column.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TaskColumnCreateRequestDto {
    @NotBlank(message = "칼럼 이름은 필수 입력 값입니다.")
    private String columnName;
}
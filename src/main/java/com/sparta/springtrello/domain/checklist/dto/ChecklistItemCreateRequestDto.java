package com.sparta.springtrello.domain.checklist.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ChecklistItemCreateRequestDto {

    @NotBlank(message = "내용을 입력해주세요.")
    private String item_name;

}

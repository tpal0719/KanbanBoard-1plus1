package com.sparta.springtrello.domain.checklist.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ChecklistItemUpdateRequestDto {

    @NotBlank(message = "변경할 내용을 입력하세요.")
    private String itemName;
}

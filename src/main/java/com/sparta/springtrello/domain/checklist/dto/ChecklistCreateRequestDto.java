package com.sparta.springtrello.domain.checklist.dto;

import jakarta.validation.constraints.NotBlank;

public class ChecklistCreateRequestDto {

    @NotBlank(message = "체크리스트 이름을 입력해주세요")
    private String checklistName;

}

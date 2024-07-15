package com.sparta.springtrello.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDeleteRequestDto {
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;
}
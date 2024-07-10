package com.sparta.springtrello.exception.custom;

import com.sparta.springtrello.common.ResponseCodeEnum;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final ResponseCodeEnum responseCode;

    public CustomException(ResponseCodeEnum responseCode) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
    }
}

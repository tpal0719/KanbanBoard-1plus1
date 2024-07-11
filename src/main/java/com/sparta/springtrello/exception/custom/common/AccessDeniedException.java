package com.sparta.springtrello.exception.custom.common;

import com.sparta.springtrello.common.ResponseCodeEnum;
import com.sparta.springtrello.exception.custom.CustomException;

public class AccessDeniedException extends CustomException {
    public AccessDeniedException(ResponseCodeEnum responseCode) {
        super(responseCode);
    }
}

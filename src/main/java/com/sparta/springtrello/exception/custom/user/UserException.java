package com.sparta.springtrello.exception.custom.user;

import com.sparta.springtrello.common.ResponseCodeEnum;
import com.sparta.springtrello.exception.custom.CustomException;

public class UserException extends CustomException {
    public UserException(ResponseCodeEnum responseCode) {
        super(responseCode);
    }
}
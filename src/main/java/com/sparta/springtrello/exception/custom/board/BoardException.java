package com.sparta.springtrello.exception.custom.board;

import com.sparta.springtrello.common.ResponseCodeEnum;
import com.sparta.springtrello.exception.custom.CustomException;

public class BoardException extends CustomException {
    public BoardException(ResponseCodeEnum responseCode) {
        super(responseCode);
    }
}

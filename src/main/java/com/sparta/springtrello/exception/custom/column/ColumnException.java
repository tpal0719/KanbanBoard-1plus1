package com.sparta.springtrello.exception.custom.column;

import com.sparta.springtrello.common.ResponseCodeEnum;
import com.sparta.springtrello.exception.custom.CustomException;

public class ColumnException extends CustomException {
    public ColumnException(ResponseCodeEnum responseCode) {
        super(responseCode);
    }
}

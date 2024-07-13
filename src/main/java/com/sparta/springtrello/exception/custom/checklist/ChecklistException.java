package com.sparta.springtrello.exception.custom.checklist;

import com.sparta.springtrello.common.ResponseCodeEnum;
import com.sparta.springtrello.exception.custom.CustomException;

public class ChecklistException extends CustomException {
    public ChecklistException(ResponseCodeEnum responseCode) {
        super(responseCode);
    }
}

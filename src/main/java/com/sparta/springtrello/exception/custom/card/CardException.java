package com.sparta.springtrello.exception.custom.card;

import com.sparta.springtrello.common.ResponseCodeEnum;
import com.sparta.springtrello.exception.custom.CustomException;

public class CardException extends CustomException {
    public CardException(ResponseCodeEnum responseCode) {
        super(responseCode);
    }
}

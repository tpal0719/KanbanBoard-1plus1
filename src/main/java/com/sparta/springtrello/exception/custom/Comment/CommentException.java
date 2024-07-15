package com.sparta.springtrello.exception.custom.Comment;

import com.sparta.springtrello.common.ResponseCodeEnum;
import com.sparta.springtrello.exception.custom.CustomException;

public class CommentException extends CustomException {
    public CommentException(ResponseCodeEnum responseCode) {
        super(responseCode);
    }
}

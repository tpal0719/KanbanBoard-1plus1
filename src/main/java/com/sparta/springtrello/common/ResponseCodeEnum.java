package com.sparta.springtrello.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ResponseCodeEnum {
    // 공용 예외
    UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "접근이 거부되었습니다."),

    // 유저 관련 예외
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "로그인 실패"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다"),
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 존재하는 사용자명입니다."),
    USER_DELETED(HttpStatus.UNAUTHORIZED, "탈퇴한 사용자입니다"),
    INVALID_TOKENS(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다"),
    PASSWORD_INCORRECT(HttpStatus.UNAUTHORIZED, "비밀번호가 올바르지 않습니다"),
    SAME_AS_OLD_PASSWORD(HttpStatus.BAD_REQUEST, "새 비밀번호가 이전 비밀번호와 동일합니다."),
    INVALID_MANAGER_PASSWORD(HttpStatus.FORBIDDEN, "관리자 암호가 틀렸습니다."),

    // 칼럼 관련 예외
    COLUMN_NOT_FOUND(HttpStatus.NOT_FOUND, "칼럼을 찾을 수 없습니다"),
    INVALID_COLUMN_ORDER(HttpStatus.BAD_REQUEST, "잘못된 컬럼 순서입니다."),

    // 보드 관련 예외
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "보드를 찾을 수 없습니다"),
    BOARD_INVITE_SELF_USER(HttpStatus.BAD_REQUEST,"본인을 초대할 수 없습니다."),
    BOARD_INVITE_ACCEPT(HttpStatus.BAD_REQUEST,"이미 초대를 수락했습니다."),


    // 카드 관련 예외
    CARD_NOT_FOUND(HttpStatus.NOT_FOUND, "카드를 찾을 수 없습니다"),
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "카드에 파일을 찾을 수 없습니다"),
    CARD_NOT_NAME(HttpStatus.NOT_FOUND, "카드에 이름이 없습니다"),

    // 댓글 관련 예외
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다"),

    //체크리스트 관련 예외
    CHECKLIST_NOT_FOUND(HttpStatus.NOT_FOUND, "체크리스트를 찾을 수 없습니다"),

    ;
    private final HttpStatus httpStatus;
    private final String message;
}
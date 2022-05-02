package com.market.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.OK;

@Getter
@AllArgsConstructor
public enum ExceptionCode {

    /* 10000  */
    EMAIL_NOT_FOUND(10001,"이메일이 존재하지 않습니다."),
    SIGN_IN_FAIL(10002,"아이디와 비밀번호가 일치하지 않습니다."),
    WRONG_PWD_FIVE(10003,"비밀번호 입력이 5회 이상 실패했습니다."),
    UNAUTHORIZED(10004, "권한이 없습니다."),
    INVALID_TOKEN(10005, "유효하지 않은 토큰입니다."),
    ACCOUNT_NOT_FOUND(10006, "사용자를 찾을 수 없습니다."),
    ACCOUNT_SUSPENSION(10007, "계정 정지된 사용자입니다."),
    ACCOUNT_LOCK(10008, "계정이 잠긴 사용자입니다."),

    PRODUCT_NOT_FOUND(20001,"상품이 존재하지 않습니다."),
    PRODUCT_DELETED(20002,"삭제된 상품입니다."),
    PRODUCT_CREATE_FAIL(20003,"상품 업데이트가 실패하였습니다."),
    PRODUCT_UPDATE_FAIL(20004,"상품 생성이 실패하였습니다."),
    PRODUCT_DELETE_FAIL(20005,"상품 삭제를 실패하였습니다."),
    PRODUCT_DUPLICATE(20005,"이미 존재하는 상품입니다."),
    /* 500 internal server error */
    INTERNAL_ERROR(500, "Internal server error")
    ;

    private final int code;
    private final String message;
    private final HttpStatus status = OK;
}

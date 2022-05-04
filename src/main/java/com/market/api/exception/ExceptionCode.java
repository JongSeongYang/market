package com.market.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.OK;

@Getter
@AllArgsConstructor
public enum ExceptionCode {

    /* 10000 auth */
    EMAIL_NOT_FOUND(10001,"이메일이 존재하지 않습니다."),
    SIGN_IN_FAIL(10002,"아이디와 비밀번호가 일치하지 않습니다."),
    WRONG_PWD_FIVE(10003,"비밀번호 입력이 5회 이상 실패했습니다."),
    UNAUTHORIZED(10004, "권한이 없습니다."),
    INVALID_TOKEN(10005, "유효하지 않은 토큰입니다."),
    ACCOUNT_NOT_FOUND(10006, "사용자를 찾을 수 없습니다."),
    ACCOUNT_SUSPENSION(10007, "계정 정지된 사용자입니다."),
    ACCOUNT_LOCK(10008, "계정이 잠긴 사용자입니다."),
    SIGN_UP_FAIL(10009, "회원가입에 실패하였습니다."),
    DUPLICATED_MEMBER(10010, "이미 가입되어 있는 회원입니다."),

    /* 20000 product */
    PRODUCT_NOT_FOUND(20001,"상품이 존재하지 않습니다."),
    PRODUCT_DELETED(20002,"삭제된 상품입니다."),
    PRODUCT_CREATE_FAIL(20003,"상품 업데이트가 실패하였습니다."),
    PRODUCT_UPDATE_FAIL(20004,"상품 생성이 실패하였습니다."),
    PRODUCT_DELETE_FAIL(20005,"상품 삭제를 실패하였습니다."),
    PRODUCT_DUPLICATE(20006,"이미 존재하는 상품입니다."),

    /* 30000 order */
    PAYMENT_NOT_FOUND(30001,"결재 수단이 존재하지 않습니다."),
    OUT_OF_STOCK(30002,"재고가 없습니다."),
    LACK_OF_QUANTITY(30003,"수량이 부족합니다."),
    WRONG_ORDER_REQUEST(30004,"주문 요청이 잘못되었습니다."),
    ORDER_NOT_FOUND(30005,"주문을 찾을 수 없습니다."),
    ORDER_CANCELED(30006,"취소된 주문입니다."),
    ORDER_COMPLETED(30007,"완료된 주문입니다."),
    ORDER_IN_PROGRESS(30008,"진행중인 주문입니다."),
    ORDER_CANCELED_FAIL(30009,"주문을 취소할 수 없습니다."),
    WRONG_CANCEL_REQUEST(30010,"취소 요청이 잘못되었습니다."),

    /* 500 internal server error */
    INTERNAL_ERROR(500, "Internal server error")
    ;

    private final int code;
    private final String message;
    private final HttpStatus status = OK;
}

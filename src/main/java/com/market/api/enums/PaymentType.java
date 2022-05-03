package com.market.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentType {
    CREDIT_CARD("신용카드"),
    CASH("현금"),
    BANK_TRANSFER("계좌이체");

    final private String payment;
}

package com.market.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatusType {
    ORDER_RECEPTION("주문접수"),
    ORDER_COMPLETE("주문완료"),
    DELIVERY_COMPLETE("배송완료"),
    ORDER_CANCEL("주문취소");

    final private String status;
}

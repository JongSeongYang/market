package com.market.api.event;


import com.market.api.domain.OrderEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderPartCanceledEvent {
    OrderEntity orderEntity;
    LocalDateTime now;
    public OrderPartCanceledEvent(OrderEntity orderEntity, LocalDateTime now) {
        this.now = now;
        this.orderEntity = orderEntity;
    }
}

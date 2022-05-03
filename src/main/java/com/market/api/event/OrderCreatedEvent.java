package com.market.api.event;


import com.market.api.domain.OrderEntity;
import lombok.Data;

@Data
public class OrderCreatedEvent {
    OrderEntity orderEntity;

    public OrderCreatedEvent(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }
}

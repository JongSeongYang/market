package com.market.api.event;


import com.market.api.domain.OrderEntity;
import lombok.Data;

@Data
public class OrderAllCanceledEvent {
    OrderEntity orderEntity;

    public OrderAllCanceledEvent(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }
}

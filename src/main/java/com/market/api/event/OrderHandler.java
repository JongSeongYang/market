package com.market.api.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderHandler {

    @TransactionalEventListener
    public void orderCreateNotice(OrderCreatedEvent event) {
        log.info("Order Success ({})",event.getOrderEntity().getCreatedTime());
    }

    @TransactionalEventListener
    public void orderAllCanceledNotice(OrderAllCanceledEvent event) {
        log.info("Order All Cancel Success ({})",event.getOrderEntity().getCancelTime());
    }

    @TransactionalEventListener
    public void orderPartCanceledNotice(OrderPartCanceledEvent event) {
        log.info("Order Part Cancel Success ({})",event.getNow());
    }
}

package com.market.api.service;

import com.market.api.config.AppConfig;
import com.market.api.domain.OrderEntity;
import com.market.api.domain.ProductEntity;
import com.market.api.dto.Order;
import com.market.api.dto.Product;
import com.market.api.exception.CustomResponseStatusException;
import com.market.api.exception.ExceptionCode;
import com.market.api.repository.OrderRepository;
import com.market.api.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final AppConfig appConfig;

    @Transactional
    public Long save(OrderEntity orderEntity) {
        OrderEntity save = orderRepository.save(orderEntity);
        return save.getId();
    }
}

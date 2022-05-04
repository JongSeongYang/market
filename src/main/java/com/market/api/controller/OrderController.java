package com.market.api.controller;

import com.market.api.domain.MemberEntity;
import com.market.api.dto.Order;
import com.market.api.dto.Product;
import com.market.api.exception.ExceptionCode;
import com.market.api.service.AuthService;
import com.market.api.service.MemberService;
import com.market.api.service.OrderService;
import com.market.api.service.ProductService;
import com.market.api.utils.annotation.Authenticate;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;

    @Authenticate
    @ApiOperation(value = "주문", notes = "주문")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order.OrderCommonResponse> create(HttpServletRequest request,
                                                            @RequestBody Order.OrderRequestList orderRequestList) {
        MemberEntity memberEntity = memberService.findOneByEmail(request, ExceptionCode.EMAIL_NOT_FOUND);
        Order.OrderCommonResponse response = orderService.create(memberEntity,orderRequestList);
        return ResponseEntity.ok(response);
    }

    @Authenticate
    @ApiOperation(value = "주문 취소", notes = "주문 취소")
    @PostMapping(value = "/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order.OrderCommonResponse> cancel(HttpServletRequest request,
                                                            @RequestBody Order.OrderUpdateRequestList orderRequestList) {
        MemberEntity memberEntity = memberService.findOneByEmail(request, ExceptionCode.EMAIL_NOT_FOUND);
        Order.OrderCommonResponse response = orderService.cancel(memberEntity,orderRequestList);
        return ResponseEntity.ok(response);
    }
}

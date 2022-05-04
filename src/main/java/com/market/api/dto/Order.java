package com.market.api.dto;

import lombok.*;

import java.util.List;

public class Order {

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class OrderRequestList {
        private List<OrderRequest> productList;
        private Integer payment; // 0: credit_card, 1: cash, 2: bank_transfer
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class OrderRequest {
        private Long id;
        private Integer count;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class OrderUpdateRequestList {
        private List<OrderRequest> productList;
        private Long orderId; // 0: credit_card, 1: cash, 2: bank_transfer
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class ProductListResponse {
        private Long id;
        private String name;
        private String category;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class ProductResponse {
        private Long id;
        private String name;
        private String category;
        private String description;
        private Integer price;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class OrderCommonResponse {
        private Boolean result;
        private String message;
    }
}

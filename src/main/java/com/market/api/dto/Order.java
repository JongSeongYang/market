package com.market.api.dto;

import lombok.*;

import java.util.List;

public class Order {

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class OrderRequest {
        private List<Long> productList;
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
    public static class ProductCommonResponse {
        private Boolean result;
        private String message;
    }
}

package com.market.api.dto;

import lombok.*;

public class Product {

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class ProductRequest {
        private String name;
        private String category;
        private String description;
        private Integer price;
        private Integer stock;
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
        private Integer stock;
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

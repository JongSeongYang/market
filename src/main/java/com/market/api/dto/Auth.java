package com.market.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Auth {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignInRequest {
        private String email;
        private String password;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignUpRequest {
        private String email;
        private String password;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthResponse {
        private Boolean result;
        private String token;
        private String message;
    }
}

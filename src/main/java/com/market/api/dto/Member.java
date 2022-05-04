package com.market.api.dto;

import lombok.*;

public class Member {

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class MemberRequest {
        private String password;
        private String email;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class MemberCommonResponse {
        private String message;
        private Boolean result;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class MemberResponse {
        private String type;
        private String email;
        private Integer status;
    }
}

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
        private String phone;
        private String name;
        private String email;
    }
}

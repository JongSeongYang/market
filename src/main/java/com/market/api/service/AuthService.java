package com.market.api.service;

import com.market.api.domain.MemberEntity;
import com.market.api.dto.Auth;
import com.market.api.exception.CustomResponseStatusException;
import com.market.api.exception.ExceptionCode;
import com.market.api.utils.AesUtils;
import com.market.api.utils.HashUtils;
import com.market.api.utils.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;
    private final HashUtils hashUtils;

    @Transactional
    public Auth.AuthResponse signUp(Auth.SignUpRequest signUpRequest, HttpServletRequest request) {

        MemberEntity memberEntity = memberService.createMember(signUpRequest);
        if (null == memberEntity) {
            log.error("signUp >> MEMBER_CREATE_FAIL");
            throw new CustomResponseStatusException(ExceptionCode.SIGN_UP_FAIL,"");
        }
        return getAuthResponse(true, memberEntity, "SIGN_UP_SUCCESS");
    }

    @Transactional(noRollbackFor = CustomResponseStatusException.class)
    public Auth.AuthResponse signIn(MemberEntity memberEntity, String password, String deviceToken) {

        boolean equals = hashUtils.toPasswordHash(password).equals(memberEntity.getPassword());
        // 비밀번호가 틀릴 경우
        if (!equals) {
            memberEntity.setPasswordFailCnt(memberEntity.getPasswordFailCnt() + 1);
            checkPwdFailCnt(memberEntity);
            throw new CustomResponseStatusException(ExceptionCode.SIGN_IN_FAIL, "");
        }
        // 비밀번호가 같을 경우
        else {
            memberEntity.setPasswordFailCnt(0);
        }

        return getAuthResponse(true, memberEntity, "LOGIN_SUCCESS");
    }

    public Boolean verifyToken(HttpServletRequest request) {
        String token = jwtTokenProvider.getTokenByHeader(request);
        Boolean validation = false;
        if (!token.equals("")) {
            validation = jwtTokenProvider.validateToken(token);
        }
        // token이 유효하지 않을 경우
        if (!validation) {
            throw new CustomResponseStatusException(ExceptionCode.INVALID_TOKEN, "");
        }

        return true;
    }

    public Auth.AuthResponse getAuthResponse(Boolean result, MemberEntity memberEntity, String message) {
        if (result)
            return Auth.AuthResponse.builder()
                    .result(result)
                    .token(jwtTokenProvider.createToken(memberEntity.getId(), memberEntity.getType(), memberEntity.getEmail(), 7))
                    .message(message)
                    .build();
        else
            return Auth.AuthResponse.builder()
                    .result(result)
                    .message(message)
                    .build();
    }

    private Integer checkPwdFailCnt(MemberEntity memberEntity) {
        Integer signInFailCount = memberEntity.getPasswordFailCnt();
        if (signInFailCount >= 5)
            throw new CustomResponseStatusException(ExceptionCode.WRONG_PWD_FIVE, "");
        return signInFailCount;
    }
}

package com.market.api.controller;

import com.market.api.domain.MemberEntity;
import com.market.api.dto.Auth;
import com.market.api.exception.ExceptionCode;
import com.market.api.service.AuthService;
import com.market.api.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final MemberService memberService;

    @ApiOperation(value = "Token 유효 확인", notes = "Token 유효 확인")
    @GetMapping(value = "/token/verify", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Auth.AuthResponse> tokenCheck(HttpServletRequest request) {
        Boolean validation = authService.verifyToken(request);
        MemberEntity memberEntity = memberService.findOneByEmail(request, ExceptionCode.SIGN_IN_FAIL);
        Auth.AuthResponse response = authService.getAuthResponse(validation, memberEntity, "VERIFY_TOKEN");
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "로그인", notes = "로그인")
    @PostMapping(value = "/signIn", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Auth.AuthResponse> signIn(@RequestBody Auth.SignInRequest signInRequest) {
        MemberEntity memberEntity = memberService.findOneByEmail(signInRequest.getEmail(), ExceptionCode.SIGN_IN_FAIL);
        Auth.AuthResponse response = authService.signIn(memberEntity, signInRequest.getPassword(), signInRequest.getDeviceToken());
        return ResponseEntity.ok(response);
    }
}

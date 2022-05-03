package com.market.api.controller;

import com.market.api.dto.Member;
import com.market.api.service.MemberService;
import com.market.api.utils.annotation.Authenticate;
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
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Authenticate
    @ApiOperation(value = "Member Info", notes = "Member Info")
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Member.MemberResponse> memberInfo(HttpServletRequest request) {
        return ResponseEntity.ok(memberService.getMemberInfo(request));
    }


    @Authenticate
    @ApiOperation(value = "Member Update", notes = "Member Update")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Member.MemberCommonResponse> memberUpdate(HttpServletRequest request,
                                                              @RequestBody Member.MemberRequest memberRequest) {
        return ResponseEntity.ok(memberService.update(request, memberRequest));
    }
}

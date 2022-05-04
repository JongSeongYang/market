package com.market.api.service;

import com.market.api.config.AppConfig;
import com.market.api.domain.MemberEntity;
import com.market.api.dto.Auth;
import com.market.api.dto.Member;
import com.market.api.enums.MemberType;
import com.market.api.exception.CustomResponseStatusException;
import com.market.api.exception.ExceptionCode;
import com.market.api.repository.MemberRepository;
import com.market.api.utils.AesUtils;
import com.market.api.utils.HashUtils;
import com.market.api.utils.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final HashUtils hashUtils;
    private final AesUtils aesUtils;
    private final AppConfig appConfig;

    @Transactional
    public Long save(MemberEntity memberEntity) {
        MemberEntity save = memberRepository.save(memberEntity);
        return save.getId();
    }

    @javax.transaction.Transactional(dontRollbackOn = CustomResponseStatusException.class)
    public MemberEntity createMember(Auth.SignUpRequest signUpRequest) {
        MemberEntity memberEntity = MemberEntity.builder()
                .type(MemberType.USER.name())
                .email(signUpRequest.getEmail())
                .passwordFailCnt(0)
                .password(hashUtils.toPasswordHash(signUpRequest.getPassword()))
                .status(0)
                .build();
        return memberRepository.save(memberEntity);
    }

    @Transactional
    public Member.MemberCommonResponse update(HttpServletRequest request, Member.MemberRequest memberRequest) {

        String email = memberRequest.getEmail();
        String password = memberRequest.getPassword();
        MemberEntity exist = memberRepository.findByEmailAndDeletedTimeIsNull(email)
                .orElseThrow(() -> new CustomResponseStatusException(ExceptionCode.EMAIL_NOT_FOUND, ""));
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

        if (null != password) {
            String hashed = hashUtils.toPasswordHash(password);
            if (!hashed.equals(exist.getPassword())) {
                exist.setPassword(hashed);
            }
        }
        exist.setStatus(0);
        memberRepository.save(exist);
        return Member.MemberCommonResponse.builder().result(true).message("PWD_UPDATE_SUCCESS").build();
    }
    public Member.MemberResponse getMemberInfo(HttpServletRequest request) {
        MemberEntity memberEntity = findOneByEmail(request, ExceptionCode.ACCOUNT_NOT_FOUND);
        return appConfig.modelMapper().map(memberEntity, Member.MemberResponse.class);
    }


    public MemberEntity findOneByEmail(HttpServletRequest request, ExceptionCode e) {
        String email = jwtTokenProvider.getEmailByClaims(request);
        MemberEntity memberEntity = memberRepository.findByEmailAndDeletedTimeIsNull(email)
                .orElseThrow(() -> new CustomResponseStatusException(e, ""));
        checkMemberStatus(memberEntity);
        return memberEntity;
    }

    public MemberEntity findOneByEmail(String email, ExceptionCode e) {
        MemberEntity memberEntity = memberRepository.findByEmailAndDeletedTimeIsNull(email)
                .orElseThrow(() -> new CustomResponseStatusException(e, ""));
        checkMemberStatus(memberEntity);
        return memberEntity;
    }

    public Boolean checkDupEmail(String email) {
        return memberRepository.findByEmailAndDeletedTimeIsNull(email).isPresent();
    }

    public MemberEntity findOneById(HttpServletRequest request) {
        long memberId = jwtTokenProvider.getMemberIdByClaims(request);
        MemberEntity memberEntity =  memberRepository.findByIdAndDeletedTimeIsNull(memberId)
                .orElseThrow(()->new CustomResponseStatusException(ExceptionCode.ACCOUNT_NOT_FOUND,""));
        checkMemberStatus(memberEntity);
        return memberEntity;
    }

    public void checkMemberStatus(MemberEntity memberEntity) {
        if (memberEntity.getStatus() == 1)
            throw new CustomResponseStatusException(ExceptionCode.ACCOUNT_SUSPENSION, "");
        if (memberEntity.getStatus() == 2)
            throw new CustomResponseStatusException(ExceptionCode.ACCOUNT_LOCK, "");
    }
}

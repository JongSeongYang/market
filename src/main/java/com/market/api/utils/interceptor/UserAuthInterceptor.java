package com.market.api.utils.interceptor;

import com.market.api.exception.CustomResponseStatusException;
import com.market.api.exception.ExceptionCode;
import com.market.api.utils.JwtTokenProvider;
import com.market.api.utils.annotation.Authenticate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserAuthInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION = "Authorization";

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        System.out.println(">>> UserAuthInterceptor.preHandle 호출");
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }
        Authenticate authenticate = ((HandlerMethod) handler).getMethodAnnotation(Authenticate.class);
        if(null != authenticate) {

            String token = extract(request, "Bearer");

            // 토큰이 없을 경우
            if (StringUtils.isEmpty(token)) {
                throw new CustomResponseStatusException(ExceptionCode.UNAUTHORIZED, "");
            }

            if (!jwtTokenProvider.validateToken(token)) {
                throw new CustomResponseStatusException(ExceptionCode.INVALID_TOKEN, "");
            }

            Map<String, String> map = jwtTokenProvider.getClaims(token);
            request.setAttribute("memberId", Integer.parseInt(map.get("id")));
            request.setAttribute("name", map.get("name"));
            request.setAttribute("email", map.get("email"));
        }
        return true;
    }

    private String extract(HttpServletRequest request, String type) {
        Enumeration<String> headers = request.getHeaders(AUTHORIZATION);
        while (headers.hasMoreElements()) {
            String value = headers.nextElement();
            if (value.toLowerCase().startsWith(type.toLowerCase())) {
                return value.substring(type.length()).trim();
            }
        }
        return "";
    }


}

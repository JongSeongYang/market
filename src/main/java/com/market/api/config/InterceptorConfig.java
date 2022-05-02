package com.market.api.config;

import com.market.api.utils.interceptor.CommonInterceptor;
import com.market.api.utils.interceptor.UserAuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {

    private final UserAuthInterceptor userAuthInterceptor;
    private final CommonInterceptor commonInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .maxAge(3000);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(commonInterceptor)
                .addPathPatterns("/api/v1/**");

        registry.addInterceptor(userAuthInterceptor)
                .addPathPatterns("/api/v1/**")
                .addPathPatterns("/rest-api-root/**")
                .excludePathPatterns("/swagger-ui/**")
                .excludePathPatterns("/h2-console/**");
    }
}

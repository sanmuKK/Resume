package com.sanmukk.resume.config;

import com.sanmukk.resume.Interceptor.AccessLimitInterceptor;
import com.sanmukk.resume.Interceptor.JwtInterceptor;
import com.sanmukk.resume.Interceptor.SuperAdminInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getAccessLimitInterceptor()).
                addPathPatterns("/**");
        registry.addInterceptor(getJwtInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/resume/downloadFile")
                .excludePathPatterns("/admin/auth/login")
                .excludePathPatterns("/superAdmin/auth/login");
        registry.addInterceptor(getSuperAdminInterceptor())
                .addPathPatterns("/superAdmin/**");
    }

    @Bean
    public AccessLimitInterceptor getAccessLimitInterceptor() {
        return new AccessLimitInterceptor();
    }

    @Bean
    public JwtInterceptor getJwtInterceptor() {
        return new JwtInterceptor();
    }

    @Bean
    public SuperAdminInterceptor getSuperAdminInterceptor() {
        return new SuperAdminInterceptor();
    }
}

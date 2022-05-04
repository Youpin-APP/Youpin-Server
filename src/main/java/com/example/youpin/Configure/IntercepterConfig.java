package com.example.youpin.Configure;

import com.example.youpin.Interceptor.LoginIntercepter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class IntercepterConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginIntercepter()).addPathPatterns("/userInfo/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}

package com.zhangzhigang.tm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.zhangzhigang.tm.interceptor.TMAuthInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	
	
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TMAuthInterceptor()).addPathPatterns("/*").excludePathPatterns("/logOut");
    }

}

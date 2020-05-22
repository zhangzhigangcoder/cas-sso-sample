package com.zhangzhigang.tb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.zhangzhigang.tb.interceptor.SSOAuthInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	
	
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SSOAuthInterceptor()).addPathPatterns("/*");
    }

//    @Bean
//    public HttpMessageConverter<String> responseBodyConverter() {
//        return new StringHttpMessageConverter(Charset.forName("UTF-8"));
//    }
}

package com.zhangzhigang.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;

import com.zhangzhigang.sso.listener.SSOHttpSessionListener;

@SpringBootApplication
public class SSOApplication {

	public static void main(String[] args) {
		SpringApplication.run(SSOApplication.class, args);
	}
	
	/**
	 * HttpSessionListener
	 */
	@Bean
	public ServletListenerRegistrationBean<SSOHttpSessionListener> myHttpSessionListener(RedisTemplate<String, Object> redisTemplate) {
		ServletListenerRegistrationBean<SSOHttpSessionListener> registration = new ServletListenerRegistrationBean<>();
		registration.setListener(new SSOHttpSessionListener(redisTemplate));
		return registration;
	}

}

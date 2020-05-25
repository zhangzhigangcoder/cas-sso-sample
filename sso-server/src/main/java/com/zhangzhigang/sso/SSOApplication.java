package com.zhangzhigang.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 24 * 60 * 60, redisNamespace = "CAS_SSO_SESSION")
@SpringBootApplication
public class SSOApplication {

	public static void main(String[] args) {
		SpringApplication.run(SSOApplication.class, args);
	}
}

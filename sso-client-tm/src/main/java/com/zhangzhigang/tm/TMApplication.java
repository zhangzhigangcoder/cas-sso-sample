package com.zhangzhigang.tm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import com.zhangzhigang.tm.properties.TMProperties;

@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60 * 60, redisNamespace = "TM_SESSION")
@EnableConfigurationProperties({TMProperties.class})
@SpringBootApplication
public class TMApplication {

	public static void main(String[] args) {
		SpringApplication.run(TMApplication.class, args);
	}

}

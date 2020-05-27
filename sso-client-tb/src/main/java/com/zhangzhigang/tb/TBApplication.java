package com.zhangzhigang.tb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import com.zhangzhigang.tb.properties.TBProperties;

@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 24 * 60 * 60, redisNamespace = "TB_SESSION")
@EnableConfigurationProperties({TBProperties.class})
@SpringBootApplication
public class TBApplication {

	public static void main(String[] args) {
		SpringApplication.run(TBApplication.class, args);
	}

}

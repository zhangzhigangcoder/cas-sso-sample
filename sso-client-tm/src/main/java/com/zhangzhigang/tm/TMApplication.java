package com.zhangzhigang.tm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.zhangzhigang.tm.properties.TMProperties;

@EnableConfigurationProperties({TMProperties.class})
@SpringBootApplication
public class TMApplication {

	public static void main(String[] args) {
		SpringApplication.run(TMApplication.class, args);
	}

}

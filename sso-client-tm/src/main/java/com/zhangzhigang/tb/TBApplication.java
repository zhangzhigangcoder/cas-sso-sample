package com.zhangzhigang.tb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.zhangzhigang.tb.properties.TMProperties;

@EnableConfigurationProperties({TMProperties.class})
@SpringBootApplication
public class TBApplication {

	public static void main(String[] args) {
		SpringApplication.run(TBApplication.class, args);
	}

}

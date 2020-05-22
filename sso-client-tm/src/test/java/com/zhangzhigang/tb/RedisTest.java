package com.zhangzhigang.tb;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.zhangzhigang.tm.TMApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TMApplication.class)
public class RedisTest {
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Test
	public void stringTest() {
		// 保存字符串
		stringRedisTemplate.opsForValue().set("aaa", "111");
		String value = stringRedisTemplate.opsForValue().get("aaa");
		System.out.println(value);
	}
	
	@Test
	public void cityTest() {
		Map<String, String> account = new HashMap<String, String>();
		account.put("username", "zhang");
		// 保存字符串
		redisTemplate.opsForValue().set("token:123456" , account, 600, TimeUnit.SECONDS);
		System.out.println((HashMap<String,String>) redisTemplate.opsForValue().get("token:123456"));
	}
	
}

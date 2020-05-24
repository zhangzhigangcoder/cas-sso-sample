package com.zhangzhigang.sso.listener;

import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.zhangzhigang.sso.config.SSOContext;
import com.zhangzhigang.sso.entity.ClientInfo;
import com.zhangzhigang.sso.utils.HttpClientUtil;

/**
 * 账号信息
 * 
 * @author zhangzhigang
 */
public class SSOHttpSessionListener implements HttpSessionListener {
	
	private RedisTemplate<String, Object> redisTemplate;
	
	public SSOHttpSessionListener(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		String token = (String) session.getAttribute("token");
		if (StringUtils.isEmpty(token)) {
			return;
		}
		System.out.println("认证中心清除token: " + token);
		
		// 销毁认证中心用户登录信息
		redisTemplate.delete(SSOContext.SSO_PREFIX + token);
		
		// 销毁所有客户端用户登录信息
		Set<ClientInfo> clientInfoSet = SSOContext.CLIENT_LOGOUT_URLS.remove(token);
		if (!CollectionUtils.isEmpty(clientInfoSet)) {
			clientInfoSet.forEach(c -> {
				try {
					HttpClientUtil.sendHttpRequest(c.getClientLogoutUrl(), c.getJsessionId());
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}
	}
	
}

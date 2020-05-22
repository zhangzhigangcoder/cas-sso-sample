package com.zhangzhigang.tm.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = TMProperties.prefix)
public class TMProperties {
	
	public static final String prefix = "tm";
	
    private String ssoServerUrlPrefix;
	
    private String clientHostUrl;

	public String getSsoServerUrlPrefix() {
		return ssoServerUrlPrefix;
	}

	public void setSsoServerUrlPrefix(String ssoServerUrlPrefix) {
		this.ssoServerUrlPrefix = ssoServerUrlPrefix;
	}

	public String getClientHostUrl() {
		return clientHostUrl;
	}

	public void setClientHostUrl(String clientHostUrl) {
		this.clientHostUrl = clientHostUrl;
	}
    
}

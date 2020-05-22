package com.zhangzhigang.tb.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = TBProperties.prefix)
public class TBProperties {
	
	public static final String prefix = "tb";
	
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

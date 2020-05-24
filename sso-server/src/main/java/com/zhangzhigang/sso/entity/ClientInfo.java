package com.zhangzhigang.sso.entity;

/**
 * 客户端地址信息
 * 
 * @author zhangzhigang
 */
public class ClientInfo {

	private String clientLogoutUrl;
	
	private String jsessionId;
	
	public ClientInfo(String clientLogoutUrl, String jsessionId) {
		this.clientLogoutUrl = clientLogoutUrl;
		this.jsessionId = jsessionId;
	}

	public String getClientLogoutUrl() {
		return clientLogoutUrl;
	}

	public void setClientLogoutUrl(String clientLogoutUrl) {
		this.clientLogoutUrl = clientLogoutUrl;
	}

	public String getJsessionId() {
		return jsessionId;
	}

	public void setJsessionId(String jsessionId) {
		this.jsessionId = jsessionId;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clientLogoutUrl == null) ? 0 : clientLogoutUrl.hashCode());
		result = prime * result + ((jsessionId == null) ? 0 : jsessionId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClientInfo other = (ClientInfo) obj;
		if (clientLogoutUrl == null) {
			if (other.clientLogoutUrl != null)
				return false;
		} else if (!clientLogoutUrl.equals(other.clientLogoutUrl))
			return false;
		if (jsessionId == null) {
			if (other.jsessionId != null)
				return false;
		} else if (!jsessionId.equals(other.jsessionId))
			return false;
		return true;
	}

}

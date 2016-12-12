package com.hola.mysdk.bean;

public class HolaUserInfo {
	private String token;
	@Override
	public String toString() {
		return "HolaUserInfo [token=" + token + ", loginType=" + loginType
				+ "]";
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getLoginType() {
		return loginType;
	}
	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	private String loginType;

}

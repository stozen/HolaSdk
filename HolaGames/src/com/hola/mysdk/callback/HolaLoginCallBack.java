package com.hola.mysdk.callback;

import com.hola.mysdk.bean.HolaUserInfo;

public abstract interface HolaLoginCallBack {
	public abstract void onSuccess(HolaUserInfo paramUserInfo);
	public abstract void onLoginFailed(String paramString);
	public abstract void onLoginCancel();
	public abstract void onLogoutSuccess();
}

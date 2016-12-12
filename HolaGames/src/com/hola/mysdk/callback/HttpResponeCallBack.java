package com.hola.mysdk.callback;

public interface HttpResponeCallBack {
	public void onResponeStart(String apiname);
	public void onLoading(String apiName, Object object);
	public void onSuccess(String apiName, Object object);
	public void onFailure(String apiName, Throwable t, int errorNo, String strMsg);
}

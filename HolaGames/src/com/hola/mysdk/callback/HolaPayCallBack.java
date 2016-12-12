package com.hola.mysdk.callback;

public abstract interface HolaPayCallBack {
	public abstract void onSuccess(Object paramObject);
	
	public abstract void onFail(Object paramObject);
	
	public abstract void onCancel();
}

package com.hola.alipay.sdk;


/**
 * 支付宝支付回调接口
 * 
 * @author xiaowei
 * 2016-12-12 下午3:40:12
 * wlcaption@qq.com
 */
public abstract interface AliPayCallback {
	public abstract void aliPayCalledBack(String paramString);
}

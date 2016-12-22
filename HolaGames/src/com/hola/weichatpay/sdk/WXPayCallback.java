package com.hola.weichatpay.sdk;

import com.tencent.mm.sdk.modelbase.BaseResp;

public abstract interface WXPayCallback {
	public abstract void wxPayCallback(BaseResp paramBaseResp);
}

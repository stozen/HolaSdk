package com.hola.weichatpay.sdk;

import android.content.Context;

import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPay {
	private IWXAPI api;
	private WXPayCallback mWxPayCallback;
	private static WXPay mWxPay;
	private Context mContext;
	
	private WXPay(Context context){
		this.mContext = context;
		this.api = WXAPIFactory.createWXAPI(context.getApplicationContext(), null);
	}
	
	public static WXPay getInstance(Context context){
		if(mWxPay == null){
			mWxPay = new WXPay(context);
		}
		return mWxPay;
	}
	
	public boolean isSupportWXPay(){
		return this.api.getWXAppSupportAPI() >=570425345;
	}
	
	public void wxpay(String content){
		sendPayReq(content);
	}
	
	public void setCallback(WXPayCallback callback){
		this.mWxPayCallback = callback;
	}
	
	public boolean isWXAppInstalled(){
		return this.api.isWXAppInstalled();
	}
	
	public void setResp(BaseResp baseResp){
		if(this.mWxPayCallback != null){
			this.mWxPayCallback.wxPayCallback(baseResp);
		}
	}
	
	public  void sendPayReq(String result){
		WXModel model     = WXModel.jsonData(result);
		PayReq request    = new PayReq();
		request.appId     = model.getAppId();
		request.partnerId = model.getPartnerId();
		request.prepayId  = model.getPrepayId();
		request.packageValue = model.getPackageValue();
		request.nonceStr  = model.getNonceStr();
		request.timeStamp = model.getTimeStamp();
		request.sign      = model.getSign();
		this.api.registerApp(model.getAppId());
		
		boolean b = this.api.sendReq(request);
	}
}

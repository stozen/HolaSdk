package com.hola.mysdk.holagames.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.avos.avoscloud.LogUtil.log;
import com.hola.mysdk.holagames.R;
import com.hola.weichatpay.sdk.Constants;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 这个类是为了显示微信支付的支付结果
 * @author xiaowei
 * 2016-12-16 上午10:37:49
 * wlcaption@qq.com
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

	private static final String TAG = "WXPayEntryActivity";
	
	private IWXAPI api;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.wechatpay_result);
		
		api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
		
		api.handleIntent(getIntent(), this);
	}
	
	@Override
	protected void onNewIntent(Intent intent){
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}
	
	
	
	@Override
	public void onReq(BaseReq req) {

	}

	@Override
	public void onResp(BaseResp resp) {
		log.d(TAG, "onPayFinish, errCode=" + resp.errCode);
		if(resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX){
			Toast.makeText(WXPayEntryActivity.this, resp.errStr + ";code=" + 
		String.valueOf(resp.errCode), Toast.LENGTH_SHORT).show();
		}
	}

}

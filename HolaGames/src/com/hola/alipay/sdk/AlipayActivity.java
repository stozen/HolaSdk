package com.hola.alipay.sdk;

import java.util.HashMap;
import java.util.Map;

import com.alipay.a.a.a;
import com.alipay.sdk.app.PayTask;
import com.hola.mysdk.holagames.HolaSDKPay;
import com.hola.mysdk.holagames.R;
import com.hola.mysdk.util.SignUtils;
import com.mysdk.test.OrderInfoUtil2_0;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/*
 * 说明:
 * 在真正的App里,privateKey等数据不要放在客户端，加签过程务必
 * 要放在服务端完成，防止商户私密数据泄露
 */
public class AlipayActivity extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.pay_main);
	}
	
	/**
	 * 支付宝支付业务
	 * 
	 * 在实际的项目中，privareKey等数据严禁放在客户端，加签过程务必要放在服务端完成;
	 * 防止商户私密数据泄露
	 */
	
	public void pay_all(View v){
		Intent intent = new Intent();
		intent.setClass(AlipayActivity.this, HolaSDKPay.class);
		startActivity(intent);
	}
	
	public void alipay_v2(View v){
		Map<String, String> params = OrderInfoUtil.buildOrderParamMap(Constans.APPID,"测试数据", "钻石", "0.01");
		String orderParam = OrderInfoUtil.buildOrderParam(params);
		String sign = OrderInfoUtil.getSign(params, Constans.RSA_PRIVATE);
		final String orderInfo = orderParam + "&" + sign;
		
		Runnable payRunnable = new Runnable() {
			
			@Override
			public void run() {
				PayTask alipay = new PayTask(AlipayActivity.this);
				Map<String, String> result = alipay.payV2(orderInfo, true);
				Log.i("msp", result.toString());
				
				Message msg = new Message();
				msg.what = Constans.SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};
		
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Constans.SDK_PAY_FLAG:{
				PayResult payResult = new PayResult((Map<String, String>) msg.obj);
				
				String resultInfo = payResult.getResult();
				String resultStatus = payResult.getResultStatus();
				
				if(TextUtils.equals(resultStatus, "9000")){
					Toast.makeText(AlipayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
				}else if(TextUtils.equals(resultStatus, "4000")){
					Toast.makeText(AlipayActivity.this, "系统异常", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(AlipayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
					break;
				}
			}
			case Constans.SDK_AUTH_FLAG:{
				
			}
				
			default:
				break;
			}
		};
	};
}

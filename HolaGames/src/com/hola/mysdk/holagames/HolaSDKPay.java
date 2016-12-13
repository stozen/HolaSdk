package com.hola.mysdk.holagames;

import java.util.Map;

import com.alipay.sdk.app.PayTask;
import com.hola.alipay.sdk.AlipayActivity;
import com.hola.alipay.sdk.Constans;
import com.hola.alipay.sdk.OrderInfoUtil;
import com.hola.alipay.sdk.PayResult;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.Toast;


/**
 * @author xiaowei
 * 2016-12-13 上午10:27:40
 * wlcaption@qq.com
 */
public class HolaSDKPay extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hola_cashier_activity);
		initView();
	}
	
	private void initView(){
		View view_back = findViewById(R.id.pay_backIcon); //返回上层
		view_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(HolaSDKPay.this, AlipayActivity.class);
				startActivity(intent);
			}
		});
		final RadioButton alipay_radio     = (RadioButton) findViewById(R.id.alipay_radio);
		final RadioButton weichatpay_radio = (RadioButton) findViewById(R.id.weichatpay_radio);
		View view_next_step = findViewById(R.id.next_step);
		view_next_step.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!alipay_radio.isChecked() || !weichatpay_radio.isChecked()){
					if(alipay_radio.isChecked() == true){
						alipay_v2();
					}
					if(weichatpay_radio.isChecked() == true){
						Toast.makeText(HolaSDKPay.this, "微信支付", Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.makeText(HolaSDKPay.this, "请选择支付方式", Toast.LENGTH_SHORT).show();
				}
				Intent intent = new Intent();
				intent.setClass(HolaSDKPay.this, AlipayActivity.class);
				startActivity(intent);
			}
		});
	}
	
	public void alipay_v2(){
		Map<String, String> params = OrderInfoUtil.buildOrderParamMap(Constans.APPID);
		String orderParam = OrderInfoUtil.buildOrderParam(params);
		String sign = OrderInfoUtil.getSign(params, Constans.RSA_PRIVATE);
		final String orderInfo = orderParam + "&" + sign;
		
		Runnable payRunnable = new Runnable() {
			
			@Override
			public void run() {
				PayTask alipay = new PayTask(HolaSDKPay.this);
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
					Toast.makeText(HolaSDKPay.this, "支付成功", Toast.LENGTH_SHORT).show();
				}else if(TextUtils.equals(resultStatus, "4000")){
					Toast.makeText(HolaSDKPay.this, "系统异常", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(HolaSDKPay.this, "支付失败", Toast.LENGTH_SHORT).show();
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

package com.hola.mysdk.holagames;

import com.hola.alipay.sdk.AlipayActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

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

		View view_next_step = findViewById(R.id.next_step);
		view_next_step.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(HolaSDKPay.this, AlipayActivity.class);
				startActivity(intent);
			}
		});
		
	}

}

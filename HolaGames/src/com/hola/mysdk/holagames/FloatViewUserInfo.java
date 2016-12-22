package com.hola.mysdk.holagames;

import com.avos.avoscloud.LogUtil.log;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class FloatViewUserInfo extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.float_view_user);
		initView();
	}
	
	public void initView(){
		log.d("TAG", "nice");
	}

}

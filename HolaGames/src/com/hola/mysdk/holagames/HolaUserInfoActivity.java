package com.hola.mysdk.holagames;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class HolaUserInfoActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.float_user_center);
		final TextView view = (TextView) findViewById(R.id.float_user_name);
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				view.setText("你好");
			}
		});
	}

}

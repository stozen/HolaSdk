package com.hola.mysdk.holagames;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class HolaRegisterActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.hola_register_account);
	}
}

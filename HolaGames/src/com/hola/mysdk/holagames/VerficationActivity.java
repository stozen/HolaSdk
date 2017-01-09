package com.hola.mysdk.holagames;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class VerficationActivity extends Activity {
	private EditText verfication_code;
	private Button verficationBtn;
	private TextView back_lastBtn;
	private String verfication_codeStr;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_verfication);
		initView();
	}
	
	private void initView(){
		verfication_code = (EditText) findViewById(R.id.verfication_input);
		verficationBtn = (Button) findViewById(R.id.now_verfication_btn);
		back_lastBtn = (TextView) findViewById(R.id.back_verfication_Btn);
		
		verficationBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				verfication_codeStr = verfication_code.getText().toString();
				if(!TextUtils.isEmpty(verfication_codeStr)){
					Intent intent = new Intent(VerficationActivity.this, PasswordUpdateActivity.class);
					intent.putExtra("verfication_code", verfication_codeStr);
					startActivity(intent);
					finish();
				}else{
					Toast.makeText(VerficationActivity.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		back_lastBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(VerficationActivity.this, PassWordReset.class);
				startActivity(intent);
				finish();
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		ExitDialog exitDialog = new ExitDialog(VerficationActivity.this);
		exitDialog.show();
	}

}

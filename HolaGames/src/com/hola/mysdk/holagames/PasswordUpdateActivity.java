package com.hola.mysdk.holagames;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.UpdatePasswordCallback;
import com.hola.mysdk.util.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PasswordUpdateActivity extends Activity {
	private Button now_passwordResetBtn;  //立即重置
	private EditText password_text;       //密码框
	private TextView back_last_ui;        //返回键
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_passwordupdate);
		initView();
	}
	
	private void initView(){
		now_passwordResetBtn = (Button) findViewById(R.id.now_resetpassword_btn);
		password_text = (EditText) findViewById(R.id.update_word);
		back_last_ui = (TextView) findViewById(R.id.password_update_back);
		
		now_passwordResetBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String passwordStr = password_text.getText().toString();
				String smsCode = getIntent().getStringExtra("verfication_code");
				Log.d("verfication_code", smsCode);
				if(!TextUtils.isEmpty(passwordStr)){
					if(Utils.isPassword(passwordStr)){
						AVUser.resetPasswordBySmsCodeInBackground(smsCode, passwordStr, new UpdatePasswordCallback() {
							
							@Override
							public void done(AVException e) {
								if(e == null){
									Toast.makeText(PasswordUpdateActivity.this, "密码已经重置", Toast.LENGTH_SHORT).show();
									Intent intent = new Intent(PasswordUpdateActivity.this, LoginActivity.class);
									startActivity(intent);
									finish();
								}
								else{
									e.getMessage();
								}
							}
						});
					}else{
						Toast.makeText(PasswordUpdateActivity.this, "输入密码的格式不对，6-16位数字和字母组合", Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.makeText(PasswordUpdateActivity.this, "密码为空", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		back_last_ui.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(PasswordUpdateActivity.this, VerficationActivity.class);
				startActivity(intent);
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		ExitDialog exitDialog = new ExitDialog(PasswordUpdateActivity.this);
		exitDialog.show();
	}

}

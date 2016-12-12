package com.hola.mysdk.holagames;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.hola.mysdk.util.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PassWordReset extends Activity {
	private Button retrieve_passwordBtn; //找回密码按钮
	private TextView login_account; //账号
	private TextView back_login_ui;    //返回登录
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_passwordreset);
		initView();
	}
	private void initView(){
		retrieve_passwordBtn = (Button) findViewById(R.id.Retrieve_password);
		login_account        = (TextView) findViewById(R.id.login_account);
		back_login_ui        = (TextView) findViewById(R.id.back_login_btn);
		retrieve_passwordBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String account = login_account.getText().toString();
				if(!TextUtils.isEmpty(account)){
					if(Utils.isNumber(account)){
						AVUser.requestPasswordResetBySmsCodeInBackground(account, new RequestMobileCodeCallback() {
							
							@Override
							public void done(AVException e) {
								if(e == null){
									Intent intent = new Intent(PassWordReset.this,VerficationActivity.class);
									startActivity(intent);
									finish();
								}else{
									Toast.makeText(PassWordReset.this, e.getMessage(),Toast.LENGTH_SHORT).show();
								}
							}
						});
					}else{
						Toast.makeText(PassWordReset.this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
					}
						
				}else{
					Toast.makeText(PassWordReset.this, "账号不能为空", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		back_login_ui.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(PassWordReset.this, LoginActivity.class);
				startActivity(intent);
			}
		});
	}

}

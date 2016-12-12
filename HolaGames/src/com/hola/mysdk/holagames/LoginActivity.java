package com.hola.mysdk.holagames;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.hola.alipay.sdk.AlipayActivity;
import com.hola.mysdk.callback.HttpResponeCallBack;
import com.hola.mysdk.util.Utils;

public class LoginActivity extends Activity implements HttpResponeCallBack {
	private EditText loginAccount;   //用户名
	private EditText loginPassword;  //密码
	private Button loginBtn;		 //登录按钮
	private TextView registerBtn;    //注册按钮
	private TextView passwordReset;  //密码重置
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		initView();
	}
	
	private void initView(){
		loginAccount = (EditText)  findViewById(R.id.login_account);
		loginPassword = (EditText) findViewById(R.id.login_password);
		loginBtn = (Button) findViewById(R.id.login_btn);
		registerBtn = (TextView) findViewById(R.id.register_btn);
		passwordReset = (TextView) findViewById(R.id.passwor_reset);
		loginBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String account = loginAccount.getText().toString();  //用户名
				String password = loginPassword.getText().toString(); //密码
				Log.d("login", account  +":" + password);
				if(!TextUtils.isEmpty(account) && !TextUtils.isEmpty(password)){//信息不完整判断
					if(Utils.isNumber(account) && Utils.isPassword(password)){ //账号和密码输入验证
						AVUser.loginByMobilePhoneNumberInBackground(account, password, new LogInCallback<AVUser>() {
				            @Override
				            public void done(AVUser avUser, AVException e) {
				            	if(e == null){
				            		Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
				            		Intent intent = new Intent(LoginActivity.this, AlipayActivity.class);
				            		startActivity(intent);
				            		finish();
				            	}else{
				            		Toast.makeText(LoginActivity.this, "账号或密码不对", Toast.LENGTH_SHORT).show();
				            	}
				            }
				        });
					}else{//账号或密码格式不对
						Toast.makeText(LoginActivity.this, "手机或密码格式不对", Toast.LENGTH_SHORT).show();
					}
				}else{//eme
					Toast.makeText(LoginActivity.this, "信息不完整", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		registerBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
				LoginActivity.this.startActivity(intent);
				finish();
			}
		});
		
		passwordReset.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this, PassWordReset.class);
				LoginActivity.this.startActivity(intent);
				finish();
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		this.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
		        builder.setTitle("退出");
		        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int whichButton) {
		                //退出apk
		                dialog.dismiss();
		                LoginActivity.this.finish();
		            }
		        });
		        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int whichButton) {
		                // dialog隐藏
		                dialog.dismiss();
		            }
		        });
		        builder.create().show();
			}
		});
		
	}
	

	@Override
	public void onResponeStart(String apiname) {
		
	}

	@Override
	public void onLoading(String apiName, Object object) {

	}

	@Override
	public void onSuccess(String apiName, Object object) {

	}

	@Override
	public void onFailure(String apiName, Throwable t, int errorNo, String strMsg) {

	}

}

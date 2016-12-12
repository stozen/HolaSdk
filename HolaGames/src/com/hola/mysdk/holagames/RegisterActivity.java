package com.hola.mysdk.holagames;

import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;
import com.hola.mysdk.callback.HttpResponeCallBack;
import com.hola.mysdk.service.RegisterCodeTimer;
import com.hola.mysdk.service.RegisterCodeTimerService;
import com.hola.mysdk.util.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity implements HttpResponeCallBack {
	private EditText phoneNumber; //电话号码
	private EditText password;    //注册密码
	private EditText verification_code; //验证码
	private Button   registBtn;   //注册
	private TextView verficationBtn; //获取验证码
	private TextView user_terms; //用户须知
	private TextView back_login_ui;// 返回上层页面
	private CheckBox termsCheckBox; //用户协议按钮
	private Intent mIntent;
	private String phoneStr;
	private String verificationStr;
	private String passwordStr;
	private WebView webView;
	private View mBack;
	private View mForward;
	private View mRefresh;
	private View backView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.hola_register_account);
		initView();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		stopService(mIntent);
	}
	
	private void initView(){
		phoneNumber = (EditText) findViewById(R.id.regist_nick);
		password = (EditText) findViewById(R.id.regist_account);
		verification_code = (EditText) findViewById(R.id.regist_password);
		registBtn = (Button) findViewById(R.id.regist_btn);
		verficationBtn = (TextView) findViewById(R.id.verfation_btn);
		RegisterCodeTimerService.setHandler(mCodeHandler);
		user_terms = (TextView) findViewById(R.id.user_terms_text);
		termsCheckBox = (CheckBox) findViewById(R.id.user_terms);
		webView = (WebView) findViewById(R.id.register_action_webview);
		backView = findViewById(R.id.back_icon);
		mBack = findViewById(R.id.hola_back);
		mForward = findViewById(R.id.hola_forward);
		mRefresh = findViewById(R.id.hola_refresh);
		//back_login_ui = (TextView) findViewById(R.id.back_login);
		user_terms.getPaint().setFlags(8);
		mIntent = new Intent(RegisterActivity.this, RegisterCodeTimerService.class);
		verficationBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				phoneStr = phoneNumber.getText().toString();
				verificationStr = verification_code.getText().toString();
				passwordStr = password.getText().toString();
				if(!TextUtils.isEmpty(phoneStr) && !TextUtils.isEmpty(passwordStr)){
					if(Utils.isNumber(phoneStr)){
						if(Utils.isPassword(passwordStr)){
						verficationBtn.setEnabled(false);
						startService(mIntent);
						AVUser user = new AVUser();
						user.setUsername(phoneStr);
						user.setPassword(passwordStr);
						user.put("mobilePhoneNumber", phoneStr);
						user.signUpInBackground(new SignUpCallback() {
							
							@Override
							public void done(AVException e) {
								if(e == null){
									Log.d("MO", "success");
								}else
									Log.d("MO", "Error"+e);
							}
						});
						}else{
							Toast.makeText(RegisterActivity.this, "密码必须是6-16字母和数字", Toast.LENGTH_SHORT).show();
						}
					}else{
						Toast.makeText(RegisterActivity.this, "输入电话号码有误", Toast.LENGTH_SHORT).show();
						}
					}else{
						Toast.makeText(RegisterActivity.this, "输入信息不完整", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		registBtn.setOnClickListener(new OnClickListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				phoneStr = phoneNumber.getText().toString();
				passwordStr= password.getText().toString();
				verificationStr = verification_code.getText().toString();
				if(!TextUtils.isEmpty(phoneStr) && !TextUtils.isEmpty(passwordStr) && !TextUtils.isEmpty(verificationStr) && !termsCheckBox.isChecked()){//判断输入信息是否为空
					if(Utils.isNumber(phoneStr)){
						if(Utils.isPassword(passwordStr)){
							//AVOSCloud.verifyCodeInBackground(verificationStr, phoneStr, new AVMobilePhoneVerifyCallback() {
							AVUser.verifyMobilePhoneInBackground(verificationStr, new AVMobilePhoneVerifyCallback() {
								@Override
								public void done(AVException e) {
									if(e == null){
										Log.d("sms", "Success");
										Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
										Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
										startActivity(intent);
										finish();
									}else{
										Log.d("sms", e.getMessage());
									}
								}
							});
						}else{
							Toast.makeText(RegisterActivity.this, "密码必须是6-16位的数字和字母", Toast.LENGTH_SHORT).show();
						}
					}else{
						Toast.makeText(RegisterActivity.this, "电话号码不合法", Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.makeText(RegisterActivity.this, "输入信息不完整，请选择用户协议", Toast.LENGTH_SHORT).show(); //输入信息不完整
				}
				
			}
		});
		
		user_terms.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("URL", "http://www.holagames.cn/protocol/protocol.html");
				intent.setClass(RegisterActivity.this, UserTermsActivity.class);
				startActivity(intent);
				//webView.loadUrl("https://sso.le.com/protocol/registerprotocol");
			}
		});
		
		backView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(RegisterActivity.this, LoginActivity.class);
				startActivity(intent);
			}
		});
		
		mBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(RegisterActivity.this, LoginActivity.class);
				startActivity(intent);
			}
		});
		
		
	}
	
	private void backEvent(){
		if(this.webView.canGoBack()){
			backPressEvent();
		}else{
			
		}
		finish();
	}
	
	public void backPressEvent(){
		if(this.webView.canGoBack()){
			this.webView.goBack();
		}
	}
	
	@Override
	public void onBackPressed() {
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
		        builder.setTitle("退出");
		        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int whichButton) {
		                //退出apk
		                dialog.dismiss();
		                RegisterActivity.this.finish();
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
	public void onFailure(String apiName, Throwable t, int errorNo,
			String strMsg) {
		
	}
	
	
	
	Handler mCodeHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what == RegisterCodeTimer.IN_RUNNING){
				verficationBtn.setText(msg.obj.toString());
			}else if(msg.what == RegisterCodeTimer.END_RUNNING){
				verficationBtn.setEnabled(true);
				verficationBtn.setText(msg.obj.toString());
			}
		};
	};
	
}

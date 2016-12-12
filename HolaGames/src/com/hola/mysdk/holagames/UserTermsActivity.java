package com.hola.mysdk.holagames;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

public class UserTermsActivity extends Activity {
	private WebView webView;
	private final static String USER_URL = "https://sso.le.com/protocol/registerprotocol";
	private String url;
	private View mForward;
	private View mBack;
	private View mBtnBar;
	private View mRefresh;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		Intent intent = new Intent();
		this.url = intent.getStringExtra("URL");
		setContentView(R.layout.hola_user_terms);
		initView();
	}
	
	private void initView(){
	this.webView = (WebView) findViewById(R.id.other_accoint_webview);
	View backView = findViewById(R.id.backIcon); //返回上一层
	this.mBack = findViewById(R.id.back);
	this.mForward = findViewById(R.id.forward);
	this.mRefresh = findViewById(R.id.refresh);
	this.webView.getSettings().setUseWideViewPort(true);
	this.webView.getSettings().setLoadWithOverviewMode(true);
		backView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UserTermsActivity.this.backEvent();
			}
		});
		this.webView.setWebChromeClient(new WebChromeClient(){
			public void onProgressChanged(WebView view, int newProgress)
		      {
		        super.onProgressChanged(view, newProgress);
		      }
		});
		
		if(TextUtils.isEmpty(this.url)){
			this.url = "http://www.holagames.cn/protocol/protocol.html";
		}
		this.webView.loadUrl(this.url);
		
		mBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("mBack", "true2");
				UserTermsActivity.this.backEvent();
			}
		});
		mForward.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(UserTermsActivity.this.webView.canGoForward()){
					UserTermsActivity.this.webView.goForward();
				}
			}
		});
		mRefresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UserTermsActivity.this.refreshPressEvent();
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
	
	@Override
	public void onBackPressed() {
		backEvent();
	}
	
	private void backPressEvent(){
		if(this.webView.canGoBack()){
			this.webView.goBack();
		}
	}
	
	private void refreshPressEvent(){
		this.webView.reload();
	}
}

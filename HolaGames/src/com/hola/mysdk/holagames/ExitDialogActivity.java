package com.hola.mysdk.holagames;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class ExitDialogActivity extends Activity {
	private BroadcastReceiver exitReceiver;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.hola_sdk_exit_dialog);
		initView();
	}
	
	private void initView(){
		Button quitebtn = (Button) findViewById(R.id.close_button);
		quitebtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				System.exit(0);
				Process.killProcess(0);
			}
		});
		
		Button continuebtn = (Button) findViewById(R.id.continue_game);
		continuebtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ExitDialogActivity.this.finish();
			}
		});
		this.exitReceiver = new BroadcastReceiver(){

			@Override
			public void onReceive(Context context, Intent intent) {
				ExitDialogActivity.this.finish();
			}
		};
		
		registerReceiver(this.exitReceiver, new IntentFilter("NOTICE_EXIT_RECEIVER"));
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (null != this.exitReceiver) {
		      unregisterReceiver(this.exitReceiver);
		      this.exitReceiver = null;
		    }
	}

}

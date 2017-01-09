package com.hola.mysdk.holagames;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.widget.Button;

public class ExitDialog extends Dialog {
	private Context mContext;
	private Button mConfirm;
	private Button mCancel;

	public ExitDialog(Context context) {
		super(context,R.style.ExitDialog);
		mContext = context;
	}
	
	public ExitDialog(Context context,int theme){
		super(context,theme);
		mContext = context;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_dialog);
		this.setCanceledOnTouchOutside(false);
		mConfirm = (Button) findViewById(R.id.dialog_confirm);
		mConfirm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				System.exit(0);
				Process.killProcess(0);
			}
		});
		
		mCancel = (Button) findViewById(R.id.dialog_cancel);
		mCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ExitDialog.this.dismiss();
			}
		});
	}
}

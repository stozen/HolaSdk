package com.hola.mysdk.floatwindow;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.sax.StartElementListener;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.hola.mysdk.holagames.FloatViewUserInfo;
import com.hola.mysdk.holagames.R;

public class SubFloatWindow extends PopupWindow implements View.OnClickListener{
    private final static int[] itemPicRes = new int[]{
    		R.drawable.uac_gameassist_forum, //论坛
            R.drawable.uac_gameassist_gift,  //礼包
            R.drawable.uac_gameassist_info,  //个人信息
            R.drawable.uac_gameassist_message,//消息
            R.drawable.uac_gameassist_info,  //关于我们
    };
    private final static String itemContent[] = new String[]{"账号", "福利", "论坛", "消息","关于我们"};

    private Context mCtx;
    private boolean isOnLeft;

    public SubFloatWindow(Context context){
        mCtx = context;
        setContentView(getWindowView());
        setWidth(WindowUtil.dip2px(mCtx, 220));
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setOutsideTouchable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);

    }

    private View getWindowView(){
        LinearLayout ll = new LinearLayout(mCtx);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        ll.setGravity(Gravity.CENTER);
        ll.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        if(isOnLeft) {
            ll.setBackgroundResource(R.drawable.uac_gameassist_left_bg);
        }else{
            ll.setBackgroundResource(R.drawable.uac_gameassist_right_bg);
        }
        for(int i=0; i<itemPicRes.length; i++){
            ll.addView(getItemView(100+i,  itemPicRes[i], itemContent[i]));
        }
        return ll;
    }

    private View getItemView(int viewTag, int picResId, String content){
        LinearLayout llItem = new LinearLayout(mCtx);
        llItem.setOrientation(LinearLayout.VERTICAL);
        llItem.setGravity(Gravity.CENTER);
        llItem.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
        ImageView ivPic = new ImageView(mCtx);
        ivPic.setImageResource(picResId);
        TextView tvContent = new TextView(mCtx);
        tvContent.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tvContent.setText(content);
        tvContent.setTextSize(12);
        tvContent.setTextColor(Color.parseColor("#787878"));
        llItem.addView(ivPic);
        llItem.addView(tvContent);
        llItem.setTag(viewTag);
        llItem.setOnClickListener(this);
        return llItem;
    }

    public void setOnLeft(boolean flag){
        isOnLeft = flag;
        if(isOnLeft) {
            getContentView().setBackgroundResource(R.drawable.uac_gameassist_left_bg);
        }else{
            getContentView().setBackgroundResource(R.drawable.uac_gameassist_right_bg);
        }
    }

    @Override
    public void onClick(View v) {
        Integer viewTag = (Integer) v.getTag();
        switch (viewTag){
            case 100:
            	Activity currentActivity1 = (Activity) v.getContext();
            	Intent intent1 = new Intent(currentActivity1,FloatViewUserInfo.class);
            	mCtx.startActivity(intent1);
                Toast.makeText(mCtx, "账号", Toast.LENGTH_SHORT).show();
                break;
            case 101:
            	Activity currentActivity2 = (Activity) v.getContext();
            	Intent intent2 = new Intent(currentActivity2,FloatViewUserInfo.class);
            	mCtx.startActivity(intent2);
                Toast.makeText(mCtx, "福利", Toast.LENGTH_SHORT).show();
                break;
            case 102:
            	Activity currentActivity3 = (Activity) v.getContext();
            	Intent intent3 = new Intent(currentActivity3,FloatViewUserInfo.class);
            	mCtx.startActivity(intent3);
                Toast.makeText(mCtx, "论坛", Toast.LENGTH_SHORT).show();
                break;
            case 103:
            	Activity currentActivity4 = (Activity) v.getContext();
            	Intent intent4 = new Intent(currentActivity4, FloatViewUserInfo.class);
            	mCtx.startActivity(intent4);
            	Toast.makeText(mCtx, "消息", Toast.LENGTH_SHORT).show();
                break;
            case 104:
            	Toast.makeText(mCtx, "关于我们", Toast.LENGTH_SHORT).show();
            	Activity currentActivity5 = (Activity) v.getContext();
            	Intent intent5 = new Intent(currentActivity5, FloatViewUserInfo.class);
            	mCtx.startActivity(intent5);
            	break;
        }
    }
    
    
    private void ShowUserInfo(){
    	AlertDialog.Builder builder = new Builder(mCtx);
    	builder.setIcon(android.R.drawable.alert_dark_frame);
    	builder.setTitle("火拉游戏");
    	final String[] items = new String[]{
    			"用户信息",
    			"攻略",
    			"福利"
    	};
    	builder.setItems(items, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
    	builder.show();
    }
}

package com.hola.mysdk.floatwindow;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.WindowManager;

/**
 */
public class FloatWindowManager {
    private Context mCtx;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams params;
    private MainFloatWindow mainFloatWindow;
    private boolean isShow;

    public FloatWindowManager(Context context){
        mCtx = context;
        mWindowManager = (WindowManager) mCtx.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        initParams();
        mainFloatWindow = new MainFloatWindow(context, mWindowManager, params);
    }

    private void initParams(){
        params = new WindowManager.LayoutParams();
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.format = PixelFormat.TRANSLUCENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.LEFT |Gravity.TOP;
        params.y = WindowUtil.getScreenHeight(mCtx)/2;
    }

    public void showFloatWindow(){
        if(isShow){
            return;
        }
        mWindowManager.addView(mainFloatWindow,params);
        isShow = true;
    }

    public void hideFloatWindow(){
        if(!isShow){
            return;
        }
        mWindowManager.removeView(mainFloatWindow);
        isShow = false;
    }

}

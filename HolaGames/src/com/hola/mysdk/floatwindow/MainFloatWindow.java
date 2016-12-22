package com.hola.mysdk.floatwindow;

import com.hola.mysdk.holagames.R;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;


public class MainFloatWindow extends LinearLayout implements PopupWindow.OnDismissListener{
    private final static int MSG_UPDATE_POS = 0x01;
    private final static int MSG_WINDOW_HIDE = 0x02;

    /** 悬浮窗往两侧靠的移动速度**/
    private final static int SPEED = 100;
    /** 悬浮窗隐藏前的等待时间**/
    private final static int WAIT_TIME = 3000;

    private Context mCtx;
    private ImageView ivDefaultWindow;
    private ImageView ivHideWindow;
    private WindowManager.LayoutParams mParams;
    private WindowManager mWindowManager;
    /**相对于View的x、y坐标 **/
    private float xInView, yInView;
    /**在屏幕中的x、y坐标 **/
    private float xDown, yDown;
    /**子悬浮窗 **/
    private SubFloatWindow mSubFloatWindow;
    /**当前悬浮窗位置是否在屏幕左半边 **/
    private boolean isOnLeft;
    /**当前状态悬浮窗能否半隐藏 **/
    private boolean canHide;
    /**当前状态悬浮窗是否处于半隐藏状态 **/
    private boolean isHide;


    public MainFloatWindow(Context context, WindowManager windowManager, WindowManager.LayoutParams params) {
        super(context);
        mSubFloatWindow = new SubFloatWindow(context);
        mWindowManager = windowManager;
        mParams = params;
        mCtx = context;

        initWindowView();
        this.addView(ivDefaultWindow);
        this.addView(ivHideWindow);
        isOnLeft = true;
        mSubFloatWindow.setOnLeft(true);
        mSubFloatWindow.setOnDismissListener(this);
        canHide = true;
        waitToHideWindow();
    }

    private void initWindowView(){
        ivDefaultWindow = new ImageView(mCtx);
        this.ivDefaultWindow.setImageResource(R.drawable.uac_gameassist_photo_default);
        this.ivDefaultWindow.setBackgroundResource(R.drawable.uac_gameassist_photo_bg);
        ivHideWindow = new ImageView(mCtx);
        ivHideWindow.setImageResource(R.drawable.uac_gameassist_photo_brand_left);
        //显示ivDefaultWindow，隐藏ivHideWindow
        setWindowHide(false);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                canHide = false;
                xInView = event.getX();
                yInView = event.getY();
                xDown = event.getRawX();
                yDown = event.getRawY() - WindowUtil.getStatusBarHeight(mCtx);
                break;

            case MotionEvent.ACTION_MOVE:
                if(!isHide) {
                    Message message = new Message();
                    message.what = MSG_UPDATE_POS;
                    message.arg1 = (int) (event.getRawX() - xInView);
                    message.arg2 = (int) (event.getRawY() - WindowUtil.getStatusBarHeight(mCtx));
                    mHandler.sendMessage(message);

                }
                break;

            case MotionEvent.ACTION_UP:
                canHide = true;
                if(xDown == event.getRawX() && yDown == event.getRawY() - WindowUtil.getStatusBarHeight(mCtx)){
                    //点击事件
                    onClick();
                }else if(!isHide) {
                    autoMoveToSide();
                }
                break;
        }

        return true;
    }

    /**
     * 自动平移到两侧停靠
     */
    private void autoMoveToSide() {

        new Thread() {
            @Override
            public void run() {
                //保存x、y坐标
                int[] location = new int[2];
                getLocationOnScreen(location);
                isOnLeft = location[0]+getWidth()/2 < WindowUtil.getScreenWidth(mCtx)/2;
                mSubFloatWindow.setOnLeft(isOnLeft);
                int moveParam = isOnLeft ? -10 : 10;    //每次移动10个像素
                while (true) {
                    location[0] = location[0] + moveParam;
                    int newX = location[0];
                    int newY = location[1];
                    if (isOnLeft && newX<=0) {     //已移至最左侧
                        newX = 0;
                        Message message = new Message();
                        message.what = MSG_UPDATE_POS;
                        message.arg1 = newX;
                        message.arg2 = newY;
                        mHandler.sendMessage(message);
                        break;
                    }else if(!isOnLeft && newX>= WindowUtil.getScreenWidth(mCtx)){     //已移至最右侧
                        newX = WindowUtil.getScreenWidth(mCtx);
                        Message message = new Message();
                        message.what = MSG_UPDATE_POS;
                        message.arg1 = newX;
                        message.arg2 = newY;
                        mHandler.sendMessage(message);
                        break;
                    } else {
                        Message message = new Message();
                        message.what = MSG_UPDATE_POS;
                        message.arg1 = newX;
                        message.arg2 = newY;
                        mHandler.sendMessage(message);
                    }

                    try {
                        Thread.sleep(100/ SPEED);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    /**
     * 更新悬浮窗的位置
     * @param x
     * @param y
     */
    private void updateWindowPos(final float x, final float y){
        mParams.x = (int) x;
        mParams.y = (int) y;
        mWindowManager.updateViewLayout(MainFloatWindow.this, mParams);

        if(x<=0){
            ivHideWindow.setImageResource(R.drawable.uac_gameassist_photo_brand_left);
            waitToHideWindow();
        }else if(x>= WindowUtil.getScreenWidth(mCtx)){
            ivHideWindow.setImageResource(R.drawable.uac_gameassist_photo_brand_right);
            waitToHideWindow();
        }

    }


    private void onClick(){
        if(ivDefaultWindow.getVisibility() == VISIBLE && ivHideWindow.getVisibility() == GONE) {
            if (!mSubFloatWindow.isShowing()) {
                if (isOnLeft) {
                    mSubFloatWindow.showAtLocation(this, Gravity.CENTER | Gravity.START, getWidth(), 0);
                    ivDefaultWindow.setBackgroundResource(R.drawable.uac_gameassist_photo_right_bg);
                } else {
                    mSubFloatWindow.showAtLocation(this, Gravity.CENTER | Gravity.END, getWidth(), 0);
                    ivDefaultWindow.setBackgroundResource(R.drawable.uac_gameassist_photo_left_bg);
                }
                canHide = false;
            } else {
                mSubFloatWindow.dismiss();
                ivDefaultWindow.setBackgroundResource(R.drawable.uac_gameassist_photo_default);
            }
        }else if(ivDefaultWindow.getVisibility() == GONE && ivHideWindow.getVisibility() == VISIBLE){
            setWindowHide(false);
            waitToHideWindow();
        }
    }

    /**
     * 悬浮窗菜单的消失事件监听
     */
    @Override
    public void onDismiss() {
        ivDefaultWindow.setBackgroundResource(R.drawable.uac_gameassist_photo_default);
        canHide = true;
        waitToHideWindow();
    }

    /**
     * 等待一定时间后隐藏悬浮窗
     */
    private void waitToHideWindow(){
        if(!canHide){
            return;
        }
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(WAIT_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(canHide) {
                    mHandler.sendEmptyMessage(MSG_WINDOW_HIDE);
                }
            }
        }.start();
    }

    private MyHandler mHandler = new MyHandler();
    class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_UPDATE_POS:
                    updateWindowPos(msg.arg1, msg.arg2);
                    break;
                case MSG_WINDOW_HIDE:
                    setWindowHide(true);
                    break;
            }
        }
    }

    /**
     * 设置悬浮窗是否半隐藏
     * @param flag
     */
    private void setWindowHide(boolean flag){
        if(flag){
            ivDefaultWindow.setVisibility(GONE);
            ivHideWindow.setVisibility(VISIBLE);
            mSubFloatWindow.dismiss();
        }else{
            ivDefaultWindow.setVisibility(VISIBLE);
            ivHideWindow.setVisibility(GONE);
        }
        isHide = flag;
    }
}

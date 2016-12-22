package com.hola.mysdk.holagames;

import com.hola.mysdk.floatwindow.FloatWindowManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * 实现的功能分析
 * 1、悬浮框可以在屏幕范围内自由拖动
 * 2、悬浮窗拖到屏幕中任意位置后放开手指，它会向近的一侧移开并停靠
 * 3、停靠在屏幕两侧时，经过一定时间后能自动切换到半隐藏状态
 * 4、点击半隐藏的悬浮窗，显示悬浮窗，再次点击则显示悬浮窗侧拉菜单
 * 
 * 开始涉及知识点
 * 1、windowManager悬浮窗口控制类
 * 2、点击触摸事件处理
 * 3、简单的多线程编程
 * 4、PopupWindow
 * 
 * @author xiaowei
 * 2016-12-22 上午11:13:23
 * wlcaption@qq.com
 */
public class FloatViewActivity extends Activity {
    private Button btnShow;
    private Button btnHide;
    private FloatWindowManager floatWindowManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.floatview_activity);
        btnShow = (Button) findViewById(R.id.btnShow);
        btnHide = (Button) findViewById(R.id.btnHide);
        btnShow.setOnClickListener(clickListener);
        btnHide.setOnClickListener(clickListener);
        floatWindowManager = new FloatWindowManager(this);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnShow:
                    floatWindowManager.showFloatWindow();
                    break;
                case R.id.btnHide:
                    floatWindowManager.hideFloatWindow();
                    break;
            }
        }
    };
    protected void onResume() {
    	floatWindowManager.showFloatWindow();
    	super.onResume();
    };
    
    protected void onPause() {
    	floatWindowManager.hideFloatWindow();
    	super.onPause();
    };

    @Override
    protected void onDestroy() {
        floatWindowManager.hideFloatWindow();
        super.onDestroy();
    }
}

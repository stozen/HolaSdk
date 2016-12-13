package com.hola.alipay.sdk;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.alipay.sdk.app.PayTask;

import android.app.Activity;
import android.os.Handler;

/**
 * 支付宝支付接口
 * 
 * @author xiaowei
 * 2016-12-12 下午2:40:29
 * wlcaption@qq.com
 */
public class Alipay {
	private AliPayCallback mAliPayCallback;
	
	public void setmAliPayCallback(AliPayCallback mAliPayCallback) {
		this.mAliPayCallback = mAliPayCallback;
	}
	

	private static final BlockingQueue<Runnable> uiWorkQueue = new LinkedBlockingQueue(8);
	private static final ThreadFactory uiThreadFactory = new ThreadFactory() {
		private final AtomicInteger mCount = new AtomicInteger(1);
		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r, "lepayTask" + this.mCount.getAndIncrement());
		}
		
	};
	private static final ThreadPoolExecutor uiExecutor = new ThreadPoolExecutor(
		    2, 8, 1L, TimeUnit.SECONDS, uiWorkQueue, uiThreadFactory, new ThreadPoolExecutor.DiscardOldestPolicy());

	protected static Handler handler = new Handler();
	public void pay(final Activity activity, final String content){
		Runnable payRunnable = new Runnable() {
			
			@Override
			public void run() {
				PayTask alipay = new PayTask(activity);
				final String result = alipay.pay(content, true);
				Alipay.execUi(new Runnable() {
					
					@Override
					public void run() {
						Alipay.this.mAliPayCallback.aliPayCalledBack(result);
					}
				});
				
			}
		};
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}
	
	public static void execUi(final Runnable command){
		Runnable runnable = new Runnable() {
			
			public void run() {
				Alipay.handler.post(command);
			}
		};
		uiExecutor.execute(runnable);
	}
}

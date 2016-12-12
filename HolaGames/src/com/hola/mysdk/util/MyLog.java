package com.hola.mysdk.util;

import android.util.Log;

public class MyLog {
	public static final boolean DEBUG = true;
	
	public static void d(String tag, String content, int lineNum){
		StringBuffer str = new StringBuffer();
		str.append(content);
		str.append(" [line]=" + lineNum);
		Log.i(tag, str.toString());
		str = null;
	}
	
	public static void i(String tag, String content, int lineNum){
		StringBuffer str = new StringBuffer();
		str.append(content);
		str.append(" [line]=" + lineNum);
		Log.i(tag, str.toString());
		str = null;
	}
	
	public static void e(String tag, String content, int lineNum){
		StringBuffer str = new StringBuffer();
		str.append(content);
		str.append(" [line]=" + lineNum);
		Log.i(tag, str.toString());
		str = null;
	}
	
	public static void v(String tag, String content, int lineNum){
		StringBuffer str = new StringBuffer();
		str.append(content);
		str.append(" [line]=" + lineNum);
		Log.i(tag, str.toString());
		str = null;
	}
	
	public static int getLine(){
		StackTraceElement[] stacks = new Throwable().getStackTrace();
		return stacks[1].getLineNumber();
	}

}

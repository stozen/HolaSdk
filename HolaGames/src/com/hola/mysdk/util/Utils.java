package com.hola.mysdk.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;

public class Utils {
	public static final String TAG = Utils.class.getSimpleName();
	
	public static int getIntMetaData(Context context, String str){
		try {
			ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
			int msg = appInfo.metaData.getInt(str);
			MyLog.d(TAG, msg+"", MyLog.getLine());
			return msg;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public static String getStrMetaData(Context context, String str){
		try {
			ApplicationInfo appInfo;
			appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
			String msg = appInfo.metaData.getString(str);
			if(TextUtils.isEmpty(msg)){
				msg = appInfo.metaData.getInt(str) + "";
				if(msg.equals("0")){
					msg = null;
				}
			}
			MyLog.d(TAG, msg, MyLog.getLine());
			return msg;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean getBooleanMetaData(Context context, String str){
		return false;
	}
	
	public static boolean isHasChinese(String content){
		return false;
	}
	
	public static boolean isEmail(String email){
		String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}
	
	public static boolean isNumber(String phoneNumber){
		String str = "^1(3|4|5|7|8)\\d{9}$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(phoneNumber);
		return m.matches();
	}
	
	public static boolean isPassword(String password){
		String str = "^(?=.{6,16})(?=.*[a-z])(?=.*[0-9])[0-9a-z]*$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(password);
		return m.matches();
	}
	
	public static String getTime(){
		return String.valueOf(System.currentTimeMillis()/1000L);
	}
	
	public static String getDay(){
		Date date = new Date();
		SimpleDateFormat localDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return localDateFormat.format(date);
	}

}

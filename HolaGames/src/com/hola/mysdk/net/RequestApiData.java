package com.hola.mysdk.net;

import com.hola.mysdk.bean.AnalyticalRegistInfo;
import com.hola.mysdk.callback.HttpResponeCallBack;

public class RequestApiData {
	private static RequestApiData instance = null;
	private HttpResponeCallBack mresponeCallBack = null;
	
	public static RequestApiData getInstance(){
		if(instance == null){
			instance = new RequestApiData();
		}
		return instance;
	}
	
	public void getRegisterData(String nickName, String Email, String numberPhone, String password, Class<AnalyticalRegistInfo> clazz,HttpResponeCallBack responeCallBack){
		mresponeCallBack = responeCallBack;
		//String tagUrl = 
	}
}

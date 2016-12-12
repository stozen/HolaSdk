package com.hola.alipay.sdk;

import java.util.Map;

import android.text.TextUtils;


/**
 * 支付结果的返回情况
 * 
 * @author xiaowei
 * 2016-12-9 下午1:52:03
 * wlcaption@qq.com
 */
public class PayResult {
	private String resultStatus;
	private String result;
	private String memo;
	
	@Override
	public String toString() {
		return "PayResult [resultStatus=" + resultStatus + ", result=" + result
				+ ", memo=" + memo + "]";
	}

	public PayResult(Map<String, String> rawResult){
		if(rawResult == null){
			return;
		}
		
		for(String key : rawResult.keySet()){
			if(TextUtils.equals(key, "resultStatus")){
				resultStatus = rawResult.get(key);
			}else if(TextUtils.equals(key, "result")){
				result = rawResult.get(key);
			}else if(TextUtils.equals(key, "memo")){
				memo = rawResult.get(key);
			}
		}
	}
	
	public String getResultStatus() {
		return resultStatus;
	}
	public String getResult() {
		return result;
	}
	public String getMemo() {
		return memo;
	}

}

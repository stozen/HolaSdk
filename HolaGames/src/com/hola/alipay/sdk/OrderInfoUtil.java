package com.hola.alipay.sdk;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import android.provider.ContactsContract.Contacts.Data;

import com.alipay.a.a.k;
import com.hola.mysdk.util.SignUtils;

public class OrderInfoUtil {
	
	/**
	 * 构造支付订单参数列表
	 * @param app_id
	 * @return
	 */
	public static Map<String, String> buildOrderParamMap(String app_id){
		Map<String, String> keyValues = new HashMap<String, String>();
		
		keyValues.put("app_id", app_id);
		
		keyValues.put("biz_content", "{\"timeout_express\":\"30m\",\"product_code\":\"QUICK_MSECURITY_PAY\",\"total_amount\":\"0.01\",\"subject\":\"1\",\"body\":\"我是测试数据\",\"out_trade_no\":\"" + getOutTradeNo() +  "\"}");
		
		keyValues.put("charset", "utf-8");
		
		keyValues.put("method", "alipay.trade.app.pay");
		
		keyValues.put("sign_type", "RSA");
		
		keyValues.put("timestamp", "2016-07-29 16:55:53");
		
		keyValues.put("version", "1.0");
		return keyValues;
	}
	
	
	/**
	 * 构造支付订单参数信息
	 * 
	 * @param map
	 * @return
	 */
	public static String buildOrderParam(Map<String, String> map){
		List<String> keys = new ArrayList<String>(map.keySet());
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < keys.size()-1; i++){
			String key = keys.get(i);
			String value = map.get(key);
			sb.append(buildKeyValue(key, value, true));
			sb.append("&");
		}
		String tailkey = keys.get(keys.size() - 1);
		String tailvalue = map.get(tailkey);
		sb.append(buildKeyValue(tailkey, tailvalue, true));
		
		return sb.toString();
	}
	
	/**
	 * 拼接键值对
	 * 
	 * @param key
	 * @param value
	 * @param isEncode
	 * @return
	 */
	private static String buildKeyValue(String key, String value, boolean isEncode){
		StringBuffer sb = new StringBuffer();
		sb.append(key);
		sb.append("=");
		if(isEncode){
			try {
				sb.append(URLEncoder.encode(value,"UTF-8"));
			} catch (UnsupportedEncodingException e) {
				sb.append(value);
			}
		}else{
			sb.append(value);
		}
		return sb.toString();
	}
	
	/**
	 * 对支付参数信息进行签名的方法
	 * 
	 * @param map
	 * @param rsaKey
	 * @return
	 */
	
	public static String getSign(Map<String, String> map, String rsaKey){
		List<String> keys = new ArrayList<String>(map.keySet());
		//key 排序
		Collections.sort(keys);
		
		StringBuffer authInfo = new StringBuffer();
		for(int i = 0; i< keys.size()-1; i++){
			String key = keys.get(i);
			String value = map.get(key);
			authInfo.append(buildKeyValue(key, value, false));
			authInfo.append("&");
		}
		
		String tailKey = keys.get((keys.size() - 1));
		String tailValue = map.get(tailKey);
		authInfo.append(buildKeyValue(tailKey, tailValue, false));
		
		String oriSign = SignUtils.Sign(authInfo.toString(), rsaKey);
		String encodedSign = "";
		
		try {
			encodedSign = URLEncoder.encode(oriSign,"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "sign" + encodedSign;
	}
	
	/**
	 * 外部订单号
	 * @return
	 */

	private static String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMDDHHmmss",Locale.getDefault());
		java.util.Date date = new java.util.Date();
		String key = format.format(date);
		
		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0,15);
		return key;
	}

}

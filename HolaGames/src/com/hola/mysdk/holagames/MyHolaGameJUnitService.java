package com.hola.mysdk.holagames;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class MyHolaGameJUnitService {
	/**
	 * 拼接键值对
	 * 
	 * @param key
	 * @param value
	 * @param isEncode
	 * @return
	 */
	public String buildKeyValue(String key, String value, boolean isEncode){
		StringBuffer sb = new StringBuffer();
		sb.append(key);
		sb.append("=");
		if(isEncode){
			try {
//				sb.append(URLEncoder.encode(value,"UTF-8"));
				sb.append(value);
			} catch (Exception e) {
				sb.append(value);
				e.printStackTrace();
			}
		}else{
			sb.append(value);
		}
		return sb.toString();
	}
	
	/**
	 * 构造支付订单参数列表
	 * @param app_id
	 * @return
	 */
	public  Map<String, String> buildOrderParamMap(String app_id){
		Map<String, String> keyValues = new HashMap<String, String>();
		
		keyValues.put("app_id", app_id);
		
		keyValues.put("biz_content", "{\"timeout_express\":\"30m\",\"product_code\":\"QUICK_MSECURITY_PAY\",\"total_amount\":\"0.01\",\"subject\":\"1\",\"body\":\"我是测试数据\",\"out_trade_no\":\"" + "123" +  "\"}");
		
		keyValues.put("charset", "utf-8");
		
		keyValues.put("method", "alipay.trade.app.pay");
		
		keyValues.put("sign_type", "RSA");
		
		keyValues.put("timestamp", "2016-07-29 16:55:53");
		
		keyValues.put("version", "1.0");
		return keyValues;
	}
	
	
	public  String buildOrderParam(Map<String, String> map){
		List<String> keys = new ArrayList<String>(map.keySet());
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < keys.size()-1; i++){
			String key = map.get(i);
			String value = map.get(key);
			sb.append(buildKeyValue(key, value, true));
			sb.append("&");
		}
		String tailkey = keys.get(keys.size() - 1);
		String tailvalue = map.get(tailkey);
		sb.append(buildKeyValue(tailkey, tailvalue, true));
		
		return sb.toString();
	}

	
	public String getOutTradeNo(){
		SimpleDateFormat format = new SimpleDateFormat("MMDDHHmmss", Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);
		
		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0,15);
		
		return key;
	}
}

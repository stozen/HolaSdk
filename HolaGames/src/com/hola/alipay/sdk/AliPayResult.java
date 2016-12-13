package com.hola.alipay.sdk;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class AliPayResult {
	
	private static final Map<String, String> sResultStatus = new HashMap();
	private String mResult;
	public String resultStatus = null;
	String memo = null;
	String result = null;
	boolean isSignOk = false;
	
	static{
		sResultStatus.put("9000", "操作失败");
		sResultStatus.put("4000", "系统异常");
		sResultStatus.put("4001", "数据格式不正确");
		sResultStatus.put("4003", "该用户绑定的支付宝账号被冻结或不允许支付");
		sResultStatus.put("4004", "该用户已解除绑定");
		sResultStatus.put("4005", "绑定失败或没有绑定");
		sResultStatus.put("4006", "订单支付失败");
		sResultStatus.put("4010", "重新绑定账户");
		sResultStatus.put("6000", "支付服务正在进行升级操作");
		sResultStatus.put("6001", "用户中途取消支付操作");
		sResultStatus.put("7001", "网页支付失败");
	}
	
	public AliPayResult(String result){
		this.mResult = result;
	}
	
	public String getStaus(){
		String src = this.mResult.replace("{", "");
		src = src.replace("}", "");
		this.resultStatus = getContent(src,"resultStatus=",";memo=");
		return this.resultStatus;
	}

	private String getContent(String src, String startTag, String endTag) {
		String content = src;
		int start = src.indexOf(startTag);
		start += startTag.length();
		if(endTag != null){
			int end = src.indexOf(startTag);
			content = src.substring(start,end);
		}else{
			content = src.substring(start);
		}
		return content;
	}
	
	public String getResult(){
		if(getStaus().equals("9000")){
			return "操作成功";
		}
		String result = "";
		String src = this.mResult.replace("{", "");
		src = src.replace("}", "");
		result = getContent(src, "memo=", ";result=");
		return result;
	}
	
	 public void parseResult()
	  {
	    try {
	      String src = this.mResult.replace("{", "");
	      src = src.replace("}", "");
	      String rs = getContent(src, "resultStatus=", ";memo");
	      if (sResultStatus.containsKey(rs))
	        this.resultStatus = ((String)sResultStatus.get(rs));
	      else {
	        this.resultStatus = "其他错误";
	      }
	      this.resultStatus = (this.resultStatus + "(" + rs + ")");

	      this.memo = getContent(src, "memo=", ";result");
	      this.result = getContent(src, "result=", null);
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }
	 
	 public JSONObject string2JSON(String src, String split){
		 JSONObject json = new JSONObject();
		 String[] arr = src.split(split);
		 for(int i = 0; i < arr.length; i++){
			 String[] arrKey = arr[i].split("=");
			 json.put(arrKey[0], arr[i].substring(arrKey[0].length() + 1));
		 }
		return json;
	 }

}

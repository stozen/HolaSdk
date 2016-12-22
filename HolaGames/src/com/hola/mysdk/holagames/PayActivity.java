package com.hola.mysdk.holagames;

import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.hola.alipay.sdk.Constans;
import com.hola.mysdk.util.Utils;
import com.hola.weichatpay.sdk.Constants;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * 微信支付
 * 1、将app注册到微信
 * 2、下单
 * @author xiaowei
 * 2016-12-16 下午4:02:58
 * wlcaption@qq.com
 */
public class PayActivity extends Activity {
	PayReq req;
	final IWXAPI api = WXAPIFactory.createWXAPI(this, null);
	private Map<String, String> resultunifiedorder;
	private StringBuffer sb;
	private TextView show;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wechatpay_pay);
		show = (TextView) findViewById(R.id.editText_prepay_id);
		req = new PayReq();
		sb = new StringBuffer();
		api.registerApp(Constants.APP_ID);
		
		//生成予支付订单
		Button getOrderBtn = (Button) findViewById(R.id.unifiedorder_btn);
		getOrderBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				GetPrepayIdTask getPrepayIdTask = new GetPrepayIdTask();
				getPrepayIdTask.execute();
			}
		});
		
		//生成签名参数
		Button getSignBtn = (Button) findViewById(R.id.appay_pre_btn);
		getSignBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				genPayReq();
			}
		});
		
		//调起支付
		Button apppayBtn = (Button) findViewById(R.id.appay_btn);
		apppayBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sendPayReq();
			}
		});
	}
	
	private void genPayReq(){
		req.appId = Constants.APP_ID;
		req.partnerId = Constants.MCH_ID;
		req.prepayId = resultunifiedorder.get("prepay_id");
		req.packageValue = "Sign=WXPay";
		req.nonceStr = genNonceStr();
		req.timeStamp = String.valueOf(genTimeStamp());
		
		List<NameValuePair> signParams = new LinkedList<NameValuePair>();
		signParams.add(new BasicNameValuePair("appid",req.appId));
		signParams.add(new BasicNameValuePair("noncestr",req.nonceStr));
		signParams.add(new BasicNameValuePair("package",req.packageValue));
		signParams.add(new BasicNameValuePair("partnerid",req.partnerId));
		signParams.add(new BasicNameValuePair("prepayid",req.prepayId));
		signParams.add(new BasicNameValuePair("timestamp",req.timeStamp));
		
		req.sign = genAppSign(signParams);
		
		sb.append("sign\n" + req.sign + "\n\n");
		show.setText(sb.toString());
		Log.e("orion", signParams.toString());  //
	}
	
	private void sendPayReq(){
		api.registerApp(Constants.APP_ID);
		api.sendReq(req);
	}
	
	private class GetPrepayIdTask extends AsyncTask<Void,Void, Map<String, String>>{

		private ProgressDialog dialog;
		
		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(PayActivity.this, getString(R.string.app_tip), getString(R.string.getting_prepayid));
		}
		
		@Override
		protected void onPostExecute(Map<String, String> result) {
			if(dialog != null){
				dialog.dismiss();
			}
			sb.append("prepay_id\n" + result.get("prepay_id") + "\n\n");
			show.setText(sb.toString());
			
			resultunifiedorder = result;
			
		}
		
		@Override
		protected Map<String, String> doInBackground(Void... params) {
			String url = String.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
			String entity = genProductArgs();
			
			Log.e("orion", entity);
			byte[] buf = Utils.httpPost(url, entity);
			
			String content = new String(buf);
			Log.e("orion", content);
			Map<String, String> xml = decodeXml(content);
			return xml;
			
		}
		
		@Override
		protected void onCancelled() {
			super.onCancelled();
		}
	}
	
	/**
	 * 对支付调起参数进行签名
	 * @param params
	 * @return
	 */
	private String genAppSign(List<NameValuePair> params){
		StringBuffer sb = new StringBuffer();
		
		for(int i =0; i < params.size(); i++){
			sb.append(params.get(i).getName());
			sb.append("=");
			sb.append(params.get(i).getValue());
			sb.append("&");
		}
		
		sb.append("key=");
		sb.append(Constants.API_KEY);
		String appSign = Utils.getMessageDigest(sb.toString().getBytes()).toUpperCase();
		Log.e("orion", appSign);
		return appSign;
	}
	
	/**
	 * 生成签名
	 * @param params 签名的串
	 * @return
	 */
	private String genPackagesSign(List<NameValuePair> params){
		StringBuffer sb = new StringBuffer();
		
		for(int i = 0; i< params.size(); i++){
			sb.append(params.get(i).getName());
			sb.append("=");
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(Constants.API_KEY);
		
		String packageSign = Utils.getMessageDigest(sb.toString().getBytes()).toUpperCase();
		Log.e("orion", packageSign);
		return packageSign;
		
	}
	
	//生成时间戳
	private long genTimeStamp(){
		return System.currentTimeMillis() / 1000;
	}
	
	/**
	 * 随机生成订单号
	 * 
	 * @return 订单号
	 */
	private String genOutTradNo(){
		Random random = new Random();
		return Utils.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
	}
	
	/**生成随机字符串
	 * 
	 * @return 随机数
	 */
	private String genNonceStr(){
		Random random = new Random();
		return Utils.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
	}
	
	/**
	 * //生成预支付订单
	 * @return string 订单信息
	 */
	private String genProductArgs(){ 
		StringBuffer xml = new StringBuffer();
		
		String nonceStr = genNonceStr();
		xml.append("</xml>");
		List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
		packageParams.add(new BasicNameValuePair("appid", Constants.APP_ID));
		packageParams.add(new BasicNameValuePair("body","weixin"));
		packageParams.add(new BasicNameValuePair("mch_id",Constants.MCH_ID));
		packageParams.add(new BasicNameValuePair("nonce_str",nonceStr));
		packageParams.add(new BasicNameValuePair("notify_url",Constants.NOTIFY_URL));
		packageParams.add(new BasicNameValuePair("out_trade_no",genOutTradNo()));
		packageParams.add(new BasicNameValuePair("spbill_create_ip",Constants.IP_ADDRESS));
		packageParams.add(new BasicNameValuePair("total_fee","1")); //支付金额
		packageParams.add(new BasicNameValuePair("trade_type","APP"));
		
		String sign = genPackagesSign(packageParams);
		packageParams.add(new BasicNameValuePair("sign",sign));
		
		String xmlString = toXml(packageParams);
		
		return xmlString;
		
	}
	
	/**
	 * 将签过名的串转化成xml,返回整体作为一个完整串
	 * @param params
	 * @return
	 */
	private String toXml(List<NameValuePair> params){
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		for(int i = 0; i < params.size(); i++){
			sb.append("<" + params.get(i).getName()+">");
			sb.append(params.get(i).getValue());
			sb.append("</" + params.get(i).getName() + ">");
		}
		sb.append("</xml>");
		Log.e("orion", sb.toString());
		return sb.toString();
		
	}
	
	/***
	 * 解析xml
	 * @param content
	 * @return
	 */
	private Map<String, String> decodeXml(String content){
		try {
			Map<String, String> xml = new HashMap<String, String>();
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(new StringReader(content));
			int event = parser.getEventType();
			while(event != XmlPullParser.END_DOCUMENT){
				String nodeName = parser.getName();
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					
					break;
				case XmlPullParser.START_TAG:
					if("xml".equals(nodeName) == false){
						xml.put(nodeName, parser.nextText());
					}
					break;
					
					case XmlPullParser.END_TAG:
					break;
				}
				event = parser.next();
			}
			return xml;
		} catch (Exception e) {
			Log.e("orion", e.toString());
		}
		return null;
		
	}

}

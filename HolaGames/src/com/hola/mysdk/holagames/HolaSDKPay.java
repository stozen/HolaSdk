package com.hola.mysdk.holagames;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.hola.alipay.sdk.AlipayActivity;
import com.hola.alipay.sdk.Constans;
import com.hola.alipay.sdk.OrderInfoUtil;
import com.hola.alipay.sdk.PayResult;
import com.hola.mysdk.util.SignUtils;
import com.hola.mysdk.util.Utils;
import com.hola.weichatpay.sdk.Constants;
import com.hola.weichatpay.sdk.WXPay;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.Toast;


/**
 * hola pay
 * 
 * @author xiaowei
 * 2016-12-13 上午10:27:40
 * wlcaption@qq.com
 */
public class HolaSDKPay extends Activity {
	private IWXAPI api;
	private Context mContext;
	private static final int TIMELINE_SUPPORTED_VERSION = 0x21020001;
	public static final String PARTNER = "2088702085756334";
	public static final String SELLER = "2088702085756334";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hola_cashier_activity);
		api = WXAPIFactory.createWXAPI(this, Constants.APP_ID,false);
		api.registerApp(Constants.APP_ID);
		initView();
		
	}
	
	private void initView(){
		View view_back = findViewById(R.id.pay_backIcon); //返回上层
		view_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(HolaSDKPay.this, AlipayActivity.class);
				startActivity(intent);
			}
		});
		final RadioButton alipay_radio     = (RadioButton) findViewById(R.id.alipay_radio);
		final RadioButton weichatpay_radio = (RadioButton) findViewById(R.id.weichatpay_radio);
		View view_next_step = findViewById(R.id.next_step);
		view_next_step.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				int wxSdkVersion = api.getWXAppSupportAPI();
				if(!alipay_radio.isChecked() || !weichatpay_radio.isChecked()){
					if(alipay_radio.isChecked() == true){
						//alipay_v2();
						alipay_auth();
					}
					if(weichatpay_radio.isChecked() == true){
						Toast.makeText(HolaSDKPay.this, "wechatPay", Toast.LENGTH_SHORT).show();
						if(api.isWXAppInstalled()){
							if(wxSdkVersion >= TIMELINE_SUPPORTED_VERSION){
								weipay_v2();
							}else{
								Toast.makeText(HolaSDKPay.this, "请安装最新版的微信", Toast.LENGTH_SHORT).show();
							}
						}else{
							Toast.makeText(HolaSDKPay.this, "请安装微信", Toast.LENGTH_SHORT).show();
						}
					}
				}else{
					Toast.makeText(HolaSDKPay.this, "请选择支付方式", Toast.LENGTH_SHORT).show();
				}
//				Intent intent = new Intent();
//				intent.setClass(HolaSDKPay.this, AlipayActivity.class);
//				startActivity(intent);
			}
		});
	}
	
	
	/*
	 * 吊起sdk支付 老版本的移动支付
	 */
	public void alipay_v21(){
		//订单
		String orderInfo = getOrderInfo("测试的商品","钻石","0.01");
		
		//对订单做RSA签名
		String sign = sign(orderInfo);
		
		try {
			sign = URLEncoder.encode(sign,"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//完整的符合支付参数规范的订单信息
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();
	
		Runnable payRunnable = new Runnable() {
			
			@Override
			public void run() {
				PayTask alipay = new PayTask(HolaSDKPay.this);
				Map<String, String> result = alipay.payV2(payInfo, true);
				
				Message message = new Message();
				message.what = Constans.SDK_PAY_FLAG;
				message.obj = result;
				mHandler.sendMessage(message);
			}
		};
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}
	
	/*
	 * 获取签名的方式
	 */
	public String getSignType() {
		return "sigin_type=\"RSA\"";
	}

	/*
	 * 待签名的订单信息
	 */
	private String sign(String content) {
		return SignUtils.Sign(content, Constans.RSA_PRIVATE);
	}

	public String getOrderInfo(String subject, String body, String price) {
		//签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\" ";
		
		//签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";
		
		//商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";
		
		//商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";
		
		//商品详细
		orderInfo += "&body=" + "\"" + body + "\"";
		
		//商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";
		
		//服务器异步通知页面Url
		orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm" + "\"";
		
		//服务器接口名称。固定值
		orderInfo += "&service=" + "\"" + "mobile.securitypay.pay\"";
		
		//支付类型，固定值
		orderInfo += "&payment_type=\"1\"";
		
		//参数编码方式
		orderInfo += "&_inout_charset=\"utf-8\"";
		
		//设置未付款交易的超时时间
		orderInfo += "it_b_pay=\"30m\"";
		
		//支付宝处理完成请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";
		return orderInfo;
	}

	/*
	 * 生成商户订单号，该值在商户端应该保持唯一
	 */
	private String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);
		Random random = new Random();
		key = key + random.nextInt();
		key = key.substring(0,15);
		return key;
	}

	public void alipay_v2(){
		Map<String, String> params = OrderInfoUtil.buildOrderParamMap(Constans.APPID, "测试数据", "钻石", "0.01");
		String orderParam = OrderInfoUtil.buildOrderParam(params);
		String sign = OrderInfoUtil.getSign(params, Constans.RSA_PRIVATE);
		final String orderInfo = orderParam + "&" + sign;
		
		Runnable payRunnable = new Runnable() {
			
			@Override
			public void run() {
				PayTask alipay = new PayTask(HolaSDKPay.this);
				Map<String, String> result = alipay.payV2(orderInfo, true);
				Log.i("msp", result.toString());
				
				Message msg = new Message();
				msg.what = Constans.SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};
		
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}
	
	public void alipay_auth(){
		
		/*
		 * 这里只是为了方便直接向商户展示支付宝的整个支付流程;所以这里加签过程直接放在客户端完成
		 * 真实App里,privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成
		 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险;
		 * authInfo的获取必须来自服务端
		 */
		
		Map<String, String> authInfoMap = OrderInfoUtil.buildAuthInfoMap(Constans.PID, Constans.APPID, Constans.TARGETID);
		String info = OrderInfoUtil.buildOrderParam(authInfoMap);
		String sign = OrderInfoUtil.getSign(authInfoMap, Constans.RSA_PRIVATE);
		final String authInfo = info + "&" + sign;
		
		Runnable authRunnable = new Runnable() {
			
			@Override
			public void run() {
				AuthTask authTask = new AuthTask(HolaSDKPay.this);
				
				Map<String, String> result = authTask.authV2(authInfo, true);
				Message message = new Message();
				message.what = Constans.SDK_AUTH_FLAG;
				message.obj = result;
				mHandler.sendMessage(message);
			}
		};
		
		Thread authThread = new Thread(authRunnable);
		authThread.start();
	}
	
	public void weipay_v2(){
		String url = "http://wxpay.weixin.qq.com/pub_v2/app/app_pay.php?plat=android";
		byte[] buf = Utils.httpGet(url);
		if(buf != null && buf.length > 0){
			String content = new String(buf);
			Log.e("get server pay params", content);
			JSONObject mJson;
			try {
				mJson = new JSONObject(content);
				if(mJson != null && !mJson.has("retcode")){
					PayReq req = new PayReq();
					req.appId = mJson.getString("appid");
					req.partnerId = mJson.getString("partnerid");
					req.prepayId = mJson.getString("prepayid");
					req.nonceStr = mJson.getString("noncestr");
					req.timeStamp = mJson.getString("timestamp");
					req.packageValue = mJson.getString("packagevalue");
					req.sign = mJson.getString("sign");
					req.extData = "app data";
					api.sendReq(req);
				}else{
					Toast.makeText(HolaSDKPay.this, "返回错误", Toast.LENGTH_SHORT).show();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			Log.d("PAY_GET", "服务器请求错误");
			Toast.makeText(HolaSDKPay.this, "服务器请求错误", Toast.LENGTH_SHORT).show();
		}
	}
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Constans.SDK_PAY_FLAG:{
				PayResult payResult = new PayResult((Map<String, String>) msg.obj);
				
				String resultInfo = payResult.getResult();
				String resultStatus = payResult.getResultStatus();
				
				if(TextUtils.equals(resultStatus, "9000")){
					Toast.makeText(HolaSDKPay.this, "支付成功", Toast.LENGTH_SHORT).show();
				}else if(TextUtils.equals(resultStatus, "4000")){
					Toast.makeText(HolaSDKPay.this, "系统异常", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(HolaSDKPay.this, "支付失败", Toast.LENGTH_SHORT).show();
					break;
				}
			}
			case Constans.SDK_AUTH_FLAG:{
				Toast.makeText(HolaSDKPay.this, "检查结果为:" + msg.obj, Toast.LENGTH_SHORT).show();
			}
				
			default:
				break;
			}
		};
	};

}

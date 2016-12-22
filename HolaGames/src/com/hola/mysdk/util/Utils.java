package com.hola.mysdk.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.logging.Log;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

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
	
	public static byte[] httpGet(final String url){
		if(url == null || url.length() == 0){
			android.util.Log.e("url", "httpGet url is null");
			return null;
		}
		
		HttpClient httpClient = getNewHttpClient();
		
		HttpGet httpGet = (HttpGet) new org.apache.http.client.methods.HttpGet(url);
		
		try {
			HttpResponse response = httpClient.execute(httpGet);
			if(response.getStatusLine().getStatusCode()!=HttpStatus.SC_OK){
				return null;
			}
			return EntityUtils.toByteArray(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static byte[] httpPost(String url, String entity){
		if(url == null || url.length() == 0){
			android.util.Log.e("post", "httpPost url is null");
			return null;
		}
		
		HttpClient httpClient = getNewHttpClient();
		
		HttpPost httpPost = new HttpPost(url);
		
		try {
			httpPost.setEntity(new StringEntity(entity));
			httpPost.setHeader("Aeeept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
			
			HttpResponse response = httpClient.execute(httpPost);
			if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
				return null;
			}
			
			return EntityUtils.toByteArray(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static HttpClient getNewHttpClient(){
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null,null);
			
			SSLSocketFactory sf = new SSLSocketFactoryEx(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			
			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
			
			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			registry.register(new Scheme("https", sf, 443));
			
			ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
			
			return new DefaultHttpClient(ccm, params);
			
		} catch (Exception e) {

			return new DefaultHttpClient();
		}
	}
	
	//MD5算法，将传入的buffer进行加密处理
	public final static String getMessageDigest(byte[] buffer){
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(buffer);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}
}

	class SSLSocketFactoryEx extends SSLSocketFactory{
		SSLContext sslContext = SSLContext.getInstance("TLS");

		public SSLSocketFactoryEx(KeyStore truststore)
				throws NoSuchAlgorithmException, KeyManagementException,
				KeyStoreException, UnrecoverableKeyException {
			super(truststore);
			
			TrustManager tm = new X509TrustManager(){

				@Override
				public void checkClientTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
					
				}

				@Override
				public void checkServerTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
					
				}

				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
				
			};
			sslContext.init(null, new TrustManager[]{tm}, null);
		}
		
		public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException{
			return sslContext.getSocketFactory().createSocket(socket,host,port,autoClose);
		}
		
		public Socket createSocket() throws IOException{
			return sslContext.getSocketFactory().createSocket();
		}
	}

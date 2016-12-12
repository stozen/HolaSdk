package com.hola.mysdk.util;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;

import android.util.Base64;


/**
 * 对订单信息进行签名,对密钥进行验签
 * 
 * @author xiaowei
 * 2016-12-9 下午12:13:13
 * wlcaption@qq.com
 */
public class SignUtils {
	
	private static final String ALGORITHM       = "RSA"; //算法名称
	
	private static final String SIGN_ALTORITHMS = "SHA1WithRSA";
	
	private static final String DEFAULT_CHARSET = "UTF-8";
	
	/**
	 * 签名方法
	 * 
	 * @param content 
	 * @param privateKey
	 * @return
	 */
	public static String Sign(String content, String privateKey){
		try {
		PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(com.hola.alipay.sdk.Base64.decode(privateKey));
		KeyFactory keyf = KeyFactory.getInstance(ALGORITHM);
		PrivateKey priKey = keyf.generatePrivate(priPKCS8);
		Signature signature = Signature.getInstance(SIGN_ALTORITHMS);
		signature.initSign(priKey);
		signature.update(content.getBytes(DEFAULT_CHARSET));
		
		byte[] signed = signature.sign();
		return com.hola.alipay.sdk.Base64.encode(signed);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	

}

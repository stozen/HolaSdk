package com.mysdk.test;

import java.util.Map;

import android.test.AndroidTestCase;
import android.util.Log;

public class MyHolaGameJUnitServiceTest extends AndroidTestCase {
	public void testbBuildKeyValue() throws Exception{
		MyHolaGameJUnitService service = new MyHolaGameJUnitService();
		Map<String, String> params = service.buildOrderParamMap("111111");
		String orderParam = service.buildOrderParam(params);
		Log.d("myvalue", orderParam);
	}
	
	public void TestTradeNo() throws Exception{
		MyHolaGameJUnitService service = new MyHolaGameJUnitService();
	    String tradeNo =service.getOutTradeNo();
	    Log.d("tradeNo", tradeNo);
	}

}

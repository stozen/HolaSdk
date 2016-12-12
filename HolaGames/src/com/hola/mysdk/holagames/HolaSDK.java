package com.hola.mysdk.holagames;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.hola.mysdk.bean.HolaPayInfos;
import com.hola.mysdk.callback.HolaExitCallBack;
import com.hola.mysdk.callback.HolaInitCallBack;
import com.hola.mysdk.callback.HolaLoginCallBack;
import com.hola.mysdk.callback.HolaLogoutCallBack;
import com.hola.mysdk.callback.HolaPayCallBack;
import com.hola.mysdk.callback.HolaSDKInterface;
import com.hola.mysdk.config.HolaAppConfig;
import com.hola.mysdk.util.MyLog;
import com.hola.mysdk.util.NetWorkIsUserUtils;
import com.hola.mysdk.util.Utils;

public class HolaSDK implements HolaSDKInterface {
	
	public static final String TAG = HolaSDK.class.getSimpleName();
	private boolean isInit = false;
	private boolean isLoginSuccess = false;
	private Activity initActivity;
	private HolaInitCallBack holaInitCallBack;
	private Activity loginActivity;
	
	private static HolaSDK holasdk_instace;
	
	public static HolaSDK getInstace(){
		if(holasdk_instace == null){
			holasdk_instace = new HolaSDK();
		}
		return holasdk_instace;
	}

	@Override
	public void init(Activity paramActivity, HolaInitCallBack paramInitCallBack) {
		this.isInit = true;
		this.initActivity = paramActivity;
		this.holaInitCallBack = paramInitCallBack;
		HolaAppConfig.GAMEID = Utils.getIntMetaData(this.initActivity, "HI_GAMEID");
		HolaAppConfig.GAMEKEY = Utils.getStrMetaData(this.initActivity, "HI_GAMEKEY");
		HolaAppConfig.GAMESCREEN = Utils.getIntMetaData(this.initActivity, "GAMESCREEN");
		if(!NetWorkIsUserUtils.isNetworkAvailable(paramActivity)){
			Toast.makeText(paramActivity, "网络连接失败，请检查网络！", 0).show();
			this.holaInitCallBack.onFail();
			return;
		}
		this.holaInitCallBack.onSuccess();
		MyLog.d(TAG, "初始化成功！", MyLog.getLine());
		
	}

	@Override
	public void login(Activity paramActivity,HolaLoginCallBack paramLoginCallback) {
		
	}

	@Override
	public void pay(Activity paramActivity, HolaPayInfos parmPayInfos,
			HolaPayCallBack paramPayCallBack) {

	}

	@Override
	public void logout(Activity paramActivity,
			HolaLogoutCallBack paramLogoutCallBack) {

	}

	@Override
	public void exit(Activity paramActivity, HolaExitCallBack paramExitCallBack) {

	}

	@Override
	public void OnReStart(Activity paramActivity) {

	}

	@Override
	public void OnStart(Activity paramActivity) {

	}

	@Override
	public void OnResume(Activity paramActivity) {

	}

	@Override
	public void OnPause(Activity paramActivity) {

	}

	@Override
	public void OnStop(Activity paramActivity) {

	}

	@Override
	public void OnDestroy(Activity paramActivity) {

	}

	@Override
	public void OnNewIntent(Intent paramIntent) {

	}

}

package com.hola.mysdk.callback;

import com.hola.mysdk.bean.HolaPayInfos;

import android.app.Activity;
import android.content.Intent;

public abstract interface HolaSDKInterface {
	public abstract void init(Activity paramActivity, HolaInitCallBack paramInitCallBack);
	public abstract void login(Activity paramActivity, HolaLoginCallBack paramLoginCallback);
	public abstract void pay(Activity paramActivity, HolaPayInfos parmPayInfos, HolaPayCallBack paramPayCallBack);
	public abstract void logout(Activity paramActivity, HolaLogoutCallBack paramLogoutCallBack);
	public abstract void exit(Activity paramActivity, HolaExitCallBack paramExitCallBack);
	public abstract void OnReStart(Activity paramActivity);
	public abstract void OnStart(Activity paramActivity);
	public abstract void OnResume(Activity paramActivity);
	public abstract void OnPause(Activity paramActivity);
	public abstract void OnStop(Activity paramActivity);
	public abstract void OnDestroy(Activity paramActivity);
	public abstract void OnNewIntent(Intent paramIntent);
}

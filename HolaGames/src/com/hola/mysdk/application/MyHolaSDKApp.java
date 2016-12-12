package com.hola.mysdk.application;

import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.AVOSCloud;

import android.app.Application;

public class MyHolaSDKApp extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		AVOSCloud.initialize(this, "5hTLTjTFytuKxIYwVt7rcusg-gzGzoHsz", "QGt2p9v1oRyJWAPg4X7HzC5a");
	}

}

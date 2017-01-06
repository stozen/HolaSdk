package com.hola.mysdk.holagames;

import java.util.ArrayList;
import java.util.List;

import com.avos.avoscloud.LogUtil.log;
import com.hola.mysdk.changHeadIcon.ImageUtils;
import com.hola.mysdk.holaactivtys.WeafareActivity;
import com.hola.mysdk.holaactivtys.ActivityAdapter;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FloatViewUserInfo extends Activity {
	private List<WeafareActivity> activityList = new ArrayList<WeafareActivity>();
	private ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.float_view_user);
		initActivitys();
		initView();
		ActivityAdapter adapter = new ActivityAdapter(FloatViewUserInfo.this, R.layout.weafarw_item, activityList);
		ListView listView = (ListView) findViewById(R.id.float_welfare_list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
					WeafareActivity weafareActivity = activityList.get(position);
					if(weafareActivity.getName() == "Apple"){
						Toast.makeText(FloatViewUserInfo.this, weafareActivity.getName(), Toast.LENGTH_SHORT).show();
					}
			}
		});
	}
	
	private void initView(){
	    imageView = (ImageView) findViewById(R.id.float_user_icon);
		imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//ImageUtils.showImagePickDialog(FloatViewUserInfo.this); //更换头像
				Intent intent = new Intent();
				intent.setClass(FloatViewUserInfo.this, HolaUserInfoActivity.class);
				startActivity(intent);
			}
		});
		
		TextView changUser = (TextView) findViewById(R.id.float_change_user);
		changUser.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(FloatViewUserInfo.this, LoginActivity.class);
				startActivity(intent);
			}
		});
		
		TextView vipLevel = (TextView) findViewById(R.id.float_vip_level);
		vipLevel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(FloatViewUserInfo.this, "vip", Toast.LENGTH_SHORT).show();
			}
		});
		
		ImageView float_my_gifts = (ImageView) findViewById(R.id.float_my_gifts);
		float_my_gifts.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(FloatViewUserInfo.this, "礼包", Toast.LENGTH_SHORT).show();
			}
		});
		
		ImageView float_forum = (ImageView) findViewById(R.id.float_forum);
		float_forum.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(FloatViewUserInfo.this, "论坛", Toast.LENGTH_SHORT).show();
			}
		});
		
		ImageView float_customer_service = (ImageView) findViewById(R.id.float_customer_service);
		float_customer_service.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(FloatViewUserInfo.this, "客服", Toast.LENGTH_SHORT).show();
			}
		});
		
		ImageView float_activity = (ImageView) findViewById(R.id.float_activity);
		float_activity.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(FloatViewUserInfo.this, "活动", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		switch (requestCode) {
		case ImageUtils.REQUEST_CODE_FROM_ALBUM:{
			
			if(resultCode == RESULT_CANCELED){ //取消操作
				return;
			}
			
			Uri imageUri = data.getData();
			ImageUtils.copyImageUri(this, imageUri);
			ImageUtils.cropImageUri(this, ImageUtils.getCurrentUri(), 200, 200);
			break;
		}

		case ImageUtils.REQUEST_CODE_FROM_CAMERA:{
			
			if(resultCode == RESULT_CANCELED){ //取消操作
				ImageUtils.deleteImageUri(this, ImageUtils.getCurrentUri()); //删除uri
			}
			ImageUtils.cropImageUri(this, ImageUtils.getCurrentUri(), 200, 200);
			break;
		}
			
		case ImageUtils.REQUEST_CODE_CROP:{
			
			if(requestCode == RESULT_CANCELED){
				return;
			}
			
			Uri imageUri = ImageUtils.getCurrentUri();
			
			if(imageUri != null){
				imageView.setImageURI(imageUri);
			}
			break;
		}
		default:
			break;
		}
	}
	
	
	
	public void initActivitys(){
		WeafareActivity apple = new WeafareActivity("Apple",R.drawable.apple_pic);
		activityList.add(apple);
	        WeafareActivity Banana = new WeafareActivity("Banana",R.drawable.banana_pic);
	        activityList.add(Banana);
	        WeafareActivity Orange = new WeafareActivity("Orange",R.drawable.orange_pic);
	        activityList.add(Orange);
	        WeafareActivity Watermelon = new WeafareActivity("Watermelon",R.drawable.watermelon_pic);
	        activityList.add(Watermelon);
	        WeafareActivity Pear = new WeafareActivity("Pear",R.drawable.pear_pic);
	        activityList.add(Pear);
	        WeafareActivity Grape = new WeafareActivity("Grape",R.drawable.grape_pic);
	        activityList.add(Grape);
	        WeafareActivity Pineapple = new WeafareActivity("Pineapple",R.drawable.grape_pic);
	        activityList.add(Pineapple);
	        WeafareActivity Strawberry = new WeafareActivity("Strawberry",R.drawable.pineapple_pic);
	        activityList.add(Strawberry);
	        WeafareActivity apple1 = new WeafareActivity("Apple1",R.drawable.grape_pic);
	        activityList.add(apple1);
	        WeafareActivity apple2 = new WeafareActivity("Apple2",R.drawable.grape_pic);
	        activityList.add(apple2);
	        WeafareActivity apple3 = new WeafareActivity("Apple3",R.drawable.grape_pic);
	        activityList.add(apple3);
	        WeafareActivity apple4 = new WeafareActivity("Apple4",R.drawable.grape_pic);
	        activityList.add(apple4);
	        WeafareActivity apple5 = new WeafareActivity("Apple5",R.drawable.grape_pic);
	        activityList.add(apple5);
	        WeafareActivity apple6 = new WeafareActivity("Apple6",R.drawable.grape_pic);
	        activityList.add(apple6);
	}

}

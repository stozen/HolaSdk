package com.hola.mysdk.holagames;

import java.util.ArrayList;
import java.util.List;

import com.avos.avoscloud.LogUtil.log;
import com.hola.mysdk.holaactivtys.WeafareActivity;
import com.hola.mysdk.holaactivtys.ActivityAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class FloatViewUserInfo extends Activity {
	private List<WeafareActivity> activityList = new ArrayList<WeafareActivity>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.float_view_user);
		initActivitys();
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

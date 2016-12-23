package com.hola.mysdk.holaactivtys;

import java.util.List;

import com.hola.mysdk.holagames.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityAdapter extends ArrayAdapter<WeafareActivity> {
	private int resourceId;

	public ActivityAdapter(Context context, int resource,List<WeafareActivity> objects) {
		super(context, resource, objects);
		resourceId = resource;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		WeafareActivity weafareActivity = getItem(position);
		View view;
		ViewHolder viewHolder;
		//没有视图缓存，加载视图
		if(convertView == null){
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.weafareImage = (ImageView) view.findViewById(R.id.activty_image);
			viewHolder.weafareName = (TextView) view.findViewById(R.id.activity_name);
			view.setTag(viewHolder);
		}else{
			//有视图缓存，复用视图
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.weafareImage.setImageResource(weafareActivity.getImageId());
		viewHolder.weafareName.setText(weafareActivity.getName());
		
		return view;
	}
	
	class ViewHolder{
		ImageView weafareImage;
		TextView weafareName;
	}

}

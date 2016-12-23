package com.hola.mysdk.holaactivtys;



/**
 * 活动class
 * 
 * @author xiaowei
 * 2016-12-23 下午2:03:02
 * wlcaption@qq.com
 */
public class WeafareActivity {
	
	private int imageId;
	private String name;
	
	public WeafareActivity(String name, int imageId){
		this.name = name;
		this.imageId = imageId;
	}
	
	public int getImageId() {
		return imageId;
	}
	public void setImageId(int imageId) {
		this.imageId = imageId;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	

}

package com.hola.mysdk.util;

import java.lang.reflect.Field;

import android.content.Context;

public class ResourceUtil {
	
	public static int getDrawableResource(Context context, String name){
		return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
	}
	
	public static int getAttrResource(Context context, String name){
		return context.getResources().getIdentifier(name, "attr", context.getPackageName());
	}
	
	public static int getColorResource(Context context, String name){
		return context.getResources().getIdentifier(name, "color", context.getPackageName());
	}
	
	public static int getIdResource(Context context, String name){
		return context.getResources().getIdentifier(name, "id", context.getPackageName());
	}
	
	public static int getLayoutResource(Context context, String name){
		return context.getResources().getIdentifier(name, "layout", context.getPackageName());
	}
	
	public static int getStringResource(Context context, String name){
		return context.getResources().getIdentifier(name, "string", context.getPackageName());
	}
	
	public static int getArrayResource(Context context, String name){
		return context.getResources().getIdentifier(name, "array", context.getPackageName());
	}
	
	public static int getStyleResource(Context context, String name){
		return context.getResources().getIdentifier(name, "style", context.getPackageName());
	}
	
	public static int getDimenResource(Context context, String name){
		return context.getResources().getIdentifier(name, "dimen", context.getPackageName());
	}
	
	public static int getAnimResource(Context context, String name){
		return context.getResources().getIdentifier(name, "anim", context.getPackageName());
	}
	
	private static Object getResourceId(Context context, String name, String type){
		String className = context.getPackageName() + ".R";
		Class cls;
		try {
			cls = Class.forName(className);
			for(Class childClass : cls.getClasses()){
				String simple = childClass.getSimpleName();
				if(simple.equals(type)){
					for(Field field : childClass.getFields()){
						String fileName = field.getName();
						if(fileName.equals(name)){
							return field.get(null);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static int getStyleable(Context context, String name){
		return ((Integer) getResourceId(context, name, "styleable")).intValue();
	}
	
	public static int[] getStyleableArray(Context context, String name){
		return (int[]) getResourceId(context, name, "styleable");
	}
}

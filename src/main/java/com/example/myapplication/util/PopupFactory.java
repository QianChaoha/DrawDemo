/**
 *
 */
package com.example.myapplication.util;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

/** 
 * class name: PopupWindowFactory
 * class description: TODO
 * author: zp
 * create time: 2016-5-17 下午1:57:13
 * modify by:
 */
public class PopupFactory {
	
	private PopupFactory() {
		throw new UnsupportedOperationException("不支持实例化");
	}
	
	public static PopupWindow create(View view, int animationStyle, OnDismissListener l, Drawable d){
		PopupWindow popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, false);  
        popupWindow.setAnimationStyle(animationStyle);  
        // 使其聚集
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setOnDismissListener(l);
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setBackgroundDrawable(d);
        return popupWindow;
	}

	/**
	 * 简单的  PopupWindow
	 * @param view
	 * @return
     */
	public  static  PopupWindow   create(View view){
		PopupWindow popupWindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		return   popupWindow;
	}



	public  static  PopupWindow   create(View view,int width,int height,String string){
		PopupWindow popupWindow = new PopupWindow(view, width,height, true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		return   popupWindow;
	}
	public static PopupWindow create(View view, int animationStyle, Drawable d){
		return create(view, -1, null, d);
	}
	
	public static PopupWindow create(View view, int animationStyle, int drawableRes){
		return create(view, -1, null, view.getContext().getApplicationContext().getResources().getDrawable(drawableRes));
	}
	
//	public static PopupWindow create(View view, int animationStyle){
//		return create(view, animationStyle, R.color.gray_c);
//	}

	public static PopupMenu create(Activity activity, View anchor, int menuRes, OnMenuItemClickListener l){
		PopupMenu popupMenu = new PopupMenu(activity, anchor);
		popupMenu.inflate(menuRes);
		popupMenu.setOnMenuItemClickListener(l);
		return popupMenu;
	}


}

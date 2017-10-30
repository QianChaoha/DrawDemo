package com.example.myapplication.base;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public abstract class BaseHolder<Data>  {
	private View contentView;
	private Data data;
	public BaseHolder(){
		contentView=initView();
		contentView.setTag(this);
	}
	public  abstract View initView();
	public View getContentView() {
		return contentView;
	}
	public void setData(Data data){
		this.data=data;
		refreshView(data);
	}
	public abstract void refreshView(Data data);
}

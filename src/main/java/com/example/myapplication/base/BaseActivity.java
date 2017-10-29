package com.example.myapplication.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.myapplication.util.SharePreference;


public abstract class BaseActivity extends ActionBarActivity {
	/**
	 * 配置文件操作
	 */
	protected SharePreference spUtil;

	/**
	 * 布局ID
	 * 
	 * @return layoutID
	 */
	protected abstract int getLayoutId();

	/**
	 * 初始化布局
	 */
	protected abstract void initView();

	/**
	 * 初始化数据
	 */
	protected abstract void initData();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		if (getSupportActionBar()!=null){
			getSupportActionBar().hide();
		}
		super.onCreate(savedInstanceState);
		spUtil = new SharePreference(this, "config");
		setContentView(getLayoutId());
		initView();
		initData();
	}

	/**
	 * Toast提醒
	 */
	protected void showToast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}
	@Override
	protected void onStop() {
		super.onStop();
		closeKeyboard();
	}
	/**
	 * 关闭软键盘
	 */
	protected void closeKeyboard() {
		try {
			InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		} catch (Exception e) {
		}
	}
}

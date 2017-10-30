package com.example.myapplication;

import android.app.Application;

import com.example.myapplication.util.ExceptionManage;

/**
 * Created by Administrator on 2017/10/30.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ExceptionManage.startCatchException(this, "系统错误");

    }
}

package com.android.account.clear_bills;

import android.app.Application;

import cn.bmob.v3.Bmob;

/**
 * Created by Administrator on 2017/11/18.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Public_Data.context=getApplicationContext();
        //第一：默认初始化
        Bmob.initialize(this, "6631877ef016c0ec9f054db1b48b5519");
    }
}

package com.example.stockmanage;

/**
 * Created by Administrator on 2017-7-2.
 */

import android.app.Application;
import android.content.Context;

/**
 * 全局应用程序类
 */
public class AppContext extends Application {

    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=this.getApplicationContext();
        // 注册App异常崩溃处理器
        Thread.setDefaultUncaughtExceptionHandler(AppException.getAppExceptionHandler());
    }
}

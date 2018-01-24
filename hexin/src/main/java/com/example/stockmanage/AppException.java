package com.example.stockmanage;

import com.example.stockmanage.util.FileUtils;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * 应用程序异常类：用于捕获异常和提示错误信息
 */
public class AppException extends Exception implements UncaughtExceptionHandler {

    private static final String TAG = "AppException";


    /**
     * 系统默认的UncaughtException处理类
     */
    private UncaughtExceptionHandler mDefaultHandler;
    /**
     * 获取APP异常崩溃处理对象
     *
     * @param
     * @return
     */
    public static AppException getAppExceptionHandler() {
        return new AppException();
    }
    private AppException() {
        this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        FileUtils.writerError(ex.toString());
        if ( mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        }
    }




}

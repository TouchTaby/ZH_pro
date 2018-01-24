package com.example.stockmanage.util;

import android.util.Log;

/**
 * Created by Administrator on 2017-09-15.
 */
public class Logs {
    private  static final String TAG="StockManage";
    public  static  boolean isFlag=true;
    public static void Info(String tag,String log){
          if(isFlag) {
               FileUtils.writerLog(tag+"   "+log+"\r\n");
              Log.e(TAG,tag+"==========>"+log);
          }
    }

}

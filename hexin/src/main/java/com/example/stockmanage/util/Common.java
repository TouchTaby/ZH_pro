package com.example.stockmanage.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017-6-18.
 */

public class Common {
    private static String name="stockmanage";
    public static int ScanKey=139;
    private static String loginName="loginName";
    private static String loginPassword="loginPassword";

    public static String ip="183.238.0.26";
    public static int port=29898;

    //账套
    public static String getZT(Context mContext){
        return  getSharedPreferences_String(mContext,"zt","test");
    }
    public static void setZT(Context mContext,String zt){
        setSharedPreferences(mContext,"zt",zt);
    }

    public static String getIP(Context mContext){
        return  getSharedPreferences_String(mContext,"ip","http://113.77.24.201:8088");
    }
    public static void setTimeOut(Context mContext,int timeOut){
        setSharedPreferences(mContext,"timeOut",timeOut);
    }
    public static int getTimeOut(Context mContext){
       return  getSharedPreferences_Int(mContext,"timeOut",25);
    }

    public static void setIP(Context mContext,String ip){
        setSharedPreferences(mContext,"ip",ip);
    }
    public static int getPort(Context mContext){
        return  getSharedPreferences_Int(mContext,"port",29898);
    }
    public static void setPort(Context mContext,int port){
        setSharedPreferences(mContext,"port",port);
    }

    public static String getLoginName(Context mContext){
        return  getSharedPreferences_String(mContext,loginName,"");
    }
    public static void setLoginName(Context mContext,String name){
        setSharedPreferences(mContext,loginName,name);
    }
    public static String getLoginPassWord(Context mContext){
        return  getSharedPreferences_String(mContext,loginPassword,"");
    }
    public static void setLoginPassWord(Context mContext,String password){
        setSharedPreferences(mContext,loginPassword,password);
    }

    private static  String getSharedPreferences_String(Context mContext, String key, String def) {
        SharedPreferences preferences = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        String result = preferences.getString(key, def);
        return result;
    }

    private static int getSharedPreferences_Int(Context mContext, String key, int def) {
        SharedPreferences preferences = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        int result = preferences.getInt(key, def);
        return result;
    }

    private static void setSharedPreferences(Context mContext, String key, int value) {
        SharedPreferences preferences = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }
    private  static void setSharedPreferences(Context mContext, String key, String value) {
        SharedPreferences preferences = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
}

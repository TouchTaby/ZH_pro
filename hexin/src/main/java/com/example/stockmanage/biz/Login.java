package com.example.stockmanage.biz;

import android.content.Context;
import android.util.JsonReader;

import com.example.stockmanage.AppContext;
import com.example.stockmanage.net.HttpUtils;
import com.example.stockmanage.net.NetUtils;
import com.example.stockmanage.util.Common;
import com.example.stockmanage.util.ErrorManage;
import com.example.stockmanage.util.JsonUtils;
import com.example.stockmanage.util.Logs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017-09-17.
 */
public class Login {
    String TAG="Login";
    public String loginSystem(String name,String passWord,Context context) {
        if(name.isEmpty() || passWord.isEmpty()){
            return ErrorManage.Error_Empty;
        }
        if(!NetUtils.isNetworkAvailable(context)) {
            return ErrorManage.Error_netWork;
        }
        String result="";
        String path= HttpUtils.getURL();
        String zt=Common.getZT(AppContext.context);
        String parameter=HttpUtils.url_progid+"&method=PDACheckUserId&userid="+name+"&password="+passWord+"&groupid="+ zt;
        String[] rsultData=HttpUtils.HttpPostData(path, parameter.getBytes());//HttpGetData(path);
        if(Logs.isFlag) {
            Logs.Info(TAG, "登录 ==============>\r\npath=" + path + "\r\n" + parameter);
        }
        if(rsultData[0].equals(ErrorManage.Error_Success)){
           String data=rsultData[1];//"{\"Issucceed\":\"1\",\"FailMsg\":\"登陆成功\"}";
            if(!data.isEmpty()){
                result= JsonUtils.loginJSON(data);
            }else{
                result="服务器无数据返回!";
            }
        }else{
            result= rsultData[1];
        }
        return  result;
    }


}

package com.example.stockmanage.biz;

import com.example.stockmanage.AppContext;
import com.example.stockmanage.entity.UserInfoEntity;
import com.example.stockmanage.entity.Warehouse_Entity;
import com.example.stockmanage.net.HttpUtils;
import com.example.stockmanage.net.NetUtils;
import com.example.stockmanage.util.ErrorManage;
import com.example.stockmanage.util.JsonUtils;
import com.example.stockmanage.util.Logs;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017-10-30.
 */

public class BaseInfo {
String TAG="TAG";
    private static BaseInfo instance = null;
    public static BaseInfo getInstance() {
        if (instance == null) {
            instance = new BaseInfo();
        }
        return instance;
    }

    public String[] PDADownWhs(ArrayList<Warehouse_Entity> data1) {
        if(!NetUtils.isNetworkAvailable(AppContext.context)) {
            return null;
        }
        String[] result=new String[2];
        result[0]= ErrorManage.Error_failure;

        String path= HttpUtils.getURL();
        String parameter=HttpUtils.url_progid+"&method=PDADownWhs";
        if(Logs.isFlag) {
            Logs.Info(TAG, "获取仓库数据==============>\r\npath=" + path + "\r\n" + parameter);
        }
        String[] rsultData=HttpUtils.HttpPostData(path, parameter.getBytes());
        if(rsultData[0].equals(ErrorManage.Error_Success)){
            String data=rsultData[1];
            //如果数据为空返回服务器无数据返回
            if(!data.isEmpty()){
                //解析json数据
                String str=JsonUtils.downWhs(data, data1);
                if(str.equals(ErrorManage.Error_Success)) {
                    //解析json数据成功，自动回传listData
                    result[0]=ErrorManage.Error_Success;
                }else {
                    result[1]=str;//解析失败返回错误信息
                }
            }else{
                result[1]="服务器无数据返回";
            }
        }else{
            result[1]= rsultData[1];
        }
        return  result;
    }

    public String[] PDADownUser(ArrayList<UserInfoEntity> data1) {
        if(!NetUtils.isNetworkAvailable(AppContext.context)) {
            return null;
        }
        String[] result=new String[2];
        result[0]= ErrorManage.Error_failure;

        String path= HttpUtils.getURL();
        String parameter=HttpUtils.url_progid+"&method=PDADownPer";
        if(Logs.isFlag) {
            Logs.Info(TAG, "获取人员信息==============>\r\npath=" + path + "\r\n" + parameter);
        }
        String[] rsultData=HttpUtils.HttpPostData(path, parameter.getBytes());
        if(rsultData[0].equals(ErrorManage.Error_Success)){
            String data=rsultData[1];
            //如果数据为空返回服务器无数据返回
            if(!data.isEmpty()){
                //解析json数据
                String str=JsonUtils.downUserInfo(data, data1);
                if(str.equals(ErrorManage.Error_Success)) {
                    //解析json数据成功，自动回传listData
                    result[0]=ErrorManage.Error_Success;
                }else {
                    result[1]=str;//解析失败返回错误信息
                }
            }else{
                result[1]="服务器无数据返回";
            }
        }else{
            result[1]= rsultData[1];
        }
        return  result;
    }
}

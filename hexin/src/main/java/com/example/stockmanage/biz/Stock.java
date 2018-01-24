package com.example.stockmanage.biz;

import com.example.stockmanage.AppContext;
import com.example.stockmanage.entity.DownInventoryBook_Entity;
import com.example.stockmanage.entity.InventoryBook_Entity;
import com.example.stockmanage.net.HttpUtils;
import com.example.stockmanage.net.NetUtils;
import com.example.stockmanage.util.ErrorManage;
import com.example.stockmanage.util.JsonUtils;
import com.example.stockmanage.util.Logs;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017-10-31.
 */

public class Stock {
     String TAG="Stock";
    private static Stock instance = null;
    public static Stock getInstance() {
        if (instance == null) {
            instance = new Stock();
        }
        return instance;
    }
//盘点清侧
    public String[] PDADownStock(ArrayList<InventoryBook_Entity> data1) {
        if(!NetUtils.isNetworkAvailable(AppContext.context)) {
            return null;
        }
        String[] result=new String[2];
        result[0]= ErrorManage.Error_failure;

        String path= HttpUtils.getURL();
        String parameter=HttpUtils.url_progid+"&method=PDADownChkBo";
        if(Logs.isFlag) {
            Logs.Info(TAG, "获取盘点清册==============>\r\npath=" + path + "\r\n" + parameter);
        }
        String[] rsultData=HttpUtils.HttpPostData(path, parameter.getBytes());
        if(rsultData[0].equals(ErrorManage.Error_Success)){
            String data=rsultData[1];
            //如果数据为空返回服务器无数据返回
            if(!data.isEmpty()){
                //解析json数据
                String str= JsonUtils.downPDADownChkBo(data,data1 );
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
    //获取盘点单号数据
    public String[] PDADownChkInve(int chkst,String blno,ArrayList<DownInventoryBook_Entity> data1,ArrayList<DownInventoryBook_Entity> data12) {
        if(!NetUtils.isNetworkAvailable(AppContext.context)) {
            return null;
        }
        String[] result=new String[2];
        result[0]= ErrorManage.Error_failure;

        String path= HttpUtils.getURL();
        String parameter=HttpUtils.url_progid+"&method=PDADownChkInve&chkst="+chkst+"&blno="+blno;
        if(Logs.isFlag) {
            Logs.Info(TAG, "获取盘点单（根据盘点单号）==============>\r\npath=" + path + "\r\n" + parameter);
        }
        String[] rsultData=HttpUtils.HttpPostData(path, parameter.getBytes());
        if(rsultData[0].equals(ErrorManage.Error_Success)){
            String data=rsultData[1];
            //如果数据为空返回服务器无数据返回
            if(!data.isEmpty()){
                //解析json数据
                String str= JsonUtils.downInventoryBook(data, data1,data12);
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

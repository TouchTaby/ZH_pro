package com.example.stockmanage.biz;

import com.example.stockmanage.AppContext;
import com.example.stockmanage.entity.Batch_Entity;
import com.example.stockmanage.entity.ProBar_Entity;
import com.example.stockmanage.net.HttpUtils;
import com.example.stockmanage.net.NetUtils;
import com.example.stockmanage.util.ErrorManage;
import com.example.stockmanage.util.JsonUtils;
import com.example.stockmanage.util.Logs;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017-11-1.
 */

//批号
public class Batch {
    private static Batch instance = null;
    String TAG="Batch";
    public static Batch getInstance() {
        if (instance == null) {
            instance = new Batch();
        }
        return instance;
    }
    // 更加产品ID 查询数据
    public String[] PDADowGetBatNo(String prodid,ArrayList<Batch_Entity> entities) {
        if(!NetUtils.isNetworkAvailable(AppContext.context)) {
            return null;
        }
        String[] result=new String[2];
        result[0]= ErrorManage.Error_failure;

        String path= HttpUtils.getURL();
        String parameter=HttpUtils.url_progid+"&method=PDADowGetBatNo&prodid="+prodid;
        if(Logs.isFlag) {
            Logs.Info(TAG, "获取批号 ==============>\r\npath=" + path + "\r\n" + parameter);
        }
        String[] rsultData=HttpUtils.HttpPostData(path, parameter.getBytes());
        if(rsultData[0]==ErrorManage.Error_Success){
            String data=rsultData[1];
            //如果数据为空返回服务器无数据返回
            if(!data.isEmpty()){
                //解析json数据
                String str= JsonUtils.downBatchListJSON(data, entities);
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

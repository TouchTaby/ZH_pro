package com.example.stockmanage.biz;

import com.example.stockmanage.AppContext;


import com.example.stockmanage.entity.Batch_Entity;
import com.example.stockmanage.entity.PDADownShpm_Entity;
import com.example.stockmanage.net.HttpUtils;
import com.example.stockmanage.net.NetUtils;
import com.example.stockmanage.util.ErrorManage;
import com.example.stockmanage.util.JsonUtils;
import com.example.stockmanage.util.Logs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017-10-22.
 */
public class OutGoing {
    String TAG="OutGoing";
    private static OutGoing instance = null;
    public static OutGoing getInstance() {
        if (instance == null) {
            instance = new OutGoing();
        }
        return instance;
    }
    public String[] downPDADownShpmApl(ArrayList<PDADownShpm_Entity> pdaDownShpm_OutListData,ArrayList<PDADownShpm_Entity> pdaPro_OutListData) {
        if(!NetUtils.isNetworkAvailable(AppContext.context)) {
            return null;
        }
        String[] result=new String[2];
        result[0]= ErrorManage.Error_failure;

        String path= HttpUtils.getURL();
        String parameter=HttpUtils.url_progid+"&method=PDADownShpmApl";
       if(Logs.isFlag) {
           Logs.Info(TAG, "查询销售出货申请单明细==============>\r\npath=" + path + "\r\n" + parameter);
       }
        String[] rsultData=HttpUtils.HttpPostData(path, parameter.getBytes());
        if(rsultData[0].equals(ErrorManage.Error_Success)){
            String data=rsultData[1];
            //如果数据为空返回服务器无数据返回
            if(!data.isEmpty()){
                //解析json数据
                String str= JsonUtils.downOutGoingListJSON(data, pdaDownShpm_OutListData,pdaPro_OutListData);
                if (str.equals(ErrorManage.Error_Success)) {
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

    /*
   *上传收货数据
   */
    public String[]  updateOutGoing(HashMap<String,String> mapT0,ArrayList<HashMap<String,String>> listT1) {
        if(!NetUtils.isNetworkAvailable(AppContext.context)) {
            return null;
        }

        JSONArray jsonArrayTab0=new JSONArray();
        JSONArray jsonArrayTab1=new JSONArray();
        JSONObject strTab0 = new JSONObject();
        for (Map.Entry<String, String> entry : mapT0.entrySet()) {
            try {
                strTab0.put(entry.getKey(), entry.getValue());
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for(int k=0;k<listT1.size();k++){
            HashMap<String,String> map=listT1.get(k);
            JSONObject strTab1 = new JSONObject();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                try {
                    if(entry.getKey().equals(PDADownShpm_Entity._SQty)){
                        strTab1.put(entry.getKey(), Double.parseDouble(entry.getValue()));
                    }else if(entry.getKey().equals(PDADownShpm_Entity._RowCd)) {
                        strTab1.put(entry.getKey(), Integer.parseInt(entry.getValue()));
                    }else if(entry.getKey().equals(Batch_Entity._StkBiza)) {
                        strTab1.put(entry.getKey(), Integer.parseInt(entry.getValue()));
                    }else{
                        strTab1.put(entry.getKey(), entry.getValue());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            jsonArrayTab1.put(strTab1);
        }
        jsonArrayTab0.put(strTab0);

        String[] result=new String[2];
        result[0]=ErrorManage.Error_failure;
        String path= HttpUtils.getURL();
        String parameter=HttpUtils.url_progid+"&method=PDAAutoConfirm&conprogid=salShpmApl&table0="+jsonArrayTab0.toString()+"&table1="+jsonArrayTab1.toString();
        if(Logs.isFlag) {
            Logs.Info(TAG, "上传销售出货 ==============>\r\npath=" + path + "\r\n" + parameter);
        }
        String[] rsultData=HttpUtils.HttpPostData(path, parameter.getBytes());
        if(rsultData[0]==ErrorManage.Error_Success){
            String data=rsultData[1];
            //如果数据为空返回服务器无数据返回
            if(!data.isEmpty()){
                //解析json数据
                String str=JsonUtils.loginJSON(data);
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

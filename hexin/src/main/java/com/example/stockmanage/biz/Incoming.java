package com.example.stockmanage.biz;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.stockmanage.AppContext;
import com.example.stockmanage.db.CreateDateBase;
import com.example.stockmanage.db.DBHelper;
import com.example.stockmanage.db.DatabaseManager;
import com.example.stockmanage.entity.IncomingEntity;
import com.example.stockmanage.entity.Incoming_purRecvOrdDet_Entity;
import com.example.stockmanage.entity.Incoming_purRecvOrd_Entity;
import com.example.stockmanage.entity.ProBar_Entity;
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
 * Created by Administrator on 2017-10-15.
 */
public class Incoming {
    String TAG="Incoming";
    private static Incoming instance = null;
    public static Incoming getInstance() {
        if (instance == null) {
            instance = new Incoming();
        }
        return instance;
    }

       /*
        * 获取采购进货申请表清单
        * @param outListData: 回传采购申请单
        * @return [0]返回状态值： ErrorManage.Error_Success表示成功，其他表失败
        *         [1]返回具体的失败信息
        */
    public String[] pdpDownRecOrdList(ArrayList<Incoming_purRecvOrd_Entity> data1, ArrayList<Incoming_purRecvOrdDet_Entity> data2) {
           if(!NetUtils.isNetworkAvailable(AppContext.context)) {
               return null;
           }
           String[] result=new String[2];
           result[0]=ErrorManage.Error_failure;
         //  String path=HttpUtils.urlRootPath2+"&method=PDADownRecOrdList";
           String[] rsultData=null;//HttpUtils.HttpGetData(path);//获取下载到的数据
           if(rsultData[0].equals(ErrorManage.Error_Success)){
               String data=rsultData[1];
               //如果数据为空返回服务器无数据返回
               if(!data.isEmpty()){
                   //解析json数据
                   String str=JsonUtils.downRecOrdListJSON(data, data1,data2);
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
        /*
         * 获取所有采购进货申请单明细
         * @param outListData: 回传采购申请单明细
         * @return [0]返回状态值： ErrorManage.Error_Success表示成功，其他表失败
         *         [1]返回具体的失败信息
         */
    public String[] pdpDownRecOrdList2(ArrayList<Incoming_purRecvOrd_Entity> purRecvOrdDet_OutListData, ArrayList<Incoming_purRecvOrdDet_Entity> Incoming_purRecvOrdDet_outListData) {
        if(!NetUtils.isNetworkAvailable(AppContext.context)) {
            return null;
        }
        String[] result=new String[2];
        result[0]=ErrorManage.Error_failure;

        String path= HttpUtils.getURL();
        String parameter=HttpUtils.url_progid+"&method=PDADownRecOrd";
        if(Logs.isFlag) {
            Logs.Info(TAG, "查询采购进货申请单明细 ==============>\r\npath=" + path + "\r\n" + parameter);
        }
        String[] rsultData=HttpUtils.HttpPostData(path, parameter.getBytes());
        if(rsultData[0].equals(ErrorManage.Error_Success)){
            String data=rsultData[1];
            //如果数据为空返回服务器无数据返回
            if(!data.isEmpty()){
                //解析json数据
                String str=JsonUtils.downRecOrdListJSON(data, purRecvOrdDet_OutListData,Incoming_purRecvOrdDet_outListData);
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

      /*
       *上传收货数据
       */
    public String[] updateIncoming(HashMap<String,String> mapT0,ArrayList<HashMap<String,String>> listT1) {
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
                    if(entry.getKey().equals(IncomingEntity._RecvSQty)){
                        strTab1.put(entry.getKey(), Double.parseDouble(entry.getValue()));
                    }else {
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
        String parameter=HttpUtils.url_progid+"&method=PDAAutoConfirm&conprogid=purRecvOrd&table0="+jsonArrayTab0.toString()+"&table1="+jsonArrayTab1.toString();
        if(Logs.isFlag) {
            Logs.Info(TAG, "上传采购进货 ==============>\r\npath=" + path + "\r\n" + parameter);
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
    // 更加产品ID 查询数据

    public String[] ProBar_EntityList(String prodid,ArrayList<ProBar_Entity> proBar_Entity_OutListData) {
        if(!NetUtils.isNetworkAvailable(AppContext.context)) {
            return null;
        }
        String[] result=new String[2];
        result[0]=ErrorManage.Error_failure;

        String path= HttpUtils.getURL();
        String parameter=HttpUtils.url_progid+"&method=PDAGetProBar&prodid="+prodid;
        if(Logs.isFlag) {
            Logs.Info(TAG, "采购进货条码扫描 ==============>\r\npath=" + path + "\r\n" + parameter);
        }
        String[] rsultData=HttpUtils.HttpPostData(path, parameter.getBytes());
        if(rsultData[0]==ErrorManage.Error_Success){
            String data=rsultData[1];
            //如果数据为空返回服务器无数据返回
            if(!data.isEmpty()){
                //解析json数据
                String str=JsonUtils.downProBardListJSON(data, proBar_Entity_OutListData);
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

    //----------------------插入查询采购进货申请单明细表一-------------------------------------
    public boolean insertIncoming_purRecvOrd(Incoming_purRecvOrd_Entity pdaDownRecOrd){
        SQLiteDatabase db =   DBHelper.getInstance().getWritableDatabase();
        if(db.isOpen()){
            ContentValues values=new ContentValues();
            if(!pdaDownRecOrd.getBlNo().isEmpty())
                values.put("BlNo",pdaDownRecOrd.getBlNo());
            if(!pdaDownRecOrd.getSupId().isEmpty())
                values.put("SupId",pdaDownRecOrd.getSupId());
            if(!pdaDownRecOrd.getSupNm().isEmpty())
                values.put("SupNm",pdaDownRecOrd.getSupNm ());
            if(!pdaDownRecOrd.getTypeId().isEmpty())
                values.put("TypeId",pdaDownRecOrd.getTypeId ());
            if(DatabaseManager.getInstance().insert(db, CreateDateBase.TAB_Incoming_purRecvOrd, values)>0){
                db.close();
                return true;
            }
        }
        return  false;
    }
    //----------------------插入查询采购进货申请单明细表一-------------------------------------
    public boolean insertIncoming_purRecvOrdDet(Incoming_purRecvOrdDet_Entity pdaDownRecOrd){
        SQLiteDatabase db =   DBHelper.getInstance().getWritableDatabase();
        if(db.isOpen()){
            //   db.execSQL("update person set name = ? where _id = ?;", new Object[]{name, id});
            ContentValues values=new ContentValues();
            if(!pdaDownRecOrd.getBlNo().isEmpty())
                values.put("BlNo",pdaDownRecOrd.getBlNo());
            if(!pdaDownRecOrd.getProdId().isEmpty())
                values.put("ProdId",pdaDownRecOrd.getProdId());
            if(!pdaDownRecOrd.getProdNm().isEmpty())
                values.put("ProdNm",pdaDownRecOrd.getProdNm());
            if(!pdaDownRecOrd.getWhsId().isEmpty())
                values.put("WhsId",pdaDownRecOrd.getWhsId());
            if(!pdaDownRecOrd.getWhsNm().isEmpty())
                values.put("WhsNm",pdaDownRecOrd.getWhsNm());
            values.put("RowCd",pdaDownRecOrd.getRowCd());
            values.put("WaCfmSQty",pdaDownRecOrd.getWaCfmSQty());
            if(DatabaseManager.getInstance().insert(db, CreateDateBase.TAB_Incoming_purRecvOrdDet, values)>0){
                return true;
            }
        }
        return  false;
    }

    public int deleteIncoming_purRecvOrd(){
        SQLiteDatabase db =   DBHelper.getInstance().getWritableDatabase();
        if(db.isOpen()){
            return  DatabaseManager.getInstance().delete(CreateDateBase.TAB_Incoming_purRecvOrd);
        }
        return  -1;
    }
    public int deleteIncoming_purRecvOrdDet(){
        SQLiteDatabase db =   DBHelper.getInstance().getWritableDatabase();
        if(db.isOpen()){
            return  DatabaseManager.getInstance().delete(CreateDateBase.TAB_Incoming_purRecvOrdDet);
        }
        return  -1;
    }

    //根据清单查询产品
    public  ArrayList<Incoming_purRecvOrdDet_Entity> selectIncoming_RecvOrdDetALL() {
        SQLiteDatabase db =   DBHelper.getInstance().getWritableDatabase();
        ArrayList<Incoming_purRecvOrdDet_Entity> prodIdList=new ArrayList<Incoming_purRecvOrdDet_Entity>();
        try {
            if(db.isOpen()) {
                String table = CreateDateBase.TAB_Incoming_purRecvOrdDet;
                String[] columns = new String[]{Incoming_purRecvOrdDet_Entity._BlNo,
                        Incoming_purRecvOrdDet_Entity._ProdId,Incoming_purRecvOrdDet_Entity._ProdNm,
                        Incoming_purRecvOrdDet_Entity._RowCd,Incoming_purRecvOrdDet_Entity._WaCfmSQty,
                        Incoming_purRecvOrdDet_Entity._WhsId,Incoming_purRecvOrdDet_Entity._WhsNm};//返回那一列，如果参数是null,则返回所有列
                String selection =null;// Incoming_purRecvOrdDet_Entity._BlNo+"= '" + BlNo + "' ";  //查询条件
                String[] selectionArgs =null;
                String groupBy = null; //分组
                String having = null;
                String orderBy = null;//排序
                Cursor c = DatabaseManager.getInstance().query(db, table, columns, selection, selectionArgs, groupBy, having, orderBy);
                if (c != null) {
                    while (c.moveToNext()) {
                        Incoming_purRecvOrdDet_Entity incoming_purRecvOrdDet_entity=new Incoming_purRecvOrdDet_Entity();
                        incoming_purRecvOrdDet_entity.setBlNo(c.getString(0));
                        incoming_purRecvOrdDet_entity.setProdId(c.getString(1));
                        incoming_purRecvOrdDet_entity.setProdNm(c.getString(2));
                        incoming_purRecvOrdDet_entity.setRowCd(c.getInt(3));
                        incoming_purRecvOrdDet_entity.setWaCfmSQty(c.getDouble(4));
                        incoming_purRecvOrdDet_entity.setWhsId(c.getString(5));
                        incoming_purRecvOrdDet_entity.setWhsNm(c.getString(6));
                        prodIdList.add(incoming_purRecvOrdDet_entity);
                    }
                }
            }
        } catch (Exception ex) {
        } finally {
            db.close();
        }
        return  prodIdList;
    }
    //查询清单
    public  ArrayList<Incoming_purRecvOrd_Entity> selectIncoming_RecvOrdALL() {
        SQLiteDatabase db =   DBHelper.getInstance().getWritableDatabase();
        ArrayList<Incoming_purRecvOrd_Entity>  BINoList=new ArrayList<Incoming_purRecvOrd_Entity>();
        try {
            if(db.isOpen()) {
                String table = CreateDateBase.TAB_Incoming_purRecvOrd;
                String[] columns = new String[]{Incoming_purRecvOrd_Entity._BlNo,Incoming_purRecvOrd_Entity._SupId,Incoming_purRecvOrd_Entity._SupNm,Incoming_purRecvOrd_Entity._TypeId};//返回那一列，如果参数是null,则返回所有列
                String selection = null; //查询条件
                String[] selectionArgs =null;
                String groupBy = null; //分组
                String having = null;
                String orderBy = null;//排序
                Cursor c = DatabaseManager.getInstance().query(db, table, columns, selection, selectionArgs, groupBy, having, orderBy);
                if (c != null) {
                    while (c.moveToNext()) {
                        Incoming_purRecvOrd_Entity incoming_purRecvOrd_entity=new Incoming_purRecvOrd_Entity();
                        incoming_purRecvOrd_entity.setBlNo(c.getString(0));
                        incoming_purRecvOrd_entity.setSupId(c.getString(1));
                        incoming_purRecvOrd_entity.setSupNm(c.getString(2));
                        incoming_purRecvOrd_entity.setTypeId(c.getString(3));
                        BINoList.add(incoming_purRecvOrd_entity);
                    }
                }
            }
        } catch (Exception ex) {
        } finally {
            db.close();
        }
        return  BINoList;
    }










/*
    public boolean insertPDADownShpmApl(PDADownShpm_Entity pdaDownRecOrd){
        SQLiteDatabase db =   DBHelper.getInstance().getWritableDatabase();
        if(db.isOpen()){
            //   db.execSQL("update person set name = ? where _id = ?;", new Object[]{name, id});
            ContentValues values=new ContentValues();
            if(!pdaDownRecOrd.getBlNo().isEmpty())
                values.put("BlNo",pdaDownRecOrd.getBlNo());
            if(!pdaDownRecOrd.getCustId().isEmpty())
                values.put("CustId",pdaDownRecOrd.getCustId());
            if(!pdaDownRecOrd.getCustNm().isEmpty())
                values.put("CustNm",pdaDownRecOrd.getCustNm ());
            if(!pdaDownRecOrd.getTypeId().isEmpty())
                values.put("TypeId",pdaDownRecOrd.getTypeId());
            if(!pdaDownRecOrd.getProdId().isEmpty())
                values.put("ProdId",pdaDownRecOrd.getProdId());
            if(!pdaDownRecOrd.getProdNm().isEmpty())
                values.put("ProdNm",pdaDownRecOrd.getProdNm());
            if(!pdaDownRecOrd.getWhsId().isEmpty())
                values.put("WhsId",pdaDownRecOrd.getWhsId());
            if(!pdaDownRecOrd.getWhsNm().isEmpty())
                values.put("WhsNm",pdaDownRecOrd.getWhsNm());
            values.put("RowCd",pdaDownRecOrd.getRowCd());
            values.put("WaCfmSQty",pdaDownRecOrd.getWaCfmSQty());
            if(DatabaseManager.getInstance().insert(db, CreateDateBase.TAB_Incoming_purRecvOrdDet, values)>0){
                return true;
            }
        }
        return  false;
    }
    */
}

package com.example.stockmanage.util;

import android.util.JsonReader;
import android.util.JsonToken;
import android.util.JsonWriter;

import com.example.stockmanage.entity.Batch_Entity;
import com.example.stockmanage.entity.DownInventoryBook_Entity;
import com.example.stockmanage.entity.Incoming_purRecvOrdDet_Entity;
import com.example.stockmanage.entity.Incoming_purRecvOrd_Entity;
import com.example.stockmanage.entity.InventoryBook_Entity;
import com.example.stockmanage.entity.PDADownShpm_Entity;
import com.example.stockmanage.entity.ProBar_Entity;
import com.example.stockmanage.entity.UserInfoEntity;
import com.example.stockmanage.entity.Warehouse_Entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


/**
 * Created by Administrator on 2017-10-15.
 */
public class JsonUtils {

    public  static String loginJSON(String data){
        try {
            JSONObject jsonObject=new JSONObject(data);
            if(jsonObject!=null) {
                if (jsonObject.getString("type").equals("suc")) {
                    String listData = jsonObject.getString("data");
                    if(!listData.isEmpty()) {
                        JSONObject jsonData=new JSONObject(listData);
                        int successCode = jsonData.getInt("Issucceed");
                        String msg = jsonData.getString("FailMsg");
                        if (successCode == 1) {
                            return ErrorManage.Error_Success;
                        } else {
                            return msg;

                        }
                    }else{
                            return "服务器无数据返回!";
                        }
            }else {
                    return "JSON数据状态为失败!";
                }
            }else{
                return "解析JSON数据格式错误!";
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return "解析JSON数据格式异常!";
        }
    }

    public  static String downRecOrdListJSON(String data, ArrayList<Incoming_purRecvOrd_Entity> purRecvOrd_OutListData, ArrayList<Incoming_purRecvOrdDet_Entity> purRecvOrdDet_outListData){
        try {
           JSONObject jsonObject=new JSONObject(data);
            if(jsonObject!=null) {
                if (jsonObject.getString("type").equals("suc")) {
                    String listData = jsonObject.getString("data");
                    if(!listData.isEmpty()) {
                        JSONArray jsonArray = new JSONArray(listData);
                        for(int k=0;k<jsonArray.length();k++) {
                            JSONObject jsonData1 = jsonArray.getJSONObject(k);
                            if (jsonData1.get("TableName").equals("purRecvOrd")) {
                                String strSource = jsonData1.getString("Source");
                                if (!strSource.isEmpty()) {
                                    JSONArray purRecvOrdArray1 = new JSONArray(strSource);
                                    for (int i = 0; i < purRecvOrdArray1.length(); i++) {
                                        JSONObject jsonObject1 = purRecvOrdArray1.getJSONObject(i);
                                        Incoming_purRecvOrd_Entity incoming_purRecvOrd_entity = new Incoming_purRecvOrd_Entity();
                                        incoming_purRecvOrd_entity.setBlNo(jsonObject1.getString(Incoming_purRecvOrd_Entity._BlNo));
                                        incoming_purRecvOrd_entity.setSupId(jsonObject1.getString(Incoming_purRecvOrd_Entity._SupId));
                                        incoming_purRecvOrd_entity.setSupNm(jsonObject1.getString(Incoming_purRecvOrd_Entity._SupNm));
                                        incoming_purRecvOrd_entity.setTypeId(jsonObject1.getString(Incoming_purRecvOrd_Entity._TypeId));
                                        purRecvOrd_OutListData.add(incoming_purRecvOrd_entity);
                                    }
                                }
                            } else if (jsonData1.get("TableName").equals("purRecvOrdDet")) {
                                String strSource = jsonData1.getString("Source");
                                if (!strSource.isEmpty()) {
                                    JSONArray purRecvOrdArray1 = new JSONArray(strSource);
                                    for (int i = 0; i < purRecvOrdArray1.length(); i++) {
                                        JSONObject jsonObject1 = purRecvOrdArray1.getJSONObject(i);
                                        Incoming_purRecvOrdDet_Entity incoming_purRecvOrd_entity = new Incoming_purRecvOrdDet_Entity();
                                        incoming_purRecvOrd_entity.setBlNo(jsonObject1.getString(Incoming_purRecvOrdDet_Entity._BlNo));
                                        incoming_purRecvOrd_entity.setWhsNm(jsonObject1.getString(Incoming_purRecvOrdDet_Entity._WhsNm));
                                        incoming_purRecvOrd_entity.setWhsId(jsonObject1.getString(Incoming_purRecvOrdDet_Entity._WhsId));
                                        incoming_purRecvOrd_entity.setProdId(jsonObject1.getString(Incoming_purRecvOrdDet_Entity._ProdId));
                                        incoming_purRecvOrd_entity.setProdNm(jsonObject1.getString(Incoming_purRecvOrdDet_Entity._ProdNm));
                                        incoming_purRecvOrd_entity.setRowCd(jsonObject1.getInt(Incoming_purRecvOrdDet_Entity._RowCd));
                                        incoming_purRecvOrd_entity.setWaCfmSQty(jsonObject1.getDouble(Incoming_purRecvOrdDet_Entity._WaCfmSQty));
                                        purRecvOrdDet_outListData.add(incoming_purRecvOrd_entity);
                                    }
                                }
                            }
                        }
                        if(purRecvOrd_OutListData.size()>0 && purRecvOrdDet_outListData.size()>0 ){
                              return  ErrorManage.Error_Success;
                        }else{
                            return "服务器无数据返回!";
                        }

                    }else{
                        return "服务器无数据返回!";
                    }
                }else{
                    return "JSON数据状态为失败!";
                }
            }
            return "解析JSON数据格式错误3!";
        } catch (JSONException e) {
            e.printStackTrace();
            return "解析JSON数据格式异常!";
        }
    }
    public  static String downProBardListJSON(String data,ArrayList<ProBar_Entity> proBar_Entity_OutListData){
        try {
            JSONObject jsonObject=new JSONObject(data);
            if(jsonObject!=null) {
                if (jsonObject.getString("type").equals("suc")) {
                    String listData = jsonObject.getString("data");
                    if(!listData.isEmpty()) {
                        JSONObject jsonData1 =new  JSONObject(listData);
                        if (jsonData1.get("TableName").equals("salShpmApl")) {
                            String strSource = jsonData1.getString("Source");
                            if (!strSource.isEmpty()) {
                                JSONArray purRecvOrdArray1 = new JSONArray(strSource);
                                for (int i = 0; i < purRecvOrdArray1.length(); i++) {
                                    JSONObject jsonObject1 = purRecvOrdArray1.getJSONObject(i);
                                    ProBar_Entity proBar_Entity = new ProBar_Entity();
                                  //  proBar_Entity.setProdid(jsonObject1.getString(ProBar_Entity._prodid));
                                    proBar_Entity.setCU_dengji(jsonObject1.getString(ProBar_Entity._CU_dengji));
                                    proBar_Entity.setCU_mm(jsonObject1.getString(ProBar_Entity._CU_mm));
                                    proBar_Entity.setProdNm(jsonObject1.getString(ProBar_Entity._ProdNm));
                                    proBar_Entity.setProdSpec(jsonObject1.getString(ProBar_Entity._ProdSpec));
                                    proBar_Entity_OutListData.add(proBar_Entity);
                                }
                            }
                        }
                        if(proBar_Entity_OutListData.size()>0){
                            return  ErrorManage.Error_Success;
                        }else{
                            return "服务器无数据返回!";
                        }

                    }else{
                        return "服务器无数据返回!";
                    }
                }else{
                    return "JSON数据状态为失败!";
                }
            }
            return "解析JSON数据格式错误3!";
        } catch (JSONException e) {
            e.printStackTrace();
            return "解析JSON数据格式异常!";
        }
    }
    public  static String downOutGoingListJSON(String data,ArrayList<PDADownShpm_Entity> pdaDownShpm_OutListData,ArrayList<PDADownShpm_Entity> Pro_OutListData){
        try {
            JSONObject jsonObject=new JSONObject(data);
            if(jsonObject!=null) {
                if (jsonObject.getString("type").equals("suc")) {
                    String listData = jsonObject.getString("data");
                    if(!listData.isEmpty()) {
                        JSONArray jsonArray = new JSONArray(listData);

                        for(int k=0;k<jsonArray.length();k++) {

                            JSONObject jsonData1 = jsonArray.getJSONObject(k);
                            if (jsonData1.get("TableName").equals("salShpmApl")) {
                                String strSource = jsonData1.getString("Source");
                                if (!strSource.isEmpty()) {
                                    JSONArray purRecvOrdArray1 = new JSONArray(strSource);

                                    for (int i = 0; i < purRecvOrdArray1.length(); i++) {
                                        JSONObject jsonObject1 = purRecvOrdArray1.getJSONObject(i);
                                        String   BlNo=jsonObject1.getString(PDADownShpm_Entity._BlNo);
                                        String   CustId=jsonObject1.getString(PDADownShpm_Entity._CustId);
                                        String   CustNm=jsonObject1.getString(PDADownShpm_Entity._CustNm);
                                        String   TypeId=jsonObject1.getString(PDADownShpm_Entity._TypeId);
                                        PDADownShpm_Entity pdaDownShpm_entity=new PDADownShpm_Entity();
                                        pdaDownShpm_entity.setBlNo(BlNo);
                                        pdaDownShpm_entity.setCustId(CustId);
                                        pdaDownShpm_entity.setCustNm(CustNm);
                                        pdaDownShpm_entity.setTypeId(TypeId);
                                        pdaDownShpm_OutListData.add(pdaDownShpm_entity);
                                    }
                                }
                            } else if (jsonData1.get("TableName").equals("salShpmDet")) {
                                String strSource = jsonData1.getString("Source");
                                if (!strSource.isEmpty()) {
                                    JSONArray purRecvOrdArray1 = new JSONArray(strSource);
                                    for (int i = 0; i < purRecvOrdArray1.length(); i++) {
                                        JSONObject jsonObject1 = purRecvOrdArray1.getJSONObject(i);
                                        PDADownShpm_Entity pdaDownShpm_entity=new PDADownShpm_Entity();
                                        pdaDownShpm_entity.setBlNo(jsonObject1.getString(Incoming_purRecvOrdDet_Entity._BlNo));
                                        pdaDownShpm_entity.setRowCd(Integer.parseInt(jsonObject1.getString(Incoming_purRecvOrdDet_Entity._RowCd)));
                                        pdaDownShpm_entity.setProdId(jsonObject1.getString(Incoming_purRecvOrdDet_Entity._ProdId));
                                        pdaDownShpm_entity.setProdNm(jsonObject1.getString(Incoming_purRecvOrdDet_Entity._ProdNm));
                                        pdaDownShpm_entity.setWhsId(jsonObject1.getString(Incoming_purRecvOrdDet_Entity._WhsId));
                                        pdaDownShpm_entity.setWhsNm(jsonObject1.getString(Incoming_purRecvOrdDet_Entity._WhsNm));
                                        pdaDownShpm_entity.setWaCfmSQty(Double.parseDouble(jsonObject1.getString(Incoming_purRecvOrdDet_Entity._WaCfmSQty)));
                                        Pro_OutListData.add(pdaDownShpm_entity);
                                    }
                                }
                            }
                        }
                        if(pdaDownShpm_OutListData.size()>0 && Pro_OutListData.size()>0){
                            return  ErrorManage.Error_Success;
                        }else{
                            return "服务器无数据返回!";
                        }

                    }else{
                        return "服务器无数据返回!";
                    }
                }else{
                    return "JSON数据状态为失败!";
                }
            }
            return "解析JSON数据格式错误3!";
        } catch (JSONException e) {
            e.printStackTrace();
            return "解析JSON数据格式异常!";
        }
    }

    public static String downWhs(String data,ArrayList<Warehouse_Entity> data1){
        try {
            JSONObject jsonObject=new JSONObject(data);
            if(jsonObject!=null) {
                if (jsonObject.getString("type").equals("suc")) {
                    String listData = jsonObject.getString("data");
                    if(!listData.isEmpty()) {
                        JSONObject jsonData1 =new  JSONObject(listData);
                        if (jsonData1.get("TableName").equals("comWhs")) {
                            String strSource = jsonData1.getString("Source");
                            if (!strSource.isEmpty()) {
                                JSONArray Whs = new JSONArray(strSource);
                                for (int i = 0; i < Whs.length(); i++) {
                                    JSONObject jsonObject1 = Whs.getJSONObject(i);
                                    Warehouse_Entity proBar_Entity = new Warehouse_Entity();
                                    proBar_Entity.setWhsId(jsonObject1.getString(Warehouse_Entity._WhsId));
                                    proBar_Entity.setWhsNm(jsonObject1.getString(Warehouse_Entity._WhsNm));
                                    data1.add(proBar_Entity);
                                }
                            }
                        }
                        if(data1.size()>0){
                            return  ErrorManage.Error_Success;
                        }else{
                            return "服务器无数据返回!";
                        }

                    }else{
                        return "服务器无数据返回!";
                    }
                }else{
                    return "JSON数据状态为失败!";
                }
            }
            return "解析JSON数据格式错误3!";
        } catch (JSONException e) {
            e.printStackTrace();
            return "解析JSON数据格式异常!";
        }
    }
    public static String downUserInfo(String data,ArrayList<UserInfoEntity> data1) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            if (jsonObject != null) {
                if (jsonObject.getString("type").equals("suc")) {
                    String listData = jsonObject.getString("data");
                    if (!listData.isEmpty()) {
                        JSONObject jsonData1 = new JSONObject(listData);
                        if (jsonData1.get("TableName").equals("comPerDt")) {
                            String strSource = jsonData1.getString("Source");
                            if (!strSource.isEmpty()) {
                                JSONArray Whs = new JSONArray(strSource);
                                for (int i = 0; i < Whs.length(); i++) {
                                    JSONObject jsonObject1 = Whs.getJSONObject(i);
                                    UserInfoEntity proBar_Entity = new UserInfoEntity();
                                    proBar_Entity.setPerId(jsonObject1.getString(UserInfoEntity._PerId));
                                    proBar_Entity.setPerNm(jsonObject1.getString(UserInfoEntity._PerNm));
                                    data1.add(proBar_Entity);
                                }
                            }
                        }
                        if (data1.size() > 0) {
                            return ErrorManage.Error_Success;
                        } else {
                            return "服务器无数据返回!";
                        }

                    } else {
                        return "服务器无数据返回!";
                    }
                } else {
                    return "JSON数据状态为失败!";
                }
            }
            return "解析JSON数据格式错误3!";
        } catch (JSONException e) {
            e.printStackTrace();
            return "解析JSON数据格式异常!";
        }
    }



    public  static String downBatchListJSON(String data,ArrayList<Batch_Entity> data1){
        try {
            JSONObject jsonObject=new JSONObject(data);
            if(jsonObject!=null) {
                if (jsonObject.getString("type").equals("suc")) {
                    String listData = jsonObject.getString("data");
                    if(!listData.isEmpty()) {
                        JSONObject jsonData1 =new  JSONObject(listData);
                        if (jsonData1.get("TableName").equals("ComBat")) {
                            String strSource = jsonData1.getString("Source");
                            if (!strSource.isEmpty()) {
                                JSONArray Whs = new JSONArray(strSource);
                                for (int i = 0; i < Whs.length(); i++) {
                                    JSONObject jsonObject1 = Whs.getJSONObject(i);
                                    Batch_Entity entity = new Batch_Entity();
                                    entity.setWhsNm(jsonObject1.getString(Batch_Entity._WhsNm));
                                    entity.setWhsId(jsonObject1.getString(Batch_Entity._WhsId));
                                    entity.setBatNo(jsonObject1.getString(Batch_Entity._BatNo));
                                    entity.setProdId(jsonObject1.getString(Batch_Entity._ProdId));
                                    entity.setProdNm(jsonObject1.getString(Batch_Entity._ProdNm));
                                    entity.setStkBiza(jsonObject1.getInt(Batch_Entity._StkBiza));
                                    entity.setStkStId(jsonObject1.getString(Batch_Entity._StkStId));
                                    entity.setQty(Double.parseDouble(jsonObject1.getString(Batch_Entity._Qty)));
                                    data1.add(entity);
                                }
                            }
                        }
                        if(data1.size()>0){
                            return  ErrorManage.Error_Success;
                        }else{
                            return "服务器无数据返回!";
                        }

                    }else{
                        return "服务器无数据返回!";
                    }
                }else{
                    return "JSON数据状态为失败!";
                }
            }
            return "解析JSON数据格式错误3!";
        } catch (JSONException e) {
            e.printStackTrace();
            return "解析JSON数据格式异常!";
        }
    }
    public  static String downInventoryBook(String data,ArrayList<DownInventoryBook_Entity> data1,ArrayList<DownInventoryBook_Entity> data2){
        try {
            JSONObject jsonObject=new JSONObject(data);
            if(jsonObject!=null) {
                if (jsonObject.getString("type").equals("suc")) {
                    String listData = jsonObject.getString("data");
                    if(!listData.isEmpty()) {
                        JSONArray jsonArray = new JSONArray(listData);

                        for(int k=0;k<jsonArray.length();k++) {

                            JSONObject jsonData1 = jsonArray.getJSONObject(k);
                            if (jsonData1.get("TableName").equals("stkChkInve")) {
                                String strSource = jsonData1.getString("Source");
                                if (!strSource.isEmpty()) {
                                    JSONArray purRecvOrdArray1 = new JSONArray(strSource);
                                    for (int i = 0; i < purRecvOrdArray1.length(); i++) {
                                        JSONObject jsonObject1 = purRecvOrdArray1.getJSONObject(i);
                                        String   _TypeId=jsonObject1.getString(DownInventoryBook_Entity._TypeId);
                                        String   _TyNm=jsonObject1.getString(DownInventoryBook_Entity._TyNm);

                                        DownInventoryBook_Entity inventoryBook_entity=new DownInventoryBook_Entity();
                                        inventoryBook_entity.setTyNm(_TyNm);
                                        inventoryBook_entity.setTypeId(_TypeId);
                                        data1.add(inventoryBook_entity);
                                    }
                                }
                            } else if (jsonData1.get("TableName").equals("stkChkInveDetail")) {
                                String strSource = jsonData1.getString("Source");
                                if (!strSource.isEmpty()) {

                                    JSONArray purRecvOrdArray1 = new JSONArray(strSource);
                                    for (int i = 0; i < purRecvOrdArray1.length(); i++) {
                                        JSONObject jsonObject1 = purRecvOrdArray1.getJSONObject(i);
                                        DownInventoryBook_Entity entity=new DownInventoryBook_Entity();
                                        entity.setBlNo(jsonObject1.getString(DownInventoryBook_Entity._BlNo));
                                        entity.setRowCd(Integer.parseInt(jsonObject1.getString(DownInventoryBook_Entity._RowCd)));
                                        entity.setCardNo(jsonObject1.getString(DownInventoryBook_Entity._CardNo));
                                        entity.setProdId(jsonObject1.getString(DownInventoryBook_Entity._ProdId));
                                        entity.setProdNm(jsonObject1.getString(DownInventoryBook_Entity._ProdNm));
                                        entity.setProdSpec(jsonObject1.getString(DownInventoryBook_Entity._ProdSpec));
                                        entity.setCU_dengji(jsonObject1.getString(DownInventoryBook_Entity._CU_dengji));
                                        entity.setCU_mm(jsonObject1.getString(DownInventoryBook_Entity._CU_mm));
                                        entity.setBoQty(Double.parseDouble(jsonObject1.getString(DownInventoryBook_Entity._BoQty)));
                                        entity.setWhsId(jsonObject1.getString(DownInventoryBook_Entity._WhsId));
                                        entity.setWhsNm(jsonObject1.getString(DownInventoryBook_Entity._WhsNm));
                                        entity.setQty(Double.parseDouble(jsonObject1.getString(DownInventoryBook_Entity._Qty)));
                                        data2.add(entity);
                                    }
                                }
                            }
                        }
                        if(data1.size()>0 && data2.size()>0){
                            return  ErrorManage.Error_Success;
                        }else{
                            return "服务器无数据返回!";
                        }

                    }else{
                        return "服务器无数据返回!";
                    }
                }else{
                    return "JSON数据状态为失败!";
                }
            }
            return "解析JSON数据格式错误3!";
        } catch (JSONException e) {
            e.printStackTrace();
            return "解析JSON数据格式异常!";
        }
    }


    public static String downPDADownChkBo(String data,ArrayList<InventoryBook_Entity> data1){
        try {
            JSONObject jsonObject=new JSONObject(data);
            if(jsonObject!=null) {
                if (jsonObject.getString("type").equals("suc")) {
                    String listData = jsonObject.getString("data");
                    if(!listData.isEmpty()) {
                        JSONObject jsonData1 =new  JSONObject(listData);
                        if (jsonData1.get("TableName").equals("stkChkBo")) {
                            String strSource = jsonData1.getString("Source");
                            if (!strSource.isEmpty()) {
                                JSONArray Whs = new JSONArray(strSource);
                                for (int i = 0; i < Whs.length(); i++) {
                                    JSONObject jsonObject1 = Whs.getJSONObject(i);
                                    InventoryBook_Entity proBar_Entity = new InventoryBook_Entity();
                                    proBar_Entity.setBoNm(jsonObject1.getString(InventoryBook_Entity._BoNm));
                                    proBar_Entity.setBoNo(jsonObject1.getString(InventoryBook_Entity._BoNo));
                                    proBar_Entity.setChkct(jsonObject1.getInt(InventoryBook_Entity._Chkct));
                                    data1.add(proBar_Entity);
                                }
                            }
                        }
                        if(data1.size()>0){
                            return  ErrorManage.Error_Success;
                        }else{
                            return "服务器无数据返回!";
                        }

                    }else{
                        return "服务器无数据返回!";
                    }
                }else{
                    return "JSON数据状态为失败!";
                }
            }
            return "解析JSON数据格式错误3!";
        } catch (JSONException e) {
            e.printStackTrace();
            return "解析JSON数据格式异常!";
        }
    }

    private JSONObject readSingleJSON(JsonReader jsonReader)throws Exception{
        JSONObject jsonObject=new JSONObject();
        jsonReader.beginObject();
        JsonToken token;
        do{
            String name=jsonReader.nextName();
            if("name".equals(name)){
                jsonObject.put("name",jsonReader.nextString());
            }else if("age".equals(name)){
                jsonObject.put("age",jsonReader.nextString());
            }else if("lon".equals(name)){
                jsonObject.put("lon",jsonReader.nextString());
            }
            token=jsonReader.peek();
        }while(token!=null&&!token.equals(JsonToken.END_OBJECT));
        jsonReader.endObject();
        return jsonObject;
    }
    public JSONArray readPeopleFromInputStream(InputStream inputStream){
        InputStreamReader reader=new InputStreamReader(inputStream);
        JsonReader jsonReader=new JsonReader(reader);
        JSONArray jsonArray=new JSONArray();
        try {
            jsonReader.beginArray();
            while(jsonReader.hasNext()){
                JSONObject jsonObject=readSingleJSON(jsonReader);
                jsonArray.put(jsonObject);
            }
            jsonReader.endArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArray;
    }
    public void writePeopleJSON(JSONArray jsonArray,OutputStream outputStream) throws Exception {
        OutputStreamWriter write=new OutputStreamWriter(outputStream);
        JsonWriter jsonWrite=new JsonWriter(write);
        int arrayLength=jsonArray.length();
        jsonWrite.beginArray();
        for (int i = 0; i < arrayLength; i++) {
            JSONObject jsonObject= (JSONObject) jsonArray.get(i);
            jsonWrite.beginObject();
            jsonWrite.name("name").value(jsonObject.getString("name"));
            jsonWrite.name("age").value(jsonObject.getString("age"));
            jsonWrite.name("lon").value(jsonObject.getString("lon"));
            jsonWrite.endObject();
        }
        jsonWrite.endArray();
        jsonWrite.close();
    }


/*****************************************************查询采购进货申请单明细************************************************
{type:"suc",data:[
{"TableIndex":0,"TableName":"purRecvOrd",
"Source":[
{"BlNo":"201709070001","SupId":"0015","SupNm":"东莞市福森纸业有限公司","TypeId":"01"},
{"BlNo":"201709140001","SupId":"0015","SupNm":"东莞市福森纸业有限公司","TypeId":"01"},
{"BlNo":"201709270001","SupId":"0003","SupNm":"东莞玖龙纸业有限公司","TypeId":"01"},
{"BlNo":"201709270002","SupId":"0005","SupNm":"东莞市泰昌纸业有限公司","TypeId":"01"},
{"BlNo":"201709270003","SupId":"0001","SupNm":"广东理文造纸有限公司","TypeId":"01"}]},

{"TableIndex":1,"TableName":"purRecvOrdDet",
"Source":[
{"RowCd":1,"BlNo":"201709070001","ProdId":"HX000701","ProdNm":"100克仿牛","WhsId":"","WhsNm":"","WaCfmSQty":27.853000000},
{"RowCd":1,"BlNo":"201709270001","ProdId":"0101200B030","ProdNm":"玖龙牛卡200","WhsId":"","WhsNm":"","WaCfmSQty":1.491000000},
{"RowCd":2,"BlNo":"201709270001","ProdId":"0101250B017","ProdNm":"玖龙牛卡250","WhsId":"","WhsNm":"","WaCfmSQty":3.226000000},
{"RowCd":3,"BlNo":"201709270001","ProdId":"0101250B019","ProdNm":"玖龙牛卡250","WhsId":"","WhsNm":"","WaCfmSQty":1.100000000},
{"RowCd":4,"BlNo":"201709270001","ProdId":"0101250B031","ProdNm":"玖龙牛卡250","WhsId":"","WhsNm":"","WaCfmSQty":1.339000000},
{"RowCd":5,"BlNo":"201709270001","ProdId":"0101250B035","ProdNm":"玖龙牛卡250","WhsId":"","WhsNm":"","WaCfmSQty":1.340000000},
{"RowCd":1,"BlNo":"201709270002","ProdId":"0501100A017","ProdNm":"HXTQ牛卡100","WhsId":"","WhsNm":"","WaCfmSQty":0.918000000},
{"RowCd":2,"BlNo":"201709270002","ProdId":"0501100A031","ProdNm":"HXTQ牛卡100","WhsId":"","WhsNm":"","WaCfmSQty":0.707000000},
{"RowCd":3,"BlNo":"201709270002","ProdId":"0501100A021","ProdNm":"HXTQ牛卡100","WhsId":"","WhsNm":"","WaCfmSQty":0.248000000},
{"RowCd":4,"BlNo":"201709270002","ProdId":"0501100A029","ProdNm":"HXTQ牛卡100","WhsId":"","WhsNm":"","WaCfmSQty":0.256000000},
{"RowCd":5,"BlNo":"201709270002","ProdId":"0501100A025","ProdNm":"HXTQ牛卡100","WhsId":"","WhsNm":"","WaCfmSQty":1.043000000},
{"RowCd":6,"BlNo":"201709270002","ProdId":"0501100A027","ProdNm":"HXTQ牛卡100","WhsId":"","WhsNm":"","WaCfmSQty":1.981000000},
{"RowCd":7,"BlNo":"201709270002","ProdId":"0501100A033","ProdNm":"HXTQ牛卡100","WhsId":"","WhsNm":"","WaCfmSQty":0.673000000},
{"RowCd":8,"BlNo":"201709270002","ProdId":"0501100A023","ProdNm":"HXTQ牛卡100","WhsId":"","WhsNm":"","WaCfmSQty":0.625000000},
{"RowCd":9,"BlNo":"201709270002","ProdId":"0501100A019","ProdNm":"HXTQ牛卡100","WhsId":"","WhsNm":"","WaCfmSQty":0.145000000},
{"RowCd":10,"BlNo":"201709270002","ProdId":"0501100A074","ProdNm":"HXTQ牛卡100","WhsId":"","WhsNm":"","WaCfmSQty":0.117000000}]}]}
    */
}

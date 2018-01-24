package com.example.stockmanage.entity;

/**
 * Created by Administrator on 2017-10-26.
 */

//仓库Info
public class Warehouse_Entity {
    public static  String _WhsId="WhsId";//仓库ID
    public static  String _WhsNm="WhsNm";//仓库名称
    private  String WhsId;
    private  String WhsNm;

    public String getWhsId() {
        return WhsId;
    }

    public void setWhsId(String whsId) {
        WhsId = whsId;
    }

    public String getWhsNm() {
        return WhsNm;
    }

    public void setWhsNm(String whsNm) {
        WhsNm = whsNm;
    }
}

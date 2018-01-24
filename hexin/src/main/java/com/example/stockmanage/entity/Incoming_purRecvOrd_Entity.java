package com.example.stockmanage.entity;

/**
 * Created by Administrator on 2017-10-18.
 */

public class Incoming_purRecvOrd_Entity {
    //单据编号
    public static String _BlNo="BlNo";
    //供应商代码
    public  static String _SupId="SupId";
    //供应商名称
    public  static String _SupNm="SupNm";
    //单据类型
    public  static String _TypeId="TypeId";

    //单据编号
    private String BlNo;
    //供应商代码
    private String SupId;
    //供应商名称
    private String SupNm;
    //单据类型
    private String TypeId;

    public String getBlNo() {
        return BlNo;
    }

    public void setBlNo(String blNo) {
        BlNo = blNo;
    }

    public String getSupId() {
        return SupId;
    }

    public void setSupId(String supId) {
        SupId = supId;
    }

    public String getSupNm() {
        return SupNm;
    }

    public void setSupNm(String supNm) {
        SupNm = supNm;
    }

    public String getTypeId() {
        return TypeId;
    }

    public void setTypeId(String typeId) {
        TypeId = typeId;
    }
}

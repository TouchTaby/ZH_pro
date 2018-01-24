package com.example.stockmanage.entity;

/**
 * Created by Administrator on 2017-10-18.
 */

public class Incoming_purRecvOrdDet_Entity {
    //标识号
    public static String _RowCd="RowCd";
    //产品代码
    public static String _ProdId="ProdId";
    //产品名称
    public static String _ProdNm="ProdNm";
    //仓库代码
    public static String _WhsId="WhsId";
    //仓库名称
    public static String _WhsNm="WhsNm";
    //待确认数量
    public static String _WaCfmSQty="WaCfmSQty";
    //单据编号
    public static String _BlNo="BlNo";
    //单据编号
    private String BlNo;
    //标识号
    private int RowCd;
    //产品代码
    private String ProdId;
    //产品名称
    private String ProdNm;
    //仓库代码
    private String WhsId;
    //仓库名称
    private String WhsNm;
    //待确认数量
    private double WaCfmSQty;


    public String getBlNo() {
        return BlNo;
    }

    public void setBlNo(String blNo) {
        BlNo = blNo;
    }

    public int getRowCd() {
        return RowCd;
    }

    public void setRowCd(int rowCd) {
        RowCd = rowCd;
    }

    public String getProdNm() {
        return ProdNm;
    }

    public void setProdNm(String prodNm) {
        ProdNm = prodNm;
    }

    public String getProdId() {
        return ProdId;
    }

    public void setProdId(String prodId) {
        ProdId = prodId;
    }

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

    public double getWaCfmSQty() {
        return WaCfmSQty;
    }

    public void setWaCfmSQty(double waCfmSQty) {
        WaCfmSQty = waCfmSQty;
    }
}

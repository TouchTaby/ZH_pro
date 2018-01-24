package com.example.stockmanage.entity;

/**
 * Created by Administrator on 2017-11-1.
 */

public class Batch_Entity {

    public  static String _ProdId="ProdId";//产品代码
    public  static String _ProdNm="ProdNm";//产品名称
    public  static  String _WhsId="WhsId";//仓库代码
    public  static String _WhsNm="WhsNm";//仓库名称
    public  static  String   _BatNo="BatNo";//批号
    public  static  String   _StkStId="StkStId";//库存状态编号
    public  static   String   _StkBiza="StkBiza";//存货属性
    public  static   String   _Qty="Qty";//库存数量


    private   String ProdId;//产品代码
    private   String ProdNm;//产品名称
    private    String WhsId;//仓库代码
    private   String WhsNm;//仓库名称
    private    String   BatNo;//批号
    private    String   StkStId;//库存状态编号
    private     int StkBiza;//存货属性
    private double Qty;//库存数量
    public double getQty() {
        return Qty;
    }

    public void setQty(double qty) {
        Qty = qty;
    }




    public String getProdId() {
        return ProdId;
    }

    public void setProdId(String prodId) {
        ProdId = prodId;
    }

    public String getProdNm() {
        return ProdNm;
    }

    public void setProdNm(String prodNm) {
        ProdNm = prodNm;
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

    public String getBatNo() {
        return BatNo;
    }

    public void setBatNo(String batNo) {
        BatNo = batNo;
    }

    public String getStkStId() {
        return StkStId;
    }

    public void setStkStId(String stkStId) {
        StkStId = stkStId;
    }

    public int getStkBiza() {
        return StkBiza;
    }

    public void setStkBiza(int stkBiza) {
        StkBiza = stkBiza;
    }
}

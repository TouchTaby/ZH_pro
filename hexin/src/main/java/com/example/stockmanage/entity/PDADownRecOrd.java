package com.example.stockmanage.entity;

/**
 * Created by Administrator on 2017-10-16.
 */

//进货清单实体类
public class PDADownRecOrd {

    //单据编号
    public static String _BlNo="BlNo";
    //供应商代码
    public  static String _SupId="SupId";
    //供应商名称
    public  static String _SupNm="SupNm";
    //单据类型
    public  static String _TypeId="TypeId";
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
    private String BlNo;
    //供应商代码
    private String SupId;
    //供应商名称
    private String SupNm;
    //单据类型
    private String TypeId;
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
    private int WaCfmSQty;

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

    public int getWaCfmSQty() {
        return WaCfmSQty;
    }

    public void setWaCfmSQty(int waCfmSQty) {
        WaCfmSQty = waCfmSQty;
    }
}

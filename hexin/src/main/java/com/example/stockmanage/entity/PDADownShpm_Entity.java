package com.example.stockmanage.entity;

/**
 * Created by Administrator on 2017-10-17.
 */

public class PDADownShpm_Entity {

    //单据编号
    public static  String _BlNo="BlNo";
    //客户代码
    public static String _CustId="CustId";
    //客户名称
    public static String _CustNm="CustNm";
    //单据类型
    public static String _TypeId="TypeId";
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
    //出货数量
    public static String _SQty="SQty";
    //待确认数量
    public static String _WaCfmSQty="WaCfmSQty";
   //规格
    public static String _ProdSpec="ProdSpec";
    //等级
    public static String _CU_dengji="CU_dengji";
    //宽幅
    public static String _CU_mm="CU_mm";

    public static String  _BatNo="BatNo";//批号
    private    String   BatNo;//批号

    public String getBatNo() {
        return BatNo;
    }

    public void setBatNo(String batNo) {
        BatNo = batNo;
    }

    //单据编号
    private String BlNo;
    //客户代码
    private String CustId;
    //客户名称
    private String CustNm;
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
    private double WaCfmSQty;



    private double SQty;
    //规格
    public  String ProdSpec;
    //等级
    public  String CU_dengji;
    //宽幅
    public  String CU_mm;
    public String getProdSpec() {
        return ProdSpec;
    }

    public void setProdSpec(String prodSpec) {
        ProdSpec = prodSpec;
    }

    public String getCU_dengji() {
        return CU_dengji;
    }

    public void setCU_dengji(String CU_dengji) {
        this.CU_dengji = CU_dengji;
    }

    public String getCU_mm() {
        return CU_mm;
    }

    public void setCU_mm(String CU_mm) {
        this.CU_mm = CU_mm;
    }


    public double getSQty() {
        return SQty;
    }

    public void setSQty(double SQty) {
        this.SQty = SQty;
    }
    public String getBlNo() {
        return BlNo;
    }

    public void setBlNo(String blNo) {
        BlNo = blNo;
    }

    public String getCustId() {
        return CustId;
    }

    public void setCustId(String custId) {
        CustId = custId;
    }

    public String getCustNm() {
        return CustNm;
    }

    public void setCustNm(String custNm) {
        CustNm = custNm;
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

    public double getWaCfmSQty() {
        return WaCfmSQty;
    }

    public void setWaCfmSQty(double waCfmSQty) {
        WaCfmSQty = waCfmSQty;
    }


}

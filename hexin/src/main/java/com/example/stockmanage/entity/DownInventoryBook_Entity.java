package com.example.stockmanage.entity;

/**
 * Created by Administrator on 2017-10-26.
 */

public class DownInventoryBook_Entity {

    public static String _TypeId="TypeId";//单据类型代码
    public static String _TyNm="TyNm";//类型名称
    public static String _BlNo="BlNo";//单据编号
    public static String _RowCd="RowCd";//标识号
    public static  String _CardNo="CardNo";//盘点卡号
    public static   String _ProdId="ProdId";//产品代码
    public static  String _ProdNm="ProdNm";//产品名称
    public static  String _ProdSpec="ProdSpec";//等级
    public static  String _CU_dengji="CU_dengji";//规格
    public static  String _CU_mm="CU_mm";//幅宽
    public static  String _BoQty="BoQty";//帐面数量
    public static String _WhsId="WhsId";//仓库代码;
    public static  String _WhsNm="WhsNm";//仓库名称
    public static   String _Qty="Qty";//盘点数量

    String BlNo;//单据编号
    int RowCd;//标识号
    String CardNo;//盘点卡号
    String ProdId;//产品代码
    String ProdNm;//产品名称
    String ProdSpec;//等级
    String CU_dengji;//规格
    String CU_mm;//幅宽
    double BoQty;//帐面数量
    String WhsId;//仓库代码;
    String WhsNm;//仓库名称
    double Qty;//盘点数量
     String TypeId;//单据类型代码
     String TyNm;//类型名称

    public String getTyNm() {
        return TyNm;
    }

    public void setTyNm(String tyNm) {
        TyNm = tyNm;
    }

    public String getTypeId() {
        return TypeId;
    }

    public void setTypeId(String typeId) {
        TypeId = typeId;
    }

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

    public String getCardNo() {
        return CardNo;
    }

    public void setCardNo(String cardNo) {
        CardNo = cardNo;
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

    public double getBoQty() {
        return BoQty;
    }

    public void setBoQty(double boQty) {
        BoQty = boQty;
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

    public double getQty() {
        return Qty;
    }

    public void setQty(double qty) {
        Qty = qty;
    }
}

package com.example.stockmanage.entity;

/**
 * Created by Administrator on 2017-10-16.
 */

//进货清单实体类
public class IncomingEntity {
    //单据编号
    public static String _BlNo="BlNo";
    //供应商代码
    public static String _SupId="SupId";//SupId";
    //供应商名称
    public static String _SupNm="SupNm";//"SupNm";
    //仓库代码
    public static String _WhsId="WhsId";
    //仓库名称
    public static String _WhsNm="WhsNm";
    //单据类型
    public static String _TypeId="TypeId";
    //标识号
    public static String  _RowCd="RowCd";
    //产品代码
    public static String _ProdId="ProdId";
    //产品名称
    public static String _ProdNm="ProdNm";
    //收货数量
    public static String _RecvSQty="RecvSQty";
    //规格
    public static String _ProdSpec="ProdSpec";
    //等级
    public static String _CU_dengji="CU_dengji";
    //宽幅
    public static String _CU_mm="CU_mm";


     //单据编号
    private String BlNo;
    //供应商代码
    private String SupId;
    //供应商名称
    private String SupNm;
    //仓库代码
    private String WhsId;
    //仓库名称
    private String WhsNm;
    //单据类型
    private String TypeId;
    //标识号
    private int RowCd;
    //产品代码
    private String ProdId;
    //产品名称
    private String ProdNm;
    //收货数量
    private double RecvSQty;
    //规格
    private String ProdSpec;
    //等级
    private String CU_dengji;
    //宽幅
    private String CU_mm;

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

    public double getRecvSQty() {
        return RecvSQty;
    }

    public void setRecvSQty(double recvSQty) {
        RecvSQty = recvSQty;
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
}

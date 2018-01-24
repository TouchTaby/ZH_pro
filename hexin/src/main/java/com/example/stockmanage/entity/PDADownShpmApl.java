package com.example.stockmanage.entity;

/**
 * Created by Administrator on 2017-10-17.
 */

public class PDADownShpmApl {

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
    private int WaCfmSQty;

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

    public int getWaCfmSQty() {
        return WaCfmSQty;
    }

    public void setWaCfmSQty(int waCfmSQty) {
        WaCfmSQty = waCfmSQty;
    }


}

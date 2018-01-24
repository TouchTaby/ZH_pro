package com.example.stockmanage.entity;

/**
 * Created by Administrator on 2017-10-21.
 */

public class ProBar_Entity {
    public  static String _prodid="prodid";//产品ID
    public  static String _ProdNm="ProdNm";//产品名称
    public  static String _ProdSpec="ProdSpec";//规格
    public  static String _CU_dengji="CU_dengji";//等级
    public  static String _CU_mm="CU_mm";//宽幅

    public   String prodid;
    public   String ProdNm;
    public   String ProdSpec;
    public   String CU_dengji;
    public   String CU_mm;

    public String getProdid() {
        return prodid;
    }

    public void setProdid(String prodid) {
        this.prodid = prodid;
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
}

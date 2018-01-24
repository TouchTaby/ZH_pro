package com.example.stockmanage.entity;

/**
 * Created by Administrator on 2017-10-26.
 */

public class UserInfoEntity {
    public static String _PerId="PerId";
    public  static  String _PerNm="PerNm";
    String PerId;
    String PerNm;

    public String getPerId() {
        return PerId;
    }

    public void setPerId(String perId) {
        PerId = perId;
    }

    public String getPerNm() {
        return PerNm;
    }

    public void setPerNm(String perNm) {
        PerNm = perNm;
    }
}

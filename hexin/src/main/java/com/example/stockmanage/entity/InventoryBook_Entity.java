package com.example.stockmanage.entity;

/**
 * Created by Administrator on 2017-10-26.
 */
//盘点清册
public class InventoryBook_Entity {

    public static String _BoNo="BoNo";//编号
    public static String _BoNm="BoNm";//盘点清册
    public static String _Chkct="Chkct";//盘点次数

    private String BoNo;//编号
    private String BoNm;//盘点清册
    private int Chkct;//盘点次数   tinyint（0:1，1:2，2:3，3:4，4:5）指：值为0时，盘点次数为1，以此类推，该字段为pickvalue

    public String getBoNo() {
        return BoNo;
    }

    public void setBoNo(String boNo) {
        BoNo = boNo;
    }

    public String getBoNm() {
        return BoNm;
    }

    public void setBoNm(String boNm) {
        BoNm = boNm;
    }

    public int getChkct() {
        return Chkct;
    }

    public void setChkct(int chkct) {
        Chkct = chkct;
    }
}

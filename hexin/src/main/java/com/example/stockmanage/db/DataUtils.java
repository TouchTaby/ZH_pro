package com.example.stockmanage.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * Created by Administrator on 2017-10-16.
 */

public class DataUtils {
    //获取采购进货申请表清单
    //public static ArrayList<String> ordList=new ArrayList<>();
    //产品id列表
    //public static ArrayList<String> prodIdList=new ArrayList<>();
    public static ArrayList<String[][] > warehouseList=new ArrayList<String[][]>();
    static {
        warehouseList.add(new String[][]{{"01","合鑫仓"}});
    }
}

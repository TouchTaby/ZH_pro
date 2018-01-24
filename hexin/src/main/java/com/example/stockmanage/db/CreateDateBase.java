package com.example.stockmanage.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.stockmanage.util.Logs;

/**
 * Created by Administrator on 2016/6/6 0006.
 */
public class CreateDateBase {

    static  String TAG="CreateDateBase";
    public  static  String TAB_Incoming_purRecvOrd="Incoming_purRecvOrd";//采购进货表一
    public  static  String TAB_Incoming_purRecvOrdDet="Incoming_purRecvOrdDet";//采购进货表二
    //public  static  String TABLE_PDADownShpmApl="PDADownShpm_Entity";//销售出货申请单明细
    //采购进货表一
    public static void  create_Incoming_purRecvOrd(SQLiteDatabase db)
    {
        if(!tabIsExist(TAB_Incoming_purRecvOrd,db)) {
            Logs.Info(TAG,"create_Incoming_purRecvOrd");
            StringBuilder sqlData = new StringBuilder();
            sqlData.append("Create TABLE IF NOT EXISTS "+TAB_Incoming_purRecvOrd);
            sqlData.append("(ID integer primary key autoincrement UNIQUE NOT NULL, ");    //id 唯一值，自动增长
            sqlData.append("[BlNo] varchar(20) UNIQUE,");//单据编号
            sqlData.append("[SupId] varchar(20),");//供应商代码
            sqlData.append("[SupNm] nvarchar(100),");//供应商名称
            sqlData.append("[TypeId] varchar(15),");//单据类型
            sqlData.append("[CreateTime] datetime default (datetime('now','localtime'))");//创建时间
            sqlData.append(")");
            db.execSQL(sqlData.toString());
        }
    }
    //采购进货表二
    public static void  create_Incoming_purRecvOrdDet(SQLiteDatabase db)
    {
        if(!tabIsExist(TAB_Incoming_purRecvOrdDet,db)) {
            Logs.Info(TAG,"create_Incoming_purRecvOrdDet");
            StringBuilder sqlData = new StringBuilder();
            sqlData.append("Create TABLE IF NOT EXISTS "+TAB_Incoming_purRecvOrdDet);
            sqlData.append("(ID integer primary key autoincrement UNIQUE NOT NULL, ");    //id 唯一值，自动增长
            sqlData.append("[BlNo] varchar(20),");//单据编号
            sqlData.append("[RowCd] integer default 0,");//标识号
            sqlData.append("[ProdId] varchar(40),");//产品代码
            sqlData.append("[ProdNm] nvarchar(100),");//产品名称
            sqlData.append("[WhsId] varchar(20),");//仓库代码
            sqlData.append("[WhsNm] nvarchar(100),");//仓库名称
            sqlData.append("[WaCfmSQty] numeric(19,9)  default 0,");//待确认数量
            sqlData.append("[CreateTime] datetime default (datetime('now','localtime'))");//创建时间
            sqlData.append(")");
            db.execSQL(sqlData.toString());
        }
    }
    //销售出货申请单明细
    public static void  create_PDADownShpmApl(SQLiteDatabase db)
    {
        /*
        if(!tabIsExist(TABLE_PDADownShpmApl,db)) {
            StringBuilder sqlData = new StringBuilder();
            sqlData.append("Create TABLE IF NOT EXISTS "+TABLE_PDADownShpmApl);
            sqlData.append("(ID integer primary key autoincrement UNIQUE NOT NULL, ");    //id 唯一值，自动增长
            sqlData.append("[BlNo] varchar(20) UNIQUE,");//单据编号
            sqlData.append("[CustId] varchar(20),");//客户代码
            sqlData.append("[CustNm] nvarchar(100),");//客户名称
            sqlData.append("[TypeId] varchar(15),");//单据类型
            sqlData.append("[RowCd] integer default 0,");//标识号
            sqlData.append("[ProdId] varchar(40),");//产品代码
            sqlData.append("[ProdNm] nvarchar(100),");//产品名称
            sqlData.append("[WhsId] varchar(20),");//仓库代码
            sqlData.append("[WhsNm] nvarchar(100),");//仓库名称
            sqlData.append("[WaCfmSQty] integer default 0,");//待确认数量
            sqlData.append("[CreateTime] datetime default (datetime('now','localtime'))");//创建时间
            sqlData.append(")");
            db.execSQL(sqlData.toString());
        }
        */
    }


    /**
     * 判断某张表是否存在
     * @param tabName 表名
     * @return
     */
    public static boolean tabIsExist(String tabName,SQLiteDatabase db){
        boolean result = false;
        if(tabName == null){
            return false;
        }
        try {
            String sql = "select count(*) as c from sqlite_master where type ='table' and name ='"+tabName.trim()+"'" ;
            Cursor cursor = db.rawQuery(sql, null);
            if(cursor.moveToNext()){
                int count = cursor.getInt(0);
                if(count>0){
                    result = true;
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
       if(result)
           Logs.Info(TAG,tabName+"数据表已经存在!");
        else
           Logs.Info(TAG,tabName+"数据表不存在!");
        return result;
    }
}

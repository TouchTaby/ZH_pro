package com.example.stockmanage.db;
import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import com.example.stockmanage.AppContext;
import com.example.stockmanage.util.Logs;

import java.io.File;

public class DBHelper extends SQLiteOpenHelper {

    String TAG="DBHelper";
    public final static String dbName = "stock.db";
    private static DBHelper dbHelper=null;

    public static DBHelper getInstance(){
        if(dbHelper==null){
            dbHelper=new DBHelper(AppContext.context,1);
        }
        return dbHelper;
    }

    /**
     * 数据库的构造函数
     * @param context
     *
     * name 数据库名称
     * factory 游标工程
     * version 数据库的版本号 不可以小于1
     */
    private DBHelper(Context context,
                     int version) {
        super(context, dbName, null, version);
        Logs.Info(TAG,"DBHelper===>");
    }
    /**
     * 数据库第一次创建时回调此方法.
     * 初始化一些表
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 操作数据库
        Logs.Info(TAG,"onCreate===>");
        CreateDateBase.create_Incoming_purRecvOrd(db);
        CreateDateBase.create_Incoming_purRecvOrdDet(db);
    }
    /**
     * 数据库的版本号更新时回调此方法,
     * 更新数据库的内容(删除表, 添加表, 修改表)
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if(oldVersion == 1 && newVersion == 2) {
            //Log.i(TAG, "数据库更新啦");
            // 在person表中添加一个余额列balance
        }
    }
}


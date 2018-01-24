package com.example.stockmanage.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Administrator on 2017-01-09.
 */
public class DatabaseManager {

    private static DatabaseManager instance = null;
    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }
    public  int delete(String tabName)//清空订单信息
    {
        int r=-1;
        SQLiteDatabase db =   DBHelper.getInstance().getWritableDatabase();
        if(db.isOpen()){
            r=db.delete(tabName,null,null);
            db.close();
        }
        return  r;
    }
    public synchronized long insert(SQLiteDatabase db,String table, ContentValues values) {
        long result=-1;
        try{
            if (db!= null)
                result= db.insert(table, null, values);
        }catch (Exception ex) {
        } finally {
            db.close();
        }
        return  result;
    }
    public synchronized Cursor query(SQLiteDatabase db, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        if (db == null) {
            return null;
        }
        try {
            return db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
        } catch (Exception ex) {
            return null;
        }
    }
/*
    public final static String dataName = Environment.getExternalStorageDirectory().toString()+File.separator+"data"+File.separator+"DB";

    public SQLiteDatabase getWritableDatabase() {
        if (mOpenCounter.get() > 0) {
            // Opening new database
            try {
                Thread.sleep(10);
                //System.out.println("write waiting for free:"+String.valueOf(mOpenCounter.get()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return this.getWritableDatabase();
        } else {
            //System.out.println("incresement value");
            synchronized (this) {
                mOpenCounter.incrementAndGet();
            }
            return mDatabase;
        }
    }

    public SQLiteDatabase getReadableDatabase() {
        if (mOpenCounter.get() > 0) {
            // Opening new database
            try {
                Thread.sleep(10);
                //System.out.println("read waiting for free"+String.valueOf(mOpenCounter.get()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return this.getReadableDatabase();
        } else {
            //System.out.println("incresement value");
            synchronized (this) {
                mOpenCounter.incrementAndGet();
            }
            return mDatabase;
        }
    }

    public void closeDatabase() {

        if (mOpenCounter.get() > 0){
            synchronized (this) {
                mOpenCounter.decrementAndGet();
            }
            //System.out.println("close operate OK");
        } else {
            //System.out.println("close operate ERROE");
        }
    }


     * 打开数据库
     *
     * param dbFile
     * @return SQLiteDatabase
     * @author

    public SQLiteDatabase openDateBase() {
        String databasePath = dataName;
        try {
            File f=new File(databasePath);
            if(!f.exists()) {
                if(!f.mkdirs())//注意是mkdirs()有个s 这样可以创建多重目录。
                {
                    return null;
                }
            }
            mDatabase = SQLiteDatabase.openOrCreateDatabase(databasePath+File.separator+dataName, null);

        } catch (Exception e) {
            Log.e("openDateBase", e.getMessage());
        }
        if(mDatabase!=null) {
//              database.execSQL("DROP TABLE "+CreateDateBase.TABLE_IMG);
            if (!checkColumnExists(mDatabase, CreateDateBase.TABLE_REAERVATION, "reservation_learned_miles"))
            {
                if (CreateDateBase.tabIsExist(CreateDateBase.TABLE_REAERVATION, mDatabase))
                {
                    mDatabase.execSQL("DROP TABLE "+CreateDateBase.TABLE_REAERVATION);
                }
            }
            if (!checkColumnExists(mDatabase, CreateDateBase.TABLE_BUNDLE_INFO, "finger"))
            {
                if (CreateDateBase.tabIsExist(CreateDateBase.TABLE_BUNDLE_INFO, mDatabase))
                {
                    mDatabase.execSQL("DROP TABLE "+CreateDateBase.TABLE_BUNDLE_INFO);
                }
            }
            CreateDateBase.create_LoginInfo(mDatabase);

        }
        return mDatabase;
    }

    private boolean checkColumnExists(SQLiteDatabase db, String tableName, String columnName) {
        boolean result = false ;
        Cursor cursor = null ;

        try{
            cursor = db.rawQuery( "select * from sqlite_master where name = ? and sql like ?"
                    , new String[]{tableName , "%" + columnName + "%"} );
            result = null != cursor && cursor.moveToFirst() ;
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            if(null != cursor && !cursor.isClosed()){
                cursor.close() ;
            }
        }
        return result ;
    }

    public SQLiteDatabase getSQLiteDatabase()
    {
        return mDatabase;
    }

    */
}
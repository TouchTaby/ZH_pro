package com.example.stockmanage.util;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-5-22.
 */

public class FileUtils {

    public static String TAG="FileUtils";
    public final static String PATH = Environment
            .getExternalStorageDirectory()
            + File.separator
            + "stockmanage"
            + File.separator;
    static {
        File filePath=new File(PATH);
        if(!filePath.exists()){
            filePath.mkdirs();
        }
    }

    public  static void WriterFile(String fileName,String data){
        if(data.isEmpty())
            return;
        String filePath=fileName;
        File file =new File(filePath);
        FileOutputStream fileOutputStream = null;
        if (!file.exists()) {
            try{
                file.createNewFile();
                Runtime runtime = Runtime.getRuntime();
                runtime.exec("chmod 0666 "+file);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        try{
            fileOutputStream = new FileOutputStream(file,true);
            fileOutputStream.write(data.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (Exception e) {
            }
        }

    }


    public static void writerLog(String data){

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMdd");//设置日期格式
        String time2= df2.format(new Date());
        String path=PATH+time2+".txt";
        WriterFile(path,df.format(new Date())+"  "+data+" \r\n");
        deleteFile();
    }

    public static void writerError(String data){
        String path=PATH+"error.txt";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        WriterFile(path,df.format(new Date())+"  "+data+" \r\n");
    }
    private static void deleteFile(){
        try{
            File filelist = new File(PATH);
            if (filelist.exists()) {
                File[] files = filelist.listFiles();
                if(files.length>7){
                    //----------------------------------------------------------------
                    Map<Integer, File> fileMap = new HashMap<Integer, File>();
                    for(int k=0;k<files.length;k++){
                        try {
                            String fileName = files[k].getName();
                            fileMap.put(Integer.parseInt((fileName).substring(0, fileName.indexOf("."))), files[k]);
                        }catch (Exception ex){}
                    }

                    //这里将map.entrySet()转换成list
                    List<Map.Entry<Integer,File>> list = new ArrayList<Map.Entry<Integer,File>>(fileMap.entrySet());
                    //然后通过比较器来实现排序
                    Collections.sort(list,new Comparator<Map.Entry<Integer,File>>() {
                        //升序排序
                        public int compare(Map.Entry<Integer, File> o1,
                                           Map.Entry<Integer, File> o2) {
                            return o1.getValue().compareTo(o2.getValue());
                        }
                    });
                    //----------------------------------------------------------------
                    int dFile=files.length-7;
                    for(Map.Entry<Integer,File> mapping:list){
                        mapping.getValue().delete();
                        dFile=dFile-1;
                        if(dFile<=0)
                        {
                            break;
                        }
                    }

                }
            }

        }catch(Exception ex){
          	Log.e(TAG, "deleteFile   Exceptione");
        }

    }


}

package com.example.stockmanage.net;

/**
 * Created by Administrator on 2016/10/28.
 */

import com.example.stockmanage.AppContext;
import com.example.stockmanage.util.Common;
import com.example.stockmanage.util.ErrorManage;
import com.example.stockmanage.util.Logs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

public class HttpUtils {

    private static final String TAG = "HttpUtils";
    private static String mSessionId;
    public static String getURL(){
        return Common.getIP(AppContext.context)+"/functions/pdahandler.ashx";
    }
   // public static String urlRootPath2 = urlRootPath+"?progid=X_PDAInterface";
    public static String url_progid="progid=X_PDAInterface";



    public static String[] HttpGetData(String strPath) {
        Logs.Info(TAG,"请求地址:"+strPath);
        String[] result=new String[2];
        try {
            int timeout=5000;
            URL  url = new URL(strPath);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();  // 打开连接
            urlConnection.setConnectTimeout(timeout);
            urlConnection.setReadTimeout(timeout);
            urlConnection.setRequestMethod("GET");
            //必须设置false，否则会自动redirect到重定向后的地址
            urlConnection.setInstanceFollowRedirects(false);
            urlConnection.connect();
            int responCode=urlConnection.getResponseCode();
            String msg=urlConnection.getResponseMessage();
            if(responCode==302) {
                //判定是否会进行302重定向
                //如果会重定向，保存302重定向地址，以及Cookies,然后重新发送请求(模拟请求)
                String location = urlConnection.getHeaderField("Location");
                Logs.Info(TAG,"重定向地址:"+location);
                url = new URL(location);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(timeout);
                urlConnection.setReadTimeout(timeout);
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                responCode=urlConnection.getResponseCode();
                msg=urlConnection.getResponseMessage();
            }

            if(responCode==200 ) {
                InputStream is = urlConnection.getInputStream();
                byte[] ret = new byte[1024*1024*4];
                int len = is.read(ret);
                if (is != null) {
                    is.close();
                }
                String data=new String(ret, 0, len);
                result[0]= ErrorManage.Error_Success;
                result[1]=data;
            }else{
                result[0]=responCode==0?"-10":(responCode+"");
                result[1]=msg;
            }
        } catch (MalformedURLException ex) {
            result[0]="-1";
            result[1]="MalformedURLException";
        }catch (ConnectException ex){
            result[0]="-2";
            result[1]="连接服务器失败";
        }catch (SocketTimeoutException ex){
            result[0]="-3";
            result[1]="服务器响应超时";
        }catch (IOException ex){
            result[0]="-4";
            result[1]="IOException";
        }
        Logs.Info(TAG,"HttpGetData:"+result[0]+","+result[1]);
        return  result;
    }
    public static String[] HttpPostData(String strUrlPath, byte[] data) {
        int timeout=30*1000;
        String[] result=new String[2];
        try {
            URL url = new URL(strUrlPath);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(timeout);
            httpURLConnection.setReadTimeout(timeout);
            httpURLConnection.setDoInput(true);                  //打开输入流，以便从服务器获取数据
            httpURLConnection.setDoOutput(true);                 //打开输出流，以便向服务器提交数据
            httpURLConnection.setRequestMethod("POST");          //设置以Post方式提交数据
            httpURLConnection.setUseCaches(false);               //使用Post方式不能使用缓存

            //设置请求体的类型是文本类型
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //设置请求体的长度
            if(data!=null && data.length>0) {
                httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
                //获得输出流，向服务器写入数据
                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(data);
            }
            httpURLConnection.connect();
            int response = httpURLConnection.getResponseCode();            //获得服务器的响应码
            String msg=httpURLConnection.getResponseMessage();
            if (response == HttpURLConnection.HTTP_OK) {
                /*
                InputStream is = httpURLConnection.getInputStream();
                byte[] ret = new byte[1024*1024*4];
                int leng=0;

                while (true){
                    int index = is.read(ret,leng,1024);
                    if(index==-1)
                        break;
                    else
                        leng=leng+index;
                }
                if (is != null) {
                    is.close();
                }
                String data1=new String(ret, 0, leng);
                */

                InputStream inputStream= httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
                BufferedReader br = new BufferedReader(inputStreamReader); // 获取输入流
                String line = null;
                StringBuilder sb = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                if(br!=null)
                    br.close();
                inputStreamReader.close();
                inputStream.close();
                result[0]=ErrorManage.Error_Success;
                result[1]=sb.toString();
            }else{
                result[0]=response==0?"-10":(response+"");
                result[1]=msg;
            }
        }catch (MalformedURLException ex) {
            result[0]="-1";
            result[1]="MalformedURLException";
        }catch (ConnectException ex){
            result[0]="-2";
            result[1]="连接服务器失败";
        }catch (SocketTimeoutException ex){
            result[0]="-3";
            result[1]="服务器响应超时";
        }catch (IOException ex){
            result[0]="-4";
            result[1]="IOException";
        }
        Logs.Info(TAG,"HttpPostData:"+result[0]+","+result[1]);
        return  result;
    }


}
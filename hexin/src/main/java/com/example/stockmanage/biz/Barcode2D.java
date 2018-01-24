package com.example.stockmanage.biz;


import com.example.stockmanage.AppContext;
import com.example.stockmanage.util.Logs;
import com.example.stockmanage.util.SoundManager;
import com.zebra.adc.decoder.Barcode2DWithSoft;

/**
 * Created by Administrator on 2017-6-17.
 */

public class Barcode2D {

    private  Barcode2DWithSoft barcode2DWithSoft=Barcode2DWithSoft.getInstance();
    private  String TAG="Barcode2D";
    private static Barcode2D barcode2D=null;
    private IScanCallBack scanCallBack=null;
    private static SoundManager soundManager=null;
    public static  Barcode2D getInstance(){
      if(barcode2D==null) {
          soundManager=SoundManager.getInstance(AppContext.context);
          barcode2D = new Barcode2D();
      }
        return barcode2D;
    }

    public Barcode2DWithSoft.ScanCallback mScanCallback = new Barcode2DWithSoft.ScanCallback() {
        @Override
        public void onScanComplete(int symbology, int length, byte[] data) {

            if (length < 1) {
                soundManager.playSound(2);
                if (length == -1) {
                     Logs.Info(TAG,"扫描取消");
                } else if (length == 0) {
                    Logs.Info(TAG,"扫描超时");
                } else {
                    Logs.Info(TAG,"扫描失败");
                }
                if(scanCallBack!=null){
                    scanCallBack.onScanResults("");
                }
            }else{
                soundManager.playSound(1);
                String barCode = new String(data, 0, length);
                Logs.Info(TAG, "扫描成功，数据：" + barCode);
                if(scanCallBack!=null){
                    scanCallBack.onScanResults(barCode);
                }
            }
        }
    };
    public boolean Open(){
        Logs.Info(TAG,"打开2D");
        if(barcode2DWithSoft.isPowerOn()) {
            Logs.Info(TAG,"2D已经上电!");
            return  true;
        }
          boolean result=barcode2DWithSoft.open(AppContext.context);
          if(result) {
              barcode2DWithSoft.setScanCallback(mScanCallback);
              return  true;
          }else{
              return  false;
          }
    }
    public boolean Close(){
        Logs.Info(TAG,"关闭2D");
        return barcode2DWithSoft.close();
    }
    public void Scan(IScanCallBack scanCallBack){
        Logs.Info(TAG,"开始扫描");
        this.scanCallBack=scanCallBack;
         barcode2DWithSoft.scan();
    }
    public void StopScan(){
        Logs.Info(TAG,"停止扫描");
        barcode2DWithSoft.stopScan();
    }
}

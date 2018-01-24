package com.example.stockmanage.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.stockmanage.R;
import com.example.stockmanage.biz.Incoming;
import com.example.stockmanage.db.DataUtils;
import com.example.stockmanage.db.DatabaseManager;
import com.example.stockmanage.entity.Incoming_purRecvOrdDet_Entity;
import com.example.stockmanage.entity.Incoming_purRecvOrd_Entity;
import com.example.stockmanage.util.ErrorManage;

import java.util.ArrayList;

public class DownActivity extends BaseActivity implements View.OnClickListener{

    Button btnPDADownRecOrdList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down);
        btnPDADownRecOrdList=(Button)findViewById(R.id.btnPDADownRecOrdList);
        btnPDADownRecOrdList.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnPDADownRecOrdList:
                new PDADownRecOrdListTask().execute();
                break;
        }
    }
    public class PDADownRecOrdListTask extends AsyncTask<String, Integer, Boolean> {
        ProgressDialog mypDialog;
        String msg;
        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub
            Incoming incoming= new Incoming();
            ArrayList<Incoming_purRecvOrd_Entity> purRecvOrdDet_OutListData =new  ArrayList<Incoming_purRecvOrd_Entity>();
            ArrayList<Incoming_purRecvOrdDet_Entity > Incoming_purRecvOrdDet_outListData=new ArrayList<Incoming_purRecvOrdDet_Entity >();
            String[] result= incoming.pdpDownRecOrdList2(purRecvOrdDet_OutListData, Incoming_purRecvOrdDet_outListData);
            if(result[0].equals(ErrorManage.Error_Success)){
                 if(purRecvOrdDet_OutListData.size()>0 && Incoming_purRecvOrdDet_outListData.size()>0){

                     incoming.deleteIncoming_purRecvOrd();
                     incoming.deleteIncoming_purRecvOrdDet();
                     for(int k=0;k<purRecvOrdDet_OutListData.size();k++){
                         incoming.insertIncoming_purRecvOrd(purRecvOrdDet_OutListData.get(k));
                     }
                     for(int k=0;k<Incoming_purRecvOrdDet_outListData.size();k++){
                         incoming.insertIncoming_purRecvOrdDet(Incoming_purRecvOrdDet_outListData.get(k));
                     }

                     return  true;
                 }
            }else{
                msg=result[1];
            }
            return  false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            mypDialog.cancel();
            if(result){
                Toast.makeText(DownActivity.this,"下载成功!",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(DownActivity.this,"下载失败!"+"("+msg+")",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mypDialog = new ProgressDialog(DownActivity.this);
            mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mypDialog.setMessage("下载中...");
            mypDialog.setCanceledOnTouchOutside(false);
            mypDialog.setCancelable(false);
            mypDialog.show();
        }
    }



}

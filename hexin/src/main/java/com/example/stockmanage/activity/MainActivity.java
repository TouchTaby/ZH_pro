package com.example.stockmanage.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.stockmanage.R;
import com.example.stockmanage.biz.Barcode2D;
import com.example.stockmanage.db.DBHelper;
import com.zebra.adc.decoder.Barcode2DWithSoft;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    LinearLayout llIncoming;
    LinearLayout llOutGoing;
    LinearLayout llStock;
    LinearLayout llDownLoad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        llIncoming=(LinearLayout)findViewById(R.id.llIncoming);
        llOutGoing=(LinearLayout)findViewById(R.id.llOutGoing);
        llStock=(LinearLayout)findViewById(R.id.llStock);
        llDownLoad=(LinearLayout)findViewById(R.id.llDownLoad);
        llDownLoad.setOnClickListener(this);
        llIncoming.setOnClickListener(this);
        llOutGoing.setOnClickListener(this);
        llStock.setOnClickListener(this);
        new InitTask().execute();
    }

    @Override
    protected void onDestroy() {
        Barcode2D.getInstance().Close();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
           Intent intent;
          switch(v.getId()){
              case R.id.llIncoming:
                  intent=new Intent(this,IncomingActivity.class);
                  startActivity(intent);
                  break;
              case R.id.llOutGoing:
                  intent=new Intent(this,OutGoingActivity.class);
                  startActivity(intent);
                  break;
              case R.id.llStock:
                  intent=new Intent(this,StockActivity.class);
                  startActivity(intent);
                  break;
              case R.id.llDownLoad:
                  intent=new Intent(this,DownActivity.class);
                  startActivity(intent);
                  break;
          }
    }


    public class InitTask extends AsyncTask<String, Integer, Boolean> {
        ProgressDialog mypDialog;
        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub
            return Barcode2D.getInstance().Open();
        }
        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if(!result){
                Toast.makeText(MainActivity.this,"初始化扫描头失败!",Toast.LENGTH_SHORT).show();
            }
            mypDialog.cancel();
        }
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mypDialog = new ProgressDialog(MainActivity.this);
            mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mypDialog.setMessage("初始化...");
            mypDialog.setCanceledOnTouchOutside(false);
            mypDialog.show();
        }

    }

}

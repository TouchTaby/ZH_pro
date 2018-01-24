package com.example.stockmanage.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stockmanage.R;
import com.example.stockmanage.biz.BaseInfo;
import com.example.stockmanage.biz.Stock;
import com.example.stockmanage.entity.InventoryBook_Entity;
import com.example.stockmanage.entity.UserInfoEntity;
import com.example.stockmanage.entity.Warehouse_Entity;
import com.example.stockmanage.util.ErrorManage;
import com.example.stockmanage.view.SpinerPopWindow;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * 盘点
 */
public class StockActivity extends BaseActivity {

    SpinerPopWindow spBoNm;//盘点清册
    SpinerPopWindow spUser;//盘点人员
    SpinerPopWindow spWhsNm;//盘点仓库
    Button btStart;//开始盘点
    EditText etData;
    ArrayList<Warehouse_Entity> WhsList=new ArrayList<Warehouse_Entity>();
    ArrayList<UserInfoEntity> UserINFO=new ArrayList<UserInfoEntity>();
    ArrayList<InventoryBook_Entity> InventoryBook=new ArrayList<InventoryBook_Entity>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
          spBoNm=(SpinerPopWindow)findViewById(R.id.spBoNm);//盘点清册
          spUser=(SpinerPopWindow)findViewById(R.id.spUser);//盘点人员
          spWhsNm=(SpinerPopWindow)findViewById(R.id.spWhsNm);//盘点仓库
          btStart=(Button)findViewById(R.id.btStart);//盘点仓库
          etData=(EditText)findViewById(R.id.etData);//;

          spBoNm.getInputEditText().setEnabled(false);
          spUser.getInputEditText().setEnabled(false);
          spWhsNm.getInputEditText().setEnabled(false);
          init();
    }
    private void init(){
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int  mDay = c.get(Calendar.DAY_OF_MONTH);
        etData.setText(new StringBuilder().append(mYear).append("-")
                .append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append("-")
                .append((mDay < 10) ? "0" + mDay : mDay));
         new downInventoryBookTask().execute();
        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(StockActivity.this,StockDetailedActivity.class);
                for(int k=0;k<InventoryBook.size();k++){
                    if(InventoryBook.get(k).getBoNm().equals(spBoNm.getInput())) {
                        intent.putExtra(InventoryBook_Entity._BoNo, InventoryBook.get(k).getBoNo());
                        intent.putExtra(InventoryBook_Entity._Chkct, InventoryBook.get(k).getChkct());
                    }
                }
                StockActivity.this.startActivity(intent);
            }
        });
        etData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(0);
            }
        });
    }


    //---------------------------获取日期------------------------------------------
    /**
     * 日期控件的事件
     */
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            int   mYear = year;
            int   mMonth = monthOfYear;
            int   mDay = dayOfMonth;
            etData.setText(new StringBuilder().append(mYear).append("-")
                    .append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append("-")
                    .append((mDay < 10) ? "0" + mDay : mDay));
        }
    };
    @Override
    protected Dialog onCreateDialog(int id) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int  mDay = c.get(Calendar.DAY_OF_MONTH);
        return  new DatePickerDialog(StockActivity.this, mDateSetListener, mYear, mMonth,mDay);
    }
    //---------------------------获取日期-end-----------------------------------------


    public class downBaseInfoTask extends AsyncTask<String, Integer, Boolean> {
        ProgressDialog mypDialog;
        String msg[];

        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub
            msg=BaseInfo.getInstance().PDADownWhs(WhsList);
            return  false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            mypDialog.cancel();
            if(msg[0].equals(ErrorManage.Error_Success)){
                //  Toast.makeText(IncomingActivity.this,"下载成功!",Toast.LENGTH_SHORT).show();
                //---------初始化清单数据---------------------
                if(WhsList.size()>0) {
                    ArrayList<String> list=new ArrayList<String>();
                    for(int k=0;k<WhsList.size();k++){
                        list.add(WhsList.get(k).getWhsNm());
                    }
                    spWhsNm.setData(list);
                    spWhsNm.setInput(list.get(0));
                }
                //----------------------------------------------------------------------
            }else{
                Toast.makeText(StockActivity.this,"仓库数据失败!"+"("+msg[1]+")",Toast.LENGTH_SHORT).show();
            }
            new downBaseUserInfoTask().execute();
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mypDialog = new ProgressDialog(StockActivity.this);
            mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mypDialog.setMessage("正在下载仓库信息...");
            mypDialog.setCanceledOnTouchOutside(false);
            mypDialog.setCancelable(false);
            mypDialog.show();
        }
    }


    public class downBaseUserInfoTask extends AsyncTask<String, Integer, Boolean> {
        ProgressDialog mypDialog;
        String msg[];

        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub
            msg=BaseInfo.getInstance().PDADownUser(UserINFO);
            return  false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            mypDialog.cancel();
            if(msg[0].equals(ErrorManage.Error_Success)){
                //  Toast.makeText(IncomingActivity.this,"下载成功!",Toast.LENGTH_SHORT).show();
                //---------初始化清单数据---------------------
                if(UserINFO.size()>0) {
                    ArrayList<String> list=new ArrayList<String>();
                    for(int k=0;k<UserINFO.size();k++){
                        list.add(UserINFO.get(k).getPerNm());
                    }
                    spUser.setData(list);
                    spUser.setInput(list.get(0));
                }
                //----------------------------------------------------------------------
            }else{
                Toast.makeText(StockActivity.this,"下载失败!"+"("+msg[1]+")",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mypDialog = new ProgressDialog(StockActivity.this);
            mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mypDialog.setMessage("正在下载人员信息...");
            mypDialog.setCanceledOnTouchOutside(false);
            mypDialog.setCancelable(false);
            mypDialog.show();
        }
    }


    public class downInventoryBookTask extends AsyncTask<String, Integer, Boolean> {
        ProgressDialog mypDialog;
        String msg[];

        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub
            msg= Stock.getInstance().PDADownStock(InventoryBook);
            return  false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            mypDialog.cancel();
            if(msg[0].equals(ErrorManage.Error_Success)){
                //  Toast.makeText(IncomingActivity.this,"下载成功!",Toast.LENGTH_SHORT).show();
                //---------初始化清单数据---------------------
                if(InventoryBook.size()>0) {
                    ArrayList<String> list=new ArrayList<String>();
                    for(int k=0;k<InventoryBook.size();k++){
                        list.add(InventoryBook.get(k).getBoNm());
                    }
                    spBoNm.setData(list);
                    spBoNm.setInput(list.get(0));
                }
                //----------------------------------------------------------------------
            }else{
                Toast.makeText(StockActivity.this,"下载失败!"+"("+msg[1]+")",Toast.LENGTH_SHORT).show();
            }
            new downBaseInfoTask().execute();
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mypDialog = new ProgressDialog(StockActivity.this);
            mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mypDialog.setMessage("正在下载盘点清册...");
            mypDialog.setCanceledOnTouchOutside(false);
            mypDialog.setCancelable(false);
            mypDialog.show();
        }
    }
}

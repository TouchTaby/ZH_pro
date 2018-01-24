package com.example.stockmanage.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.stockmanage.R;
import com.example.stockmanage.biz.Barcode2D;
import com.example.stockmanage.biz.Stock;
import com.example.stockmanage.entity.DownInventoryBook_Entity;
import com.example.stockmanage.entity.InventoryBook_Entity;
import com.example.stockmanage.util.ErrorManage;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * 盘点
 */
public class StockDetailedActivity extends AppCompatActivity {
    SimpleAdapter adapter;
    private ArrayList<HashMap<String, String>> desList=new ArrayList<HashMap<String, String>>();
    Button btOK;//提交
    ListView tvData;
    EditText etWide;//宽幅
    EditText etLevel;//等级
    EditText etSpecification;//规格
    EditText etWhsNm;//仓库
    EditText etNumb2;//盘点数量
    EditText etNumb1;//账面数量
    EditText etProName;//物料名称
    EditText etProID;//物料代码
    EditText etBOM;//条码扫描
    String _BoNo;
    int _Chkct;
    String strIndex="index";
    ScanCallBack scanCallBack=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_detailed);
      Intent intent= getIntent();
        _BoNo=intent.getStringExtra(InventoryBook_Entity._BoNo);
        _Chkct=Integer.parseInt(intent.getStringExtra(InventoryBook_Entity._Chkct));
        init();
    }
    private void init(){
        //---------------------设置listView数据-----------------------------------------
        adapter = new SimpleAdapter(this, desList, R.layout.outgoing_list_item,
                new String[] {strIndex,DownInventoryBook_Entity._ProdId,DownInventoryBook_Entity._ProdNm,DownInventoryBook_Entity._Qty},
                new int[] { R.id.tvStockIndex,R.id._StockProdID,R.id._StockProdName,R.id._StockNuber});

          scanCallBack=new ScanCallBack();
          btOK=(Button)findViewById(R.id.btOK);//提交
          tvData=(ListView)findViewById(R.id.tvData);
          etWide=(EditText)findViewById(R.id.etWide);//宽幅
          etLevel=(EditText)findViewById(R.id.etLevel);//等级
          etSpecification=(EditText)findViewById(R.id.etSpecification);//规格
          etWhsNm=(EditText)findViewById(R.id.etWhsNm);//仓库
          etNumb2=(EditText)findViewById(R.id.etNumb2);//盘点数量
          etNumb1=(EditText)findViewById(R.id.etNumb1);//账面数量
          etProName=(EditText)findViewById(R.id.etProName);//物料名称
          etProID=(EditText)findViewById(R.id.etProID);//物料代码
          etBOM=(EditText)findViewById(R.id.etBOM);//条码扫描
          btOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==139){
            if(event.getRepeatCount()==0) {
                Barcode2D.getInstance().Scan(scanCallBack);
                return true;
            }
        }if(keyCode==4){

            AlertDialog.Builder builder =new AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("您确定要退出吗?");
            //这里添加上这个view
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog,int which) {
                    finish();
                }
            });

            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.create().show();
            return  true;
        }
        return super.onKeyDown(keyCode, event);
    }
    private class ScanCallBack implements com.example.stockmanage.biz.IScanCallBack{
        @Override
        public void onScanResults(String var1) {
            etBOM.setText(var1);
            new waitDownTask(1,"").execute();
        }
    }
    private class waitDownTask extends AsyncTask<String, Integer, Boolean> {
            int chkst;
            String blno;
            public  waitDownTask(int chkst,String blno) {
                this.chkst=chkst;
                this.blno=blno;
            }
        ProgressDialog mypDialog;
        String msg[];
        ArrayList<DownInventoryBook_Entity> data1=new ArrayList<DownInventoryBook_Entity>();
        ArrayList<DownInventoryBook_Entity> data2=new ArrayList<DownInventoryBook_Entity>();
        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub
            msg= Stock.getInstance().PDADownChkInve(chkst,blno,data1,data2);
            return  false;
        }
        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            mypDialog.cancel();
            if(msg[0].equals(ErrorManage.Error_Success)){
                for(int k=0;k<data2.size();k++){
                    DownInventoryBook_Entity entity=data2.get(k);
                    etWide.setText(entity.getCU_mm());
                    etLevel.setText(entity.getCU_dengji());
                    etSpecification.setText(entity.getProdSpec());//规格
                    etWhsNm.setText(entity.getWhsNm());//仓库
                    etNumb2.setText(entity.getQty()+"");//盘点数量
                    etNumb1.setText(entity.getBoQty()+"");//账面数量
                    etProName.setText(entity.getProdNm());//物料名称
                    etProID.setText(entity.getProdId());//物料代码
                }
                adapter.notifyDataSetChanged();
            }else{
                Toast.makeText(StockDetailedActivity.this,"查询失败!"+"("+msg[1]+")",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mypDialog = new ProgressDialog(StockDetailedActivity.this);
            mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mypDialog.setMessage("正在查询数据...");
            mypDialog.setCanceledOnTouchOutside(false);
            mypDialog.setCancelable(false);
            mypDialog.show();
        }
    }
}

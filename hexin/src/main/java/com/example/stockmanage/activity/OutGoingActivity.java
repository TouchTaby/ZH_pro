package com.example.stockmanage.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.stockmanage.R;
import com.example.stockmanage.biz.Barcode2D;
import com.example.stockmanage.biz.BaseInfo;
import com.example.stockmanage.biz.Batch;
import com.example.stockmanage.biz.Incoming;
import com.example.stockmanage.biz.OutGoing;
import com.example.stockmanage.entity.Batch_Entity;
import com.example.stockmanage.entity.PDADownShpm_Entity;
import com.example.stockmanage.entity.ProBar_Entity;
import com.example.stockmanage.entity.Warehouse_Entity;
import com.example.stockmanage.util.ErrorManage;
import com.example.stockmanage.view.SpinerPopWindow;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 出货
 */
public class OutGoingActivity extends BaseActivity implements View.OnClickListener{
    ArrayList<PDADownShpm_Entity> pdaDownShpm_OutListData =new ArrayList<PDADownShpm_Entity>();
    ArrayList<PDADownShpm_Entity> pdaDown_PorListData =new ArrayList<PDADownShpm_Entity>();
    ArrayList<String> billList=new ArrayList<String>();
    SimpleAdapter   adapter;
    ScanCallBack scanCallBack=null;
    SpinerPopWindow spwOutGingBill;
    SpinerPopWindow spwWhsNm;
    EditText etCustNm;//客户名称
    EditText edCustId;//客户ID
    EditText etProdId;//产品ID
    EditText etProdNm;//产品名称
    EditText etProdSpec;//规格
    EditText etCU_dengji;//等级
    EditText etCU_mm;//宽幅
    EditText  etStockID;//仓库ID
    EditText etTypeId; //单据类型
    ListView lvListData;
    Button btnSubmit;
    String strIndex="index";
    ArrayList<Warehouse_Entity> WhsList=new ArrayList<Warehouse_Entity>();
    private ArrayList<HashMap<String, String>> desList=new ArrayList<HashMap<String, String>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        exitNotify=true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_going);
        init();
    }

    private void init(){
        setTitle("销售出货");
        scanCallBack=new ScanCallBack();
        new DownListTask().execute();
        spwOutGingBill=(SpinerPopWindow)findViewById(R.id.spwOutGingBill);
        spwWhsNm=(SpinerPopWindow)findViewById(R.id.spwWhsNm);
        etCustNm=(EditText)findViewById(R.id.etCustNm);
        etProdId=(EditText)findViewById(R.id.etProdId);
          etProdNm=(EditText)findViewById(R.id.etProdNmOut);
          etProdSpec=(EditText)findViewById(R.id.etProdSpecOut);
          etCU_dengji=(EditText)findViewById(R.id.etCU_dengjiOut);
          etCU_mm=(EditText)findViewById(R.id.etCU_mm);
        etStockID=(EditText)findViewById(R.id.etStockID);
        edCustId=(EditText)findViewById(R.id.edCustId);
        etTypeId=(EditText)findViewById(R.id.etTypeId);
        lvListData=(ListView)findViewById(R.id.lvListData);
        btnSubmit=(Button)findViewById(R.id.btnSubmit);
        etProdId.requestFocus();
        etProdSpec.setEnabled(false);
        etCU_dengji.setEnabled(false);
        etCU_mm.setEnabled(false);
        etProdNm.setEnabled(false);
        etCustNm.setEnabled(false);
        spwOutGingBill.setInputEnable(false);
        spwWhsNm.setInputEnable(false);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,String> map =new HashMap<String, String>();
                map.put(PDADownShpm_Entity._BlNo,spwOutGingBill.getInput());
                map.put(PDADownShpm_Entity._CustId, edCustId.getText().toString());
                map.put(PDADownShpm_Entity._CustNm,etCustNm.getText().toString());
                map.put(PDADownShpm_Entity._WhsNm,spwWhsNm.getInput());
                map.put(PDADownShpm_Entity._WhsId,etStockID.getText().toString());
                map.put(PDADownShpm_Entity._TypeId,etTypeId.getText().toString());
                ArrayList<HashMap<String,String>> listTable1=new ArrayList<HashMap<String, String>>();
                boolean flag=true;
                for(int k=0;k<desList.size();k++){
                    if(Double.parseDouble(desList.get(k).get(PDADownShpm_Entity._SQty))>0){
                        flag=false;
                    }else{
                        continue;
                    }
                    HashMap<String,String> map2=new HashMap<String,String>();
                    map2.put(PDADownShpm_Entity._BlNo,desList.get(k).get(PDADownShpm_Entity._BlNo));
                    map2.put(PDADownShpm_Entity._RowCd,desList.get(k).get(PDADownShpm_Entity._RowCd));
                    map2.put(PDADownShpm_Entity._ProdId,desList.get(k).get(PDADownShpm_Entity._ProdId));
                    map2.put(PDADownShpm_Entity._ProdNm,desList.get(k).get(PDADownShpm_Entity._ProdNm));
                    map2.put(PDADownShpm_Entity._WhsId,etStockID.getText().toString());
                    map2.put(PDADownShpm_Entity._WhsNm,spwWhsNm.getInput());
                    map2.put(PDADownShpm_Entity._SQty,desList.get(k).get(PDADownShpm_Entity._SQty));
                    map2.put(PDADownShpm_Entity._ProdSpec,desList.get(k).get(PDADownShpm_Entity._ProdSpec));
                    map2.put(PDADownShpm_Entity._CU_dengji,desList.get(k).get(PDADownShpm_Entity._CU_dengji));
                    map2.put(PDADownShpm_Entity._CU_mm,desList.get(k).get(PDADownShpm_Entity._CU_mm));


                    map2.put(Batch_Entity._BatNo,desList.get(k).get(Batch_Entity._BatNo));
                    map2.put(Batch_Entity._StkStId,desList.get(k).get(Batch_Entity._StkStId));
                    map2.put(Batch_Entity._StkBiza,desList.get(k).get(Batch_Entity._StkBiza));

                    listTable1.add(map2);
                }
                if(flag){
                    //如果发货数量都是0，那么就提示用户
                    Toast.makeText(OutGoingActivity.this,"发货数量为0,请补充发货数量!",Toast.LENGTH_SHORT).show();
                    return  ;
                }

                new WaitUPTask(map,listTable1).execute();
            }
        });
        etProdId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    getPro(etProdId.getText().toString());
                }
            }
        });
        //---------------------设置listView数据-----------------------------------------
        adapter = new SimpleAdapter(this, desList, R.layout.outgoing_list_item,
                new String[] { strIndex,PDADownShpm_Entity._BlNo,PDADownShpm_Entity._RowCd,PDADownShpm_Entity._ProdId, PDADownShpm_Entity._ProdNm,
                               PDADownShpm_Entity._WhsId,PDADownShpm_Entity._WhsNm,PDADownShpm_Entity._WaCfmSQty,PDADownShpm_Entity._ProdSpec,
                               PDADownShpm_Entity._CU_dengji,PDADownShpm_Entity._CU_mm,PDADownShpm_Entity._SQty,Batch_Entity._BatNo,Batch_Entity._Qty},
                   new int[] { R.id.tvIndex,R.id._BlNo, R.id._RowCd, R.id._ProdId, R.id._ProdNm,
                                R.id._WhsId, R.id._WhsNm, R.id._WaCfmSQty,R.id._ProdSpec,
                                R.id._CU_dengji, R.id._CU_mm,R.id._SQty,R.id.tvBatNo,R.id._kc});
        lvListData.setAdapter(adapter);
        lvListData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> map=(HashMap<String, String>)parent.getItemAtPosition(position);
                Intent intent=new Intent(OutGoingActivity.this,OutGoingDetailActivity.class);
                Bundle bundle=new Bundle();
                map.put("position", position + "");
                bundle.putSerializable("OutData", map);
                intent.putExtra("data", bundle);
                startActivityForResult(intent, 1);
            }
        });
        //---------------------初始化仓库数据-------------------------------------------
        /*
        ArrayList<String> wareList=new ArrayList<>();
        if(DataUtils.warehouseList.size()>0) {
            for (int k = 0; k < DataUtils.warehouseList.size(); k++) {
                wareList.add(DataUtils.warehouseList.get(k)[k][1]);
            }

            etStockID.setText(DataUtils.warehouseList.get(0)[0][0]);
            spwWhsNm.setData(wareList);
            spwWhsNm.setInput(wareList.get(0));
        }
        */
        //---------------------------------------------------------------------------------

        spwWhsNm.setOnItemClickListener(new SpinerPopWindow.OnPopWindowClickListener() {
            @Override
            public void onItemClick(int position, String txt) {
                for(int k=0;k<WhsList.size();k++){
                    if(txt.equals(WhsList.get(k).getWhsNm())) {
                        etStockID.setText(WhsList.get(k).getWhsId());
                    }
                }
            }

            @Override
            public void onInputFocusChange(boolean hasFocus) {

            }

            @Override
            public boolean onClick() {
                if(desList.size()>0) {
                    Toast.makeText(OutGoingActivity.this,"还有数据尚未上传不能更改仓库!",Toast.LENGTH_SHORT).show();
                    return  false;
                }
                return true;
            }
        });
        spwOutGingBill.setOnItemClickListener(new SpinerPopWindow.OnPopWindowClickListener() {
            @Override
            public void onItemClick(int position, String txt) {
                PDADownShpm_Entity entity= getCustInfo(txt);
                if(entity!=null){
                    etCustNm.setText(entity.getCustNm());
                    edCustId.setText(entity.getCustId());
                    etTypeId.setText(entity.getTypeId());
                }

            }

            @Override
            public void onInputFocusChange(boolean hasFocus) {

            }
            @Override
            public boolean onClick() {
                if(desList.size()>0) {
                    Toast.makeText(OutGoingActivity.this,"还有数据尚未上传不能切换单号!",Toast.LENGTH_SHORT).show();
                    return  false;
                }
                return true;
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //获取详细页面的回传数据
        switch(requestCode) {
            case 1:
                if(data==null)
                    return;
                Bundle bundle = data.getBundleExtra("data");
                if(bundle!=null) {
                    HashMap<String, String> map = (HashMap<String, String>) bundle.getSerializable("OutData");
                    int position = Integer.parseInt(map.get("position"));
                    String AlreadySH = map.get(PDADownShpm_Entity._SQty);
                    HashMap<String, String> map1 = desList.get(position);
                    map1.put(PDADownShpm_Entity._SQty, AlreadySH);
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSubmit:
            break;
        }
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
    //下载产品清单的数据
    public class DownListTask extends AsyncTask<String, Integer, Boolean> {
        ProgressDialog mypDialog;
        String msg;

        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub

            String[] result = OutGoing.getInstance().downPDADownShpmApl(pdaDownShpm_OutListData,pdaDown_PorListData);
            if (result[0].equals(ErrorManage.Error_Success)) {
                if (pdaDownShpm_OutListData.size() > 0 && pdaDown_PorListData.size()>0) {
                    return true;
                }
            } else {
                msg = result[1];
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            mypDialog.cancel();
            if (result) {
                initData();
                Toast.makeText(OutGoingActivity.this, "下载成功!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(OutGoingActivity.this, "下载发货单号失败!" + "(" + msg + ")", Toast.LENGTH_SHORT).show();
            }
            new downBaseInfoTask().execute();
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mypDialog = new ProgressDialog(OutGoingActivity.this);
            mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mypDialog.setMessage("正在下载发货单号...");
            mypDialog.setCanceledOnTouchOutside(false);
            mypDialog.setCancelable(false);
            mypDialog.show();
        }
    }



    private void initData(){
       if(pdaDownShpm_OutListData.size()>0) {//
           for(int k=0;k<pdaDownShpm_OutListData.size();k++){
               billList.add(pdaDownShpm_OutListData.get(k).getBlNo());
           }
           spwOutGingBill.setData(billList);
           spwOutGingBill.setInput(billList.get(0));

           PDADownShpm_Entity entity= getCustInfo(spwOutGingBill.getInput());
           if(entity!=null){
               etCustNm.setText(entity.getCustNm());
               edCustId.setText(entity.getCustId());
               etTypeId.setText(entity.getTypeId());
           }
       }
   }

    //根据清单获取产品信息
    private PDADownShpm_Entity getProInfo2(String bill,String ProID){
        for(int k=0;k<pdaDown_PorListData.size();k++){
            PDADownShpm_Entity entity=pdaDown_PorListData.get(k);
            if(entity.getProdId().equals(ProID) && entity.getBlNo().equals(bill))
                return  entity;
        }
        return null;
    }


    //获取当前清单的信息
    private PDADownShpm_Entity getCustInfo(String BlNo){
        for(int k=0;k<pdaDownShpm_OutListData.size();k++){
            PDADownShpm_Entity entity=pdaDownShpm_OutListData.get(k);
            if(entity.getBlNo().equals(BlNo))
                return  entity;
        }
        return null;
    }
    private void getPro(String var1){
        if(var1.isEmpty())
            return;
        for(int k=0;k<desList.size();k++){
            if(desList.get(k).get(PDADownShpm_Entity._ProdId).equals(var1)){
                Toast.makeText(OutGoingActivity.this,var1+"已经扫描!",Toast.LENGTH_SHORT).show();
                return;
            }
        }
        PDADownShpm_Entity proInfo2=getProInfo2(spwOutGingBill.getInput(),var1);
        String curBill= spwOutGingBill.getInput();
        if(proInfo2==null){
            Toast.makeText(OutGoingActivity.this,var1+",此条码产品信息不存在!",Toast.LENGTH_SHORT).show();
            return;
        }else  if(!proInfo2.getBlNo().equals(curBill)){
            Toast.makeText(OutGoingActivity.this,var1+",此条码产品信息不属于当前出货清单!",Toast.LENGTH_SHORT).show();
            return;
        }else {
            String proName =proInfo2.getProdNm();
            etProdId.setText(var1);
            etProdNm.setText(proName);
            new WaitTask(var1).execute();
        }
    }
    //上传数据
    public class WaitUPTask extends AsyncTask<String, Integer, Boolean> {
        ProgressDialog mypDialog;
        HashMap<String,String> map;
        ArrayList<HashMap<String,String>> list;
        String msg="";
        ArrayList<ProBar_Entity> proBar_Entity_OutListData =new ArrayList<ProBar_Entity>();
        public WaitUPTask( HashMap<String,String> map,ArrayList<HashMap<String,String>> list){
            this.map=map;
            this.list=list;
        }
        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub
            String[] str=OutGoing.getInstance().updateOutGoing(map, list);
            if (str[0].equals(ErrorManage.Error_Success)){
                return true;
            }else{
                msg=str[1];
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            mypDialog.cancel();
            if(result){
                removeBill(spwOutGingBill.getInput());
                initData();
                desList.clear();
                adapter.notifyDataSetChanged();
                Toast.makeText(OutGoingActivity.this,"提交数据成功!",Toast.LENGTH_SHORT).show();

            }else{

                Toast.makeText(OutGoingActivity.this,msg,Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mypDialog = new ProgressDialog(OutGoingActivity.this);
            mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mypDialog.setMessage("正在提交数据...");
            mypDialog.setCanceledOnTouchOutside(false);
            mypDialog.show();
        }
    }
    //移出已经上传的清单数据
    private void removeBill(String bill){
        for(int k=0;k<pdaDownShpm_OutListData.size();k++){
            if(pdaDownShpm_OutListData.get(k).getBlNo().equals(bill)){
                pdaDownShpm_OutListData.remove(k);
                break;
            }
        }
        /*
        int len=listOrdDet.size();
        for(int k=0;k<len;k++){
           if(listOrdDet.get(k).getBlNo().equals(bill)){
               listOrdDet.remove(k);
               k=k-1;
               len=len-1;
           }
        }
        */
        //---------初始化清单数据---------------------

        //----------------------------------------------------------------------
    }

    //查询数据，根据扫描条码 查询产品  以及查询批号
    public class WaitTask extends AsyncTask<String, Integer, Boolean> {
        ProgressDialog mypDialog;
        HashMap<String,String> map;
        String prodId="";
        String msg="";
        ArrayList<Batch_Entity> entities =new  ArrayList<Batch_Entity>();//批号
        ArrayList<ProBar_Entity> proBar_Entity_OutListData =new ArrayList<ProBar_Entity>();
        public WaitTask(String prodId){
            this.prodId=prodId;
        }
        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub
            String[] result = Batch.getInstance().PDADowGetBatNo(prodId,entities);
            if (result[0].equals(ErrorManage.Error_Success)) {
                String[] str=Incoming.getInstance().ProBar_EntityList(prodId, proBar_Entity_OutListData);
                if(str[0].equals(ErrorManage.Error_Success)){
                    return true;
                }else{
                    msg=str[1];
                    return false;
                }
            } else {
                msg = "查询批号失败,"+result[1];
                return false;
            }


        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            mypDialog.cancel();
            if(result){
                boolean flag=false;
                ProBar_Entity  proBar_entity= proBar_Entity_OutListData.get(0);
                etProdNm.setText(proBar_entity.getProdNm());
                etProdSpec.setText(proBar_entity.getProdSpec());
                etCU_dengji.setText(proBar_entity.getCU_dengji());
                etCU_mm.setText(proBar_entity.getCU_mm());
                PDADownShpm_Entity entity=  getProInfo2(spwOutGingBill.getInput(),prodId);
                ArrayList<Integer> list=new ArrayList<Integer>();
                for(int k=0;k<entities.size();k++){
                    if(entities.get(k).getWhsId().equals(etStockID.getText().toString())) {//同一个仓库才增加数据
                        flag=true;
                        map = new HashMap<String, String>();
                        map.put(strIndex, desList.size() + "");
                        list.add(desList.size());
                        map.put(PDADownShpm_Entity._BlNo, spwOutGingBill.getInput());
                        map.put(PDADownShpm_Entity._RowCd, entity.getRowCd() + "");
                        map.put(PDADownShpm_Entity._ProdId, prodId);
                        map.put(PDADownShpm_Entity._ProdNm, entity.getProdNm());
                        map.put(PDADownShpm_Entity._WhsId, etStockID.getText().toString());
                        map.put(PDADownShpm_Entity._WhsNm, spwWhsNm.getInput());
                        map.put(PDADownShpm_Entity._WaCfmSQty, entity.getWaCfmSQty() + "");

                        map.put(PDADownShpm_Entity._ProdSpec, proBar_entity.getProdSpec());
                        map.put(PDADownShpm_Entity._CU_dengji, proBar_entity.getCU_dengji());
                        map.put(PDADownShpm_Entity._CU_mm, proBar_entity.getCU_mm());
                        map.put(PDADownShpm_Entity._SQty, "0");
                        map.put(Batch_Entity._BatNo, entities.get(k).getBatNo());
                        map.put(Batch_Entity._StkStId, entities.get(k).getStkStId());
                        map.put(Batch_Entity._StkBiza, entities.get(k).getStkBiza() + "");
                        map.put(Batch_Entity._Qty, entities.get(k).getQty() + "");
                        desList.add(map);
                    }
                }

                adapter.notifyDataSetChanged();
                if(flag)
                    Toast.makeText(OutGoingActivity.this,"查询成功!",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(OutGoingActivity.this,"查询成功,此仓库没有库存!",Toast.LENGTH_SHORT).show();
                if(list.size()>1) {
                    Message msg = Message.obtain();
                    msg.obj = list;
                    msg.what = 1;
                    handler.sendMessage(msg);
                }
            }else{
                Toast.makeText(OutGoingActivity.this,msg,Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mypDialog = new ProgressDialog(OutGoingActivity.this);
            mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mypDialog.setMessage("正在查询数据...");
            mypDialog.setCanceledOnTouchOutside(false);
            mypDialog.show();
        }
    }

    private class ScanCallBack implements com.example.stockmanage.biz.IScanCallBack{
        @Override
        public void onScanResults(String var1) {
            if(var1.startsWith("S2017")||var1.startsWith("S2018")){
                if(desList.size()>0) {
                  //  Toast.makeText(IncomingActivity.this,"还有数据尚未上传不能切换单号!",Toast.LENGTH_SHORT).show();
                    return;
                }
                PDADownShpm_Entity pdaDownShpm_entity= getCustInfo(var1);
                if(pdaDownShpm_entity==null){
                    spwOutGingBill.setInput("");
                    Toast.makeText(OutGoingActivity.this,var1+",产品清单不存在!",Toast.LENGTH_SHORT).show();
                }else{
                    spwOutGingBill.setInput(var1);
                }
            }else{
                getPro(var1);
            }
        }
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    ArrayList<Integer> list=(ArrayList<Integer>) msg.obj;
                    showDialog(list);
                    break;
            }
        }
    };
    HashMap<String,String> hashMap=null;
    ArrayList<Integer> list2;
    private void showDialog( ArrayList<Integer> list){
        final View view = LayoutInflater.from(this).inflate(R.layout.dialog_outgoing_veiw, null);
        hashMap= desList.get(list.get(0));
        list2=list;
        ((EditText)view.findViewById(R.id.etBatchNumber_outveiw)).setText(hashMap.get(Batch_Entity._BatNo));
        ((EditText)view.findViewById(R.id.etBomName_outveiw)).setText(hashMap.get(PDADownShpm_Entity._ProdNm));
        ((EditText)view.findViewById(R.id.etWFNumber_outveiw)).setText(hashMap.get(PDADownShpm_Entity._WaCfmSQty));
        ((EditText)view.findViewById(R.id.etkc)).setText(hashMap.get(Batch_Entity._Qty));
        view.findViewById(R.id.etNumber_outveiw).requestFocus();



        final AlertDialog dialog = new AlertDialog.Builder(this).setView(view).setTitle("发货数量确认").setPositiveButton("确定", null)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(list2.size()>1) {
                            list2.remove(0);
                            Message msg = Message.obtain();
                            msg.obj = list2;
                            msg.what = 1;
                            handler.sendMessage(msg);
                        }
                    }
                }).setNeutralButton("全部取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();
        //这里必须要先调show()方法，后面的getButton才有效
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText Number = (EditText) view.findViewById(R.id.etNumber_outveiw);//要用里面这个控件，记得加上view
                String strNum=Number.getText().toString();
                if(strNum==null || strNum.isEmpty()){
                    Toast.makeText(OutGoingActivity.this,"发货数量不能位空!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Double.parseDouble(strNum)>Double.parseDouble(hashMap.get(PDADownShpm_Entity._WaCfmSQty))){
                    Toast.makeText(OutGoingActivity.this,"发货数量不能大于未发货数量!",Toast.LENGTH_SHORT).show();
                    return;
                }
                hashMap.put(PDADownShpm_Entity._SQty, Number.getText().toString());
                adapter.notifyDataSetChanged();
                if (list2.size() > 1) {
                    list2.remove(0);
                    Message msg = Message.obtain();
                    msg.obj = list2;
                    msg.what = 1;
                    handler.sendMessage(msg);
                }
                dialog.dismiss();
            }
        });
    }


    public class downBaseInfoTask extends AsyncTask<String, Integer, Boolean> {
        ProgressDialog mypDialog;
        String msg[];

        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub
            msg= BaseInfo.getInstance().PDADownWhs(WhsList);
            return  false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            mypDialog.cancel();
            if(msg[0].equals(ErrorManage.Error_Success)){
                if(WhsList.size()>0) {
                    ArrayList<String> list=new ArrayList<String>();
                    for(int k=0;k<WhsList.size();k++){
                        list.add(WhsList.get(k).getWhsNm());
                    }
                    spwWhsNm.setData(list);
                    spwWhsNm.setInput(list.get(0));
                    for(int k=0;k<WhsList.size();k++){
                        if(list.get(0).equals(WhsList.get(k).getWhsNm())) {
                            etStockID.setText(WhsList.get(k).getWhsId());
                        }
                    }
                }
                //----------------------------------------------------------------------
            }else{
                Toast.makeText(OutGoingActivity.this,"仓库数据失败!"+"("+msg[1]+")",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mypDialog = new ProgressDialog(OutGoingActivity.this);
            mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mypDialog.setMessage("正在下载仓库信息...");
            mypDialog.setCanceledOnTouchOutside(false);
            mypDialog.setCancelable(false);
            mypDialog.show();
        }
    }
}

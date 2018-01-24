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
import com.example.stockmanage.biz.Incoming;
import com.example.stockmanage.entity.IncomingEntity;
import com.example.stockmanage.entity.Incoming_purRecvOrdDet_Entity;
import com.example.stockmanage.entity.Incoming_purRecvOrd_Entity;
import com.example.stockmanage.entity.ProBar_Entity;
import com.example.stockmanage.entity.Warehouse_Entity;
import com.example.stockmanage.util.ErrorManage;
import com.example.stockmanage.view.SpinerPopWindow;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 收货
 */
public class IncomingActivity extends BaseActivity implements View.OnClickListener {
    BillOnItemClick billOnItemClick= new BillOnItemClick();
    SpinerPopWindow spwIncomingBill;
    SpinerPopWindow spwIncomingStock;//仓库
    ArrayList<Warehouse_Entity> WhsList=new ArrayList<Warehouse_Entity>();
    private HashMap<String,String> map=null;
    private ArrayList<HashMap<String, String>> desList=new ArrayList<HashMap<String, String>>();;
    Button submit2;//bom明细
    Button submit;//提交
    EditText etStockID;//仓库ID
    EditText etSupplier;//供应商
    EditText etBarcode;//条码
    EditText etBOM;//物料
    EditText etSpecification;//规格
    EditText etLevel;//等级
    EditText etWide;//宽幅
    EditText ProdNm;//产品名称
    EditText etSupplierID;//供应商代码
    ListView lvListData;//物料说明
    SimpleAdapter   adapter;
    ScanCallBack scanCallBack=null;
    ArrayList<Incoming_purRecvOrd_Entity> listOrd=new  ArrayList<Incoming_purRecvOrd_Entity>();
    ArrayList<Incoming_purRecvOrdDet_Entity> listOrdDet=new  ArrayList<Incoming_purRecvOrdDet_Entity>();
    ArrayList<String> BlNolist=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        exitNotify=true;
        setContentView(R.layout.activity_incoming);
        init();
    }
    private void init(){
          setTitle("采购收货");
        //-----------------------------------------------
        spwIncomingBill=(SpinerPopWindow)findViewById(R.id.spwIncomingBill);
        spwIncomingStock=(SpinerPopWindow)findViewById(R.id.spwIncomingStock);
        etSupplierID=(EditText)findViewById(R.id.etSupplierID);
        ProdNm=(EditText)findViewById(R.id.ProdNm);
        submit=(Button)findViewById(R.id.submit);
        etStockID=(EditText)findViewById(R.id.etStockID);
        etSupplier=(EditText)findViewById(R.id.etSupplier);
        lvListData=(ListView) findViewById(R.id.lvListData);
         etBOM=(EditText)findViewById(R.id.etBOM);//物料
          etSpecification=(EditText)findViewById(R.id.etSpecification);//规格
          etLevel=(EditText)findViewById(R.id.etLevel);//等级
          etWide=(EditText)findViewById(R.id.etWide);//宽幅
        etBarcode=(EditText)findViewById(R.id.etBarcode);//产品iD
        etBarcode.requestFocus();
        etBarcode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                getPro(etBarcode.getText().toString());
               // etBarcode.requestFocus();

            }
        });
        spwIncomingStock.setInputEnable(false);
        spwIncomingBill.setInputEnable(false);
        etSupplier.setEnabled(false);
        //---------------------------------------------------
        adapter = new SimpleAdapter(this, desList, R.layout.incoming_list_item,
                new String[] { "tvProID","index", "PNam", "notSH", "AlreadySH","Specification","Level","Wide","RowCd" },
                new int[] { R.id.tvProID,R.id.tvIndex, R.id.tvName, R.id.tvWS, R.id.tvYS, R.id.tvSpecification, R.id.tvLevel, R.id.tvWide,R.id.tvRowCd });
            lvListData.setAdapter(adapter);
            lvListData.setOnItemClickListener(new MyOnItemClickListener());
        //----------------------------------------------------
        spwIncomingBill.setOnItemClickListener(billOnItemClick);
        //-------------------------------------------------
        submit.setOnClickListener(this);
        scanCallBack=new ScanCallBack();

        new PDADownRecOrdListTask().execute();
        //---------------------------------------------------------------
//        listOrd=Incoming.getInstance().selectIncoming_RecvOrdALL();//查询清单信息
//        listOrdDet=Incoming.getInstance().selectIncoming_RecvOrdDetALL();


        //---------------------初始化仓库数据-------------------------------------------
        /*
        ArrayList<String> wareList=new ArrayList<>();
        if(DataUtils.warehouseList.size()>0) {
            for (int k = 0; k < DataUtils.warehouseList.size(); k++) {
                wareList.add(DataUtils.warehouseList.get(k)[k][1]);
            }
            etStockID.setText(DataUtils.warehouseList.get(0)[0][0]);
            spwIncomingStock.setData(wareList);
            spwIncomingStock.setInput(wareList.get(0));
        }
        */
        spwIncomingStock.setOnItemClickListener(new SpinerPopWindow.OnPopWindowClickListener() {
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
                    Toast.makeText(IncomingActivity.this,"还有数据尚未上传不能更改仓库!",Toast.LENGTH_SHORT).show();
                    return  false;
                }
                return true;
            }
        });
    }

    //提交
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit:
                upLoading();
                break;

        }
    }

    private void upLoading(){
        if(etSupplier.getText().toString().isEmpty()){
            Toast.makeText(IncomingActivity.this,"供应商不能为空!",Toast.LENGTH_SHORT).show();

            return  ;
        }
        ArrayList<HashMap<String,String>> listTable1=new ArrayList<HashMap<String, String>>();
        HashMap<String,String> map_table0=new HashMap<String,String>();
        map_table0.put(IncomingEntity._BlNo,spwIncomingBill.getInput());
        map_table0.put(IncomingEntity._SupId,etSupplierID.getText().toString());
        map_table0.put(IncomingEntity._SupNm,etSupplier.getText().toString());
        map_table0.put(IncomingEntity._WhsId,etStockID.getText().toString());
        map_table0.put(IncomingEntity._WhsNm,spwIncomingStock.getInput());
        map_table0.put(IncomingEntity._TypeId,etStockID.getText().toString());

        for(int k=0;k<desList.size();k++){
            HashMap<String,String> map=new HashMap<String,String>();
            map.put(IncomingEntity._BlNo,spwIncomingBill.getInput());
            map.put(IncomingEntity._RowCd,desList.get(k).get("RowCd"));
            map.put(IncomingEntity._ProdId,desList.get(k).get("tvProID"));
            map.put(IncomingEntity._ProdNm,desList.get(k).get("PNam"));
            map.put(IncomingEntity._WhsId,etStockID.getText().toString());
            map.put(IncomingEntity._WhsNm,spwIncomingStock.getInput());
            map.put(IncomingEntity._RecvSQty,desList.get(k).get("AlreadySH"));
            map.put(IncomingEntity._ProdSpec,desList.get(k).get("Specification"));
            map.put(IncomingEntity._CU_dengji,desList.get(k).get("Level"));
            map.put(IncomingEntity._CU_mm,desList.get(k).get("Wide"));
            listTable1.add(map);
            if(desList.get(k).get("AlreadySH").equals("0")){
                Toast.makeText(this,"物料:"+desList.get(k).get("PNam")+"没有完成收货，收货数量为0!",Toast.LENGTH_SHORT).show();

                return  ;
            }
        }
        new WaitUPTask(map_table0,listTable1).execute();
        return ;
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
            if(var1.startsWith("2017")||var1.startsWith("2018")){
                if(desList.size()>0) {
                    Toast.makeText(IncomingActivity.this,"还有数据尚未上传不能切换单号!",Toast.LENGTH_SHORT).show();
                    return;
                }
                getBill(var1);
            }else{
                getPro(var1);
            }
        }
    }
    private void getPro(String var1){
        if(var1.isEmpty())
            return;
        /*
        for(int k=0;k<desList.size();k++){
            if(desList.get(k).get("tvProID").equals(var1)){
                Toast.makeText(IncomingActivity.this,var1+"已经扫描!",Toast.LENGTH_SHORT).show();
                return;
            }
        }
        */
        String curBill= spwIncomingBill.getInput();
        Incoming_purRecvOrdDet_Entity incoming_purRecvOrdDet_entity=getProdInfo(curBill,var1);
        if(incoming_purRecvOrdDet_entity==null){
            Toast.makeText(IncomingActivity.this,var1+",此条码产品信息不存在!",Toast.LENGTH_SHORT).show();
            return;
        }else  if(!incoming_purRecvOrdDet_entity.getBlNo().equals(curBill)){
            Toast.makeText(IncomingActivity.this,var1+",此条码产品信息不属于当前进货清单!",Toast.LENGTH_SHORT).show();
            return;
        }else {
            String proName =incoming_purRecvOrdDet_entity.getProdNm();
            etBarcode.requestFocus();
            etBarcode.setText(var1);
            ProdNm.setText(proName);
            new WaitTask(var1).execute();
        }
    }
    private  void getBill(String var1){
        if(var1.isEmpty())
            return;
        for(int k=0;k<listOrd.size();k++){
            if(listOrd.get(k).getBlNo().equals(var1)){
                spwIncomingBill.setInput(var1);
                spwIncomingBill.getInputEditText().requestFocus();
                return;
            }
        }
        spwIncomingBill.setInput("");
        Toast.makeText(IncomingActivity.this,var1+",产品清单不存在!",Toast.LENGTH_SHORT).show();
    }
    //根据清单获取产品信息 和 供应商
    private void showProInfo(String BlNo){
        for(int k=0;k<listOrd.size();k++){
            Incoming_purRecvOrd_Entity entity=listOrd.get(k);
            if(entity.getBlNo().equals(BlNo)){
                etSupplier.setText(entity.getSupNm());
                etSupplierID.setText(entity.getSupId());
            }
        }
    }
    //根据产品ID获取产品信息
    private Incoming_purRecvOrdDet_Entity getProdInfo(String bill,String proID){
        for(int k=0;k<listOrdDet.size();k++){
            Incoming_purRecvOrdDet_Entity entity=listOrdDet.get(k);
            if((entity.getProdId().equals(proID)) && (entity.getBlNo().equals(bill))){
               return  entity;
            }
        }
        return null;
    }

    //点击产品清单监听事件
    private class BillOnItemClick implements  SpinerPopWindow.OnPopWindowClickListener{
        public  boolean isCheak=false;
        public Object object1=new Object();
        @Override
        public void onItemClick(int position, String txt) {
            showProInfo(txt);
        }

        @Override
        public void onInputFocusChange(boolean hasFocus) {
            String txt=spwIncomingBill.getInput();
            if(!txt.isEmpty()){
                for(int k=0;k<listOrd.size();k++){
                    if(listOrd.get(k).getBlNo().equals(txt)){
                        return;
                    }
                }
                spwIncomingBill.setInput("");
                Toast.makeText(IncomingActivity.this,"产品清单不存在!",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public boolean onClick() {

            if(desList.size()>0) {
                Toast.makeText(IncomingActivity.this,"还有数据尚未上传不能切换单号!",Toast.LENGTH_SHORT).show();
                return  false;
            }
            return true;
        }

    }

   //查询数据
    public class WaitTask extends AsyncTask<String, Integer, Boolean> {
        ProgressDialog mypDialog;
        String prodId="";
        String msg="";
        ArrayList<ProBar_Entity> proBar_Entity_OutListData =new ArrayList<ProBar_Entity>();
        public WaitTask(String prodId){
            this.prodId=prodId;
        }
        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub
            String[] str=Incoming.getInstance().ProBar_EntityList(prodId, proBar_Entity_OutListData);
            if(str[0].equals(ErrorManage.Error_Success)){
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
                 ArrayList<Integer> indexLis=new ArrayList<Integer>();
                for(int k=0;k<proBar_Entity_OutListData.size();k++){
                    ProBar_Entity  proBar_entity= proBar_Entity_OutListData.get(k);
                    map=new HashMap<String,String>();
                    map.put("tvProID",prodId);
                    map.put("index",desList.size()+"");
                    map.put("PNam",proBar_entity.getProdNm());
                    map.put("notSH",getProdInfo(spwIncomingBill.getInput(),prodId).getWaCfmSQty()+"");
                    map.put("AlreadySH", "0");
                    map.put("Specification",proBar_entity.getProdSpec());
                    map.put("Level",proBar_entity.getCU_dengji());
                    map.put("Wide",proBar_entity.getCU_mm());
                    map.put("RowCd",getProdInfo(spwIncomingBill.getInput(),prodId).getRowCd()+"");
                    indexLis.add(desList.size());
                    desList.add(map);

                    etBOM.setText(proBar_entity.getProdNm());
                    etSpecification.setText(proBar_entity.getProdSpec());
                    etLevel.setText(proBar_entity.getCU_dengji());
                    etWide.setText(proBar_entity.getCU_mm());
                }

                adapter.notifyDataSetChanged();
                Toast.makeText(IncomingActivity.this,"查询成功!",Toast.LENGTH_SHORT).show();
                if(indexLis.size()>0) {
                    Message msg = Message.obtain();
                    msg.obj = indexLis;
                    msg.what = 1;
                    handler.sendMessage(msg);
                }
            }else{
                Toast.makeText(IncomingActivity.this,msg,Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mypDialog = new ProgressDialog(IncomingActivity.this);
            mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mypDialog.setMessage("正在查询数据...");
            mypDialog.setCanceledOnTouchOutside(false);
            mypDialog.show();
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
            String[] str=Incoming.getInstance().updateIncoming(map,list);
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
                removeBill(spwIncomingBill.getInput());
                initBill();
                desList.clear();
                adapter.notifyDataSetChanged();
                Toast.makeText(IncomingActivity.this,"提交数据成功!",Toast.LENGTH_SHORT).show();

            }else{

                Toast.makeText(IncomingActivity.this,msg,Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mypDialog = new ProgressDialog(IncomingActivity.this);
            mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mypDialog.setMessage("正在提交数据...");
            mypDialog.setCanceledOnTouchOutside(false);
            mypDialog.show();
        }
    }

    private class MyOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            HashMap<String, String> map=(HashMap<String, String>)parent.getItemAtPosition(position);
            Intent intent=new Intent(IncomingActivity.this,IncomingDetailedActivity.class);
            Bundle bundle=new Bundle();
            map.put("position", position + "");
            bundle.putSerializable("IncomData", map);
            intent.putExtra("data", bundle);
            startActivityForResult(intent, 1);
        }
    }
    private  void  initBill(){
        if(listOrd.size()>0) {
            for(int k=0;k<listOrd.size();k++){
                BlNolist.add(listOrd.get(k).getBlNo());//获取清单数据
            }
            spwIncomingBill.setData(BlNolist);
            spwIncomingBill.setInput(BlNolist.get(0));
            showProInfo(BlNolist.get(0));
        }
    }
    //移出已经上传的清单数据
    private void removeBill(String bill){
        for(int k=0;k<listOrd.size();k++){
            if(listOrd.get(k).getBlNo().equals(bill)){
                listOrd.remove(k);
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
        initBill();
        //----------------------------------------------------------------------
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
                   HashMap<String, String> map = (HashMap<String, String>) bundle.getSerializable("IncomData");
                   int position = Integer.parseInt(map.get("position"));
                   String AlreadySH = map.get("AlreadySH");
                   HashMap<String, String> map1 = desList.get(position);
                   map1.put("AlreadySH", AlreadySH);
                   adapter.notifyDataSetChanged();
               }
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
            String[] result= incoming.pdpDownRecOrdList2(listOrd, listOrdDet);
            if(result[0].equals(ErrorManage.Error_Success)){
                if(listOrd.size()>0 && listOrdDet.size()>0){
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
              //  Toast.makeText(IncomingActivity.this,"下载成功!",Toast.LENGTH_SHORT).show();
                //---------初始化清单数据---------------------
                if(listOrd.size()>0) {
                    for(int k=0;k<listOrd.size();k++){
                        BlNolist.add(listOrd.get(k).getBlNo());//获取清单数据
                    }
                    spwIncomingBill.setData(BlNolist);
                    spwIncomingBill.setInput(BlNolist.get(0));
                    showProInfo(BlNolist.get(0));
                }
                //----------------------------------------------------------------------
            }else{
                Toast.makeText(IncomingActivity.this,"下载收货清单失败!"+"("+msg+")",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mypDialog = new ProgressDialog(IncomingActivity.this);
            mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mypDialog.setMessage("正在下载收货清单...");
            mypDialog.setCanceledOnTouchOutside(false);
            mypDialog.setCancelable(false);
            mypDialog.show();
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
        final View view = LayoutInflater.from(this).inflate(R.layout.dialog_veiw, null);
           hashMap= desList.get(list.get(0));
           list2=list;
        ((EditText)view.findViewById(R.id.etBomName)).setText(hashMap.get("PNam"));
        ((EditText)view.findViewById(R.id.etWSNumber)).setText(hashMap.get("notSH"));
        view.findViewById(R.id.etNumber).requestFocus();
        final AlertDialog dialog = new AlertDialog.Builder(this).setView(view).setTitle("收货数量确认").setPositiveButton("确定", null)
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
                EditText Number = (EditText) view.findViewById(R.id.etNumber);//要用里面这个控件，记得加上view
                String strNum=Number.getText().toString();
                if(strNum==null || strNum.isEmpty()){
                    Toast.makeText(IncomingActivity.this,"收货数量不能位空!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Double.parseDouble(strNum)>Double.parseDouble(hashMap.get("notSH"))){
                    Toast.makeText(IncomingActivity.this,"收货数量不能大于未收货数量!",Toast.LENGTH_SHORT).show();
                    return;
                }
                hashMap.put("AlreadySH", Number.getText().toString());
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
                    spwIncomingStock.setData(list);
                    spwIncomingStock.setInput(list.get(0));
                    for(int k=0;k<WhsList.size();k++){
                        if(list.get(0).equals(WhsList.get(k).getWhsNm())) {
                            etStockID.setText(WhsList.get(k).getWhsId());
                        }
                    }
                }
                //----------------------------------------------------------------------
            }else{
                Toast.makeText(IncomingActivity.this,"仓库数据失败!"+"("+msg[1]+")",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mypDialog = new ProgressDialog(IncomingActivity.this);
            mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mypDialog.setMessage("正在下载仓库信息...");
            mypDialog.setCanceledOnTouchOutside(false);
            mypDialog.setCancelable(false);
            mypDialog.show();
        }
    }
}

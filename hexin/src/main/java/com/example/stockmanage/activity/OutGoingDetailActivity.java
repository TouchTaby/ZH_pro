package com.example.stockmanage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stockmanage.R;
import com.example.stockmanage.entity.Batch_Entity;
import com.example.stockmanage.entity.PDADownShpm_Entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
/**
 * 出货
 */
public class OutGoingDetailActivity extends BaseActivity {

    EditText ProdNm;
    EditText SQty;
    EditText WaCfmSQty;
    EditText WhsNm;
    EditText ProdSpec;
    EditText CU_dengji;
    EditText CU_mm;
    EditText etBatchNumber;
    EditText kcNumb;
    Button btSubit;
    HashMap<String,String >map=new HashMap<String, String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_going_detail);
        setTitle("销售出货明细");
        ProdNm = (EditText) findViewById(R.id.ProdNm);
        SQty = (EditText) findViewById(R.id.SQty);
        WaCfmSQty = (EditText) findViewById(R.id.WaCfmSQty);
        WhsNm = (EditText) findViewById(R.id.WhsNm);
        ProdSpec = (EditText) findViewById(R.id.ProdSpec);
        CU_dengji = (EditText) findViewById(R.id.CU_dengji);
        CU_mm = (EditText) findViewById(R.id.CU_mm);
        etBatchNumber = (EditText) findViewById(R.id.etBatchNumber);
        kcNumb= (EditText) findViewById(R.id.kcNumb);
        btSubit = (Button) findViewById(R.id.btSubit);


        final Bundle bundle= getIntent().getBundleExtra("data");
        map=(HashMap<String,String>) bundle.getSerializable("OutData");
        ProdNm.setText(map.get(PDADownShpm_Entity._ProdNm));
        SQty.setText(map.get(PDADownShpm_Entity._SQty));
        WaCfmSQty.setText(map.get(PDADownShpm_Entity._WaCfmSQty));
        WhsNm.setText(map.get(PDADownShpm_Entity._WhsNm));
        ProdSpec.setText(map.get(PDADownShpm_Entity._ProdSpec));
        CU_dengji.setText(map.get(PDADownShpm_Entity._CU_dengji));
        CU_mm.setText(map.get(PDADownShpm_Entity._CU_mm));
        etBatchNumber.setText(map.get(Batch_Entity._BatNo));
        kcNumb.setText(map.get(Batch_Entity._Qty));
        btSubit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str=SQty.getText().toString();
                if(str.isEmpty()){
                    Toast.makeText(OutGoingDetailActivity.this, "出货数量不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Double.parseDouble(str)==0){
                    Toast.makeText(OutGoingDetailActivity.this,"出货数量必须大于0!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Double.parseDouble(str)>Double.parseDouble(WaCfmSQty.getText().toString())){
                    Toast.makeText(OutGoingDetailActivity.this,"出货数量不能大于未出货数量!",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent=new Intent();
                Bundle bundleResult=new Bundle();
                map.put(PDADownShpm_Entity._SQty,SQty.getText().toString());
                bundleResult.putSerializable("OutData",map);
                intent.putExtra("data",bundleResult);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
    //获取当前时间
    private static String getNowDateTime() {
        String strFormat = "yyyyMMdd";
        Date now = new Date();
        SimpleDateFormat df = new SimpleDateFormat(strFormat);//设置日期格式
        return df.format(now); // new Date()为获取当前系统时间
    }
}

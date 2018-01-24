package com.example.stockmanage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stockmanage.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
/**
 * 收货
 */
public class IncomingDetailedActivity extends BaseActivity {

    HashMap<String,String >map=new HashMap<String, String>();
    EditText etBOM;//物料名称
    EditText etGoodsNum;//已经收货数量
    EditText etNotGoodsNum;//未来收货数量
    EditText etBatchNumber;//批号
    EditText etSpecification;//规格
    EditText etLevel;//等级
    EditText etWide;//宽幅
    Button btOK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_bomdetailed);
           setTitle("采购收货明细");
          etBOM=(EditText)findViewById(R.id.etBOM);//物料名称
          etGoodsNum=(EditText)findViewById(R.id.etGoodsNum);;//已经收货数量
          etNotGoodsNum=(EditText)findViewById(R.id.etNotGoodsNum);;//未来收货数量
          etBatchNumber=(EditText)findViewById(R.id.etBatchNumber);;//屁孩
          etSpecification=(EditText)findViewById(R.id.etSpecification);;//规格
          etLevel=(EditText)findViewById(R.id.etLevel);;//等级
          etWide=(EditText)findViewById(R.id.etWide);;//宽幅
          btOK=(Button)findViewById(R.id.btOK);;//宽幅
          etGoodsNum.requestFocus();

         final Bundle bundle= getIntent().getBundleExtra("data");
         map=(HashMap<String,String>) bundle.getSerializable("IncomData");
        etBOM.setText(map.get("PNam"));
        etGoodsNum.setText(map.get("AlreadySH"));
        etNotGoodsNum.setText(map.get("notSH"));
        etSpecification.setText(map.get("Specification"));
        etLevel.setText(map.get("Level"));
        etWide.setText(map.get("Wide"));
        etBatchNumber.setText(getNowDateTime().substring(2));
        btOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str=etGoodsNum.getText().toString();
                if(str.isEmpty()){
                    Toast.makeText(IncomingDetailedActivity.this,"收货数量不能为空!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Double.parseDouble(str)==0){
                    Toast.makeText(IncomingDetailedActivity.this,"收货数量必须大于0!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Double.parseDouble(str)>Double.parseDouble(etNotGoodsNum.getText().toString())){
                    Toast.makeText(IncomingDetailedActivity.this,"收货数量不能大于未收货数量!",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent=new Intent();
                Bundle bundleResult=new Bundle();
                map.put("AlreadySH",etGoodsNum.getText().toString());
                bundleResult.putSerializable("IncomData",map);
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

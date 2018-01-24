package com.example.stockmanage.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stockmanage.AppContext;
import com.example.stockmanage.R;
import com.example.stockmanage.net.HttpUtils;
import com.example.stockmanage.util.Common;

public class ConfigActivity extends BaseActivity{
    Button setIP;
    EditText edIP;
    Button setZT;
    EditText edZT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

         setIP= (Button) findViewById(R.id.setIP);
        setZT= (Button) findViewById(R.id.setZT);
         edIP= (EditText) findViewById(R.id.edIP);
        edZT= (EditText) findViewById(R.id.edZT);
         edIP.setText(Common.getIP(AppContext.context));
        edZT.setText(Common.getZT(AppContext.context));
         setIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edIP.getText().toString().isEmpty()){
                    Toast.makeText(ConfigActivity.this,"地址不能为空!",Toast.LENGTH_LONG).show();
                    return;
                }
                Common.setIP(ConfigActivity.this,edIP.getText().toString());
                Toast.makeText(ConfigActivity.this,"OK!",Toast.LENGTH_LONG).show();

            }
        });

        setZT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edZT.getText().toString().isEmpty()){
                    Toast.makeText(ConfigActivity.this,"账套不能为空!",Toast.LENGTH_LONG).show();
                    return;
                }
                Common.setZT(ConfigActivity.this,edZT.getText().toString());
                Toast.makeText(ConfigActivity.this,"OK!",Toast.LENGTH_LONG).show();
            }
        });
    }
}

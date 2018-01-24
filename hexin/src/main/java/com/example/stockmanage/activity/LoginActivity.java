package com.example.stockmanage.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stockmanage.R;
import com.example.stockmanage.biz.Barcode2D;
import com.example.stockmanage.biz.Login;
import com.example.stockmanage.net.HttpUtils;
import com.example.stockmanage.net.NetUtils;
import com.example.stockmanage.util.Common;
import com.example.stockmanage.util.ErrorManage;

public class LoginActivity extends AppCompatActivity {
    Button bt_login;
    Button tvSet;
    EditText et_name_login;
    EditText et_psw_login;
    CheckBox cbUserName;
    String TAG="LoginActivity";
    String name="";
    String passWord="";


    Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(LoginActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG,"onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {

        et_name_login= (EditText) findViewById(R.id.et_name_login);
        et_psw_login= (EditText) findViewById(R.id.et_psw_login);

        cbUserName= (CheckBox) findViewById(R.id.cbUserName);
        cbUserName.setChecked(true);

        et_name_login.setText(Common.getLoginName(this));
       // et_psw_login.setText(Common.getLoginPassWord(this));

        bt_login = (Button) findViewById(R.id.bt_login);
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name=et_name_login.getText().toString();
                passWord=et_psw_login.getText().toString();
                if(name.isEmpty() || passWord.isEmpty()){
                    Toast.makeText(LoginActivity.this,"用户名和密码不能为空!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!NetUtils.isNetworkAvailable(LoginActivity.this)){
                    Toast.makeText(LoginActivity.this,"请检查当前网络!",Toast.LENGTH_SHORT).show();
                    return;
                }
                new LoinTask().execute();
            }
        });
        tvSet= (Button) findViewById(R.id.tvSet);
        tvSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,ConfigActivity.class);
                startActivity(intent);
            }
        });

    }

    public class LoinTask extends AsyncTask<String, Integer, Boolean> {
        ProgressDialog mypDialog;
        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub
            if(name.isEmpty() || passWord.isEmpty())
                return false;
            String result= new Login().loginSystem(name,passWord,LoginActivity.this);
            if(result.equals(ErrorManage.Error_Success)){
                return  true;
            }else{
                Message msg=Message.obtain();
                msg.obj="登录失败!("+result+")";
                handler.sendMessage(msg);
                return  false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            mypDialog.cancel();
            if(result){
                login();
            }
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mypDialog = new ProgressDialog(LoginActivity.this);
            mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mypDialog.setMessage("登录，正在验证...");
            mypDialog.setCanceledOnTouchOutside(false);
            mypDialog.show();
        }
    }


    private void login(){
        if(cbUserName.isChecked()) {
            Common.setLoginName(LoginActivity.this, name);
            Common.setLoginPassWord(LoginActivity.this, passWord);
        }else{
            Common.setLoginName(LoginActivity.this, "");
            Common.setLoginPassWord(LoginActivity.this, "");
        }
        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==4){
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG,"onDestroy");
        super.onDestroy();
        android.os.Process.killProcess(android.os.Process.myPid());// System.exit(0);
    }
}

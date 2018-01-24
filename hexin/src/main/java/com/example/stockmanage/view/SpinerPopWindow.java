package com.example.stockmanage.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.stockmanage.R;

import java.util.List;

/**
 * Created by Administrator on 2017-09-17.
 */
public class SpinerPopWindow extends LinearLayout {

    private Context context;
    private ListView listView;
    private EditText input;
    private ImageView downArrow;
    private ArrayAdapter adapter;
    private PopupWindow popWin;
    private  OnPopWindowClickListener onItemClickListener=null;

    public SpinerPopWindow(Context context) {
        super(context);
    }

    public SpinerPopWindow(final Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        LayoutInflater.from(context).inflate(R.layout.spiner_pop_veiw, this);
        input=(EditText)findViewById(R.id.input);
        downArrow = (ImageView) findViewById(R.id.down_arrow);
        initListView();
        downArrow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null) {
                    if(!onItemClickListener.onClick()){
                        return;
                    }
                }
                //定义 popupWindow
                popWin = new PopupWindow(context);
                popWin.setWidth(input.getWidth()); //设置宽度
                popWin.setHeight(200);    //设置popWin 高度
                popWin.setContentView(listView); //为popWindow填充内容
                popWin.setOutsideTouchable(true); // 点击popWin 以处的区域，自动关闭 popWin
                popWin.showAsDropDown(input, 0, 0);//设置 弹出窗口，显示的位置
            }
        });
        input.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(onItemClickListener!=null){
                    onItemClickListener.onInputFocusChange(hasFocus);
                }
            }
        });
    }
    private void initListView() {
        adapter=new ArrayAdapter(context,R.layout.list_spiner_pop_item,R.id.tv_list_item);
        listView = new ListView(context);
        //listView.setBackgroundResource(R.drawable); //设置listView 背景
        listView.setDivider(new ColorDrawable(Color.GRAY));	//设置条目之间的分隔线为null
        listView.setDividerHeight(2);
        listView.setVerticalScrollBarEnabled(false); // 关闭
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                input.setText(adapter.getItem(position).toString());
                if(onItemClickListener!=null){
                    onItemClickListener.onItemClick(position,adapter.getItem(position).toString());
                }
                popWin.dismiss();
            }
        });
    }
    public String getInput(){
        if(input!=null)
           return  input.getText().toString();
        else
          return "";
    }
    public void setInput(String strData){
        if(input!=null)
            input.setText(strData);
    }
    public void setInputEnable(boolean Enable){
        if(input!=null)
            input.setEnabled(Enable);
    }
    public void setData( List<String> msgList) {
        if (msgList != null) {
            for (int k = 0; k < msgList.size(); k++) {
                adapter.add(msgList.get(k));
            }
        } else {
            adapter.clear();
        }
    }

    public EditText getInputEditText(){
        return input;
    }
    public  void setOnItemClickListener(OnPopWindowClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    public interface OnPopWindowClickListener{
        public void onItemClick(int position,String txt);
        public void onInputFocusChange(boolean hasFocus);
        public boolean onClick();
    }



}


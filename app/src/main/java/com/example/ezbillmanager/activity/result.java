package com.example.ezbillmanager.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ezbillmanager.R;

public class result extends AppCompatActivity {

    private TextView date1;
    private TextView date2;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        //获取组件
        date1.findViewById(R.id.result_date1);
        date2.findViewById(R.id.result_date2);
        btn=findViewById(R.id.result_btn_return);
        //获取从query获取的日期
        String Date1 =(String)getIntent().getCharSequenceExtra("startdate");
        String Date2 =(String)getIntent().getCharSequenceExtra("enddate");
        //设置日期文本显示
        date1.setText(Date1);
        date2.setText(Date2);
        //设置返回
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //循环视图

    }
}
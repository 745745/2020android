package com.example.ezbillmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ezbillmanager.R;

import java.util.ArrayList;
import java.util.List;

public class home_page extends AppCompatActivity {

    private Button btn_add;
    private Button btn_piechart;
    private Button btn_payment;
    private ImageView img_write;
    private TextView tv_sentence;

    private RecyclerView mRecyclerView;
    private List<CostBean> mList;
    private Time date;
    private Button btn_up;
    private Button btn_down;
    private TextView tv_month;
    private int month;
    private TextView expnum;
    private TextView incomum;
    private int exp_num_month;
    private int income_num_month;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //获取activity_home_page所有控件
        btn_add = findViewById(R.id.btn_add);
        btn_piechart = findViewById(R.id.btn_piechart);
        btn_payment = findViewById(R.id.btn_payment);

        img_write = findViewById(R.id.img_write);
        tv_sentence = findViewById(R.id.tv_sentence);
        btn_down=findViewById(R.id.btn_up);
        btn_up=findViewById(R.id.btn_down);
        tv_month=findViewById(R.id.tv_month);
        expnum=findViewById(R.id.tv_expendnum);
        incomum=findViewById(R.id.tv_incomenum);

        //获取当前月份
        date = new Time();
        date.setToNow();
        month=date.month;
        month++;
        tv_month.setText(String.valueOf(month)+"月");

        //按键监听
        //跳转至记账页面，变换按钮颜色
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home_page.this, Account.class);
                intent.putExtra("Account",0);
                startActivityForResult(intent,1);
            }
        });

        //跳转至图表页面，变换按钮颜色
        btn_piechart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home_page.this, Chart.class);
                btn_piechart.setBackgroundResource(R.drawable.piechart1);
                btn_payment.setBackgroundResource(R.drawable.payment);
                startActivity(intent);
            }
        });

        //左键由本月->本周->本日
        btn_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        //右键本月->本季度->本年
        btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });



    }
}
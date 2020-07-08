package com.example.ezbillmanager.activity;

import android.widget.Button;
import android.widget.TextView;

public class Chart {
    private Button btn_add;
    private Button btn_piechart;
    private Button btn_payment;
    private Button btn_account_expend;
    private Button btn_account_income;
    private Button btn_down;
    private Button btn_up;
    private TextView textView_month;
    private TextView expnum;
    private TextView incomum;
    private android.text.format.Time date;


    private TextView tv_cost;

    int mark=-1,month;//mark 1代表查询收入，-1代表查询支出
}

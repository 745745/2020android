package com.example.ezbillmanager.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ezbillmanager.R;

public class Account extends AppCompatActivity {
    private Button btn_return;
    private Button btn_classify;
    private Button btn_save;
    private Button btn_account_expend;
    private Button btn_account_income;
    private EditText et_money;
    private boolean inorout = true;//true为支出栏，false为收入栏

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        btn_return = findViewById(R.id.btn_return);
        btn_classify = findViewById(R.id.btn_classify);
        btn_save = findViewById(R.id.btn_save);
        et_money = findViewById(R.id.et_money);
        btn_account_expend = findViewById(R.id.btn_account_expend);
        btn_account_income = findViewById(R.id.btn_account_income);

        //返回键：返回主页
        btn_return.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Account.this, home_page.class);
                startActivity(intent);
            }
        });

        //分类栏进入项目分类页面label
        btn_classify.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Account.this, label.class);
                startActivity(intent);
                //无法跳转，出了问题
            }
        });

        //收入栏
        btn_account_income.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                inorout = false;
                btn_account_income.setBackgroundColor(Color.parseColor("#ffffff"));
                btn_account_expend.setBackgroundColor(Color.parseColor("#ececec"));
            }
        });

        //支出栏
        btn_account_expend.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                inorout = true;
                btn_account_expend.setBackgroundColor(Color.parseColor("#ffffff"));
                btn_account_income.setBackgroundColor(Color.parseColor("#ececec"));
            }
        });

        //保存键：数据存储；并返回主页home_page
        btn_save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {


            }
        });
    }
}

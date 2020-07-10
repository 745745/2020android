package com.example.ezbillmanager.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ezbillmanager.utils.*;
import com.example.ezbillmanager.R;
import com.example.ezbillmanager.utils.NetManager;
import com.example.ezbillmanager.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Account extends AppCompatActivity {
    private Button btn_return;
    private EditText textview_classify;
    private Button btn_save;
    private Button btn_account_expend;
    private Button btn_account_income;
    private EditText et_money;
    private boolean inorout = true;//true为支出栏，false为收入栏
    private String string1;
    private int t;
    private String str;
    public boolean success=false;
    private android.text.format.Time date;
    int year,day,month;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        btn_return = findViewById(R.id.btn_return);
        textview_classify = findViewById(R.id.textview_classify);
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
                Account.this.finish();
            }
        });

        //分类栏进入项目分类页面label
        /*textview_classify.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Account.this, label.class);
                startActivity(intent);
                //无法跳转，出了问题
            }
        });*/

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
                String string = et_money.getText().toString();
                if(inorout==true)
                    t = Integer.parseInt(string);
                else
                    t =-1* Integer.parseInt(string);
                string1 = textview_classify.getText().toString();



                //获取时间
                date = initDate();
                str = date.year+"."+date.month+"."+date.monthDay;
                final userInfo userinfo=userInfo.getInstance();
                //


                class Mythread extends Thread
                {
                    @Override
                    public void run() {
                        super.run();
                        com.example.ezbillmanager.utils.NetManager netManager= com.example.ezbillmanager.utils.NetManager.getInstance();
                        //这里
                        if(netManager.addBill(userinfo.userid,str,string1,t)==1)
                        {
                            success=true;
                        }
                    }
                }

                Mythread mythread=new Mythread();
                mythread.start();
                try {
                    mythread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(success)
                {

                    Intent intent = new Intent(Account.this, home_page.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"失败",Toast.LENGTH_LONG);
                }

            }
        });



    }


    public android.text.format.Time initDate()
    {
        date = new Time();
        date.setToNow();
        date.year=date.year;
        date.month=date.month+1;
        date.monthDay=date.monthDay;
        return date;
    }
}

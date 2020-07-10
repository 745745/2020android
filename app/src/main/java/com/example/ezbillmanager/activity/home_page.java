package com.example.ezbillmanager.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.Time;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ezbillmanager.R;
import com.example.ezbillmanager.utils.BillInfo;
import com.example.ezbillmanager.utils.userInfo;
import java.net.*;
import java.io.*;
import com.example.ezbillmanager.utils.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class home_page extends AppCompatActivity {

    private Button btn_add;
    private Button btn_piechart;
    private Button btn_payment;
    private ImageView img_write;
    private TextView tv_sentence;

    private RecyclerView mRecyclerView;
    private Time date;
    private Button btn_up;
    private Button btn_down;
    private TextView tv_month;
    private int month;
    private TextView expnum;
    private TextView incomum;
    private LinearLayout putbill;
    public boolean success = false;
    private int income = 0;
    private int outcome = 0;
    private BillInfo[] bill;
    private int dateMark=2;//0当天,1当周，2当月，3当季度，4当年，5全部
    private String[] text={"今天","本周","本月","本季度","本年度","全部"};
    private ArrayList<TextView> tx=new ArrayList<TextView>();
    String startTime="";
    String endTime="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //获取activity_home_page所有控件
        btn_add = findViewById(R.id.btn_add);
        btn_piechart = findViewById(R.id.btn_piechart);
        btn_payment = findViewById(R.id.btn_payment);
        putbill = findViewById(R.id.putbill);
        //putbill.setMovementMethod(ScrollingMovementMethod.getInstance());



        img_write = findViewById(R.id.img_write);
        tv_sentence = findViewById(R.id.tv_sentence);
        btn_down = findViewById(R.id.btn_up);
        btn_up = findViewById(R.id.btn_down);
        tv_month = findViewById(R.id.tv_month);
        expnum = findViewById(R.id.tv_expendnum);
        incomum = findViewById(R.id.tv_incomenum);

        //加号键：跳转至记账页面
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home_page.this, Account.class);
                startActivity(intent);
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



        //获取当前月份
        date = new Time();
        date.setToNow();
        date.month = date.month +1;
        tv_month.setText(String.valueOf(date.month));
        final userInfo userinfo = userInfo.getInstance();
//
//
        final ArrayList<String> put = new ArrayList<String>();
        class Mythread extends Thread {
            @Override
            public void run() {
                super.run();
                NetManager netManager = NetManager.getInstance();
                String startTime=date.year+"."+date.month+"."+"0";
                String endTime=date.year+"."+date.month+"."+"9";
                bill = netManager.findBillByTimeval(userinfo.userid,startTime,endTime);
                income += chooseBill.sumBill(chooseBill.chooseIncome(bill));
                outcome += chooseBill.sumBill(chooseBill.chooseExpand(bill));
                Arrays.sort(bill);
                int len = bill.length;
                for (int i = 0; i < len; i++) {
                    if (bill == null) break;
                    else {
                        if (bill[i].getMoney() > 0)
                            put.add("时间：" + bill[i].getTime() + " " + "金额：" + bill[i].getMoney() + " " + "花费类型：" + bill[i].getType() + "\n");
                        else
                            put.add("时间：" + bill[i].getTime() + " " + "金额：" + -1 * bill[i].getMoney() + " " + "收入类型：" + bill[i].getType() + "\n");
                        success = true;
                    }

                }
            }
        }

        Mythread mythread = new Mythread();
        mythread.start();
        try {
            mythread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (success) {
            for (int i = 0; i < put.size(); i++) {
                TextView textView = new TextView(this);
                textView.setText(put.get(i));
                if (bill[i].getMoney() > 0)
                    textView.setTextColor(Color.parseColor("#FF0000"));
                else
                    textView.setTextColor(Color.parseColor("#008000"));
                tx.add(textView);
                putbill.addView(textView);
            }
            String income11 = String.valueOf(income);
            String outcome11 = String.valueOf(outcome);
            expnum.setText("￥" + income11);
            incomum.setText("￥" + outcome11);
        } else {
            Toast.makeText(getApplicationContext(), "获取账单失败，请检查网络", Toast.LENGTH_LONG);
        }




        //左键由本月->本周->本日
        btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                income=0;
                outcome=0;
                for(int i=0;i<tx.size();i++)
                {
                    tx.get(i).setVisibility(View.GONE);
                }
                tx.clear();


                if(dateMark>0)
                {
                    dateMark--;
                    btn_up.setVisibility(View.VISIBLE);
                }
                else
                {
                    btn_down.setVisibility(View.INVISIBLE);
                }
                date = new Time();
                date.setToNow();
                date.year=date.year;
                date.month=date.month;
                date.monthDay=date.monthDay;
                NetManager netManager=NetManager.getInstance();

                int year=date.year;
                int month=date.month;
                int day=date.monthDay;



                switch (dateMark)
                {
                    case(0)://当天
                        startTime=year+"."+(month+1)+"."+day;
                        endTime=year+"."+(month+1)+"."+day;
                        break;
                    case (1)://当周
                        int h=weekDay(year,month+1,day);
                        int day1=day-(h-1);
                        int day2=day+(7-h);
                        if(day1<10)
                        {
                            startTime = year + "." + (month + 1) + "." + 0+day1;
                        }
                        else
                            startTime = year + "." + (month + 1) + "." + day1;
                        endTime=year+"."+(month+1)+"."+day2;
                        break;
                    case (2)://当月
                        startTime=year+"."+(month+1)+"."+0;
                        endTime=year+"."+(month+1)+"."+9;
                        break;
                    case(3)://当季度
                        int month1=(month+1)/3*3+1;
                        int month2=((month+1)/3+1)*3;
                        startTime=year+"."+month1+"."+0;
                        endTime=year+"."+month2+"."+9;
                        break;
                    case(4):
                        startTime=year+"."+0+"."+0;
                        endTime=year+"."+9+"."+9;
                        break;
                    case(5):
                        startTime="0000.00.00";
                        endTime="9999.99.99";
                }

                tv_month.setText(text[dateMark]);

                final userInfo userinfo = userInfo.getInstance();
                final ArrayList<String> put = new ArrayList<String>();
                class Mythread extends Thread {
                    @Override
                    public void run() {
                        super.run();
                        NetManager netManager = NetManager.getInstance();
                        bill = netManager.findBillByTimeval(userinfo.userid,startTime,endTime);
                        income += chooseBill.sumBill(chooseBill.chooseIncome(bill));
                        outcome += chooseBill.sumBill(chooseBill.chooseExpand(bill));
                        Arrays.sort(bill);
                        int len = bill.length;
                        for (int i = 0; i < len; i++) {
                            if (bill == null) break;
                            else {
                                if (bill[i].getMoney() > 0)
                                    put.add("时间：" + bill[i].getTime() + " " + "金额：" + bill[i].getMoney() + " " + "花费类型：" + bill[i].getType() + "\n");
                                else
                                    put.add("时间：" + bill[i].getTime() + " " + "金额：" + -1 * bill[i].getMoney() + " " + "收入类型：" + bill[i].getType() + "\n");
                                success = true;
                            }

                        }
                    }
                }

                Mythread mythread = new Mythread();
                mythread.start();
                try {
                    mythread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (success) {
                    for (int i = 0; i < put.size(); i++) {
                        TextView textView = new TextView(home_page.this);
                        textView.setText(put.get(i));
                        if (bill[i].getMoney() > 0)
                            textView.setTextColor(Color.parseColor("#FF0000"));
                        else
                            textView.setTextColor(Color.parseColor("#008000"));
                        tx.add(textView);
                        putbill.addView(textView);
                    }
                    String income11 = String.valueOf(income);
                    String outcome11 = String.valueOf(outcome);
                    expnum.setText("￥" + income11);
                    incomum.setText("￥" + outcome11);

                } else {
                    Toast.makeText(getApplicationContext(), "获取账单失败，请检查网络", Toast.LENGTH_LONG);
                }
        }});

        //右键本月->本季度->本年->全部
        btn_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                income=0;
                outcome=0;
                for(int i=0;i<tx.size();i++)
                {
                    tx.get(i).setVisibility(View.GONE);
                }
                tx.clear();

                if(dateMark<5)
                {
                    dateMark++;
                    btn_down.setVisibility(View.VISIBLE);
                }
                else
                {
                    btn_up.setVisibility(View.INVISIBLE);
                }
                date = new Time();
                date.setToNow();
                date.year=date.year;
                date.month=date.month;
                date.monthDay=date.monthDay;
                NetManager netManager=NetManager.getInstance();

                int year=date.year;
                int month=date.month;
                int day=date.monthDay;



                switch (dateMark)
                {
                    case(0)://当天
                        startTime=year+"."+(month+1)+"."+day;
                        endTime=year+"."+(month+1)+"."+day;
                        break;
                    case (1)://当周
                        int h=weekDay(year,month+1,day);
                        int day1=day-(h-1);
                        int day2=day+(7-h);
                        if(day1<10)
                        {
                            startTime = year + "." + (month + 1) + "." + 0+day1;
                        }
                        else
                            startTime = year + "." + (month + 1) + "." + day1;
                        endTime=year+"."+(month+1)+"."+day2;
                        break;
                    case (2)://当月
                        startTime=year+"."+(month+1)+"."+0;
                        endTime=year+"."+(month+1)+"."+9;
                        break;
                    case(3)://当季度
                        int month1=(month+1)/3*3+1;
                        int month2=((month+1)/3+1)*3;
                        startTime=year+"."+month1+"."+0;
                        endTime=year+"."+month2+"."+9;
                        break;
                    case(4):
                        startTime=year+"."+0+"."+0;
                        endTime=year+"."+9+"."+9;
                        break;
                    case(5):
                        startTime="0000.00.00";
                        endTime="9999.99.99";
                }

                tv_month.setText(text[dateMark]);

                final userInfo userinfo = userInfo.getInstance();
                final ArrayList<String> put = new ArrayList<String>();
                class Mythread extends Thread {
                    @Override
                    public void run() {
                        super.run();
                        NetManager netManager = NetManager.getInstance();
                        bill = netManager.findBillByTimeval(userinfo.userid,startTime,endTime);
                        income += chooseBill.sumBill(chooseBill.chooseIncome(bill));
                        outcome += chooseBill.sumBill(chooseBill.chooseExpand(bill));
                        Arrays.sort(bill);
                        int len = bill.length;
                        for (int i = 0; i < len; i++) {
                            if (bill == null) break;
                            else {
                                if (bill[i].getMoney() > 0)
                                    put.add("时间：" + bill[i].getTime() + " " + "金额：" + bill[i].getMoney() + " " + "花费类型：" + bill[i].getType() + "\n");
                                else
                                    put.add("时间：" + bill[i].getTime() + " " + "金额：" + -1 * bill[i].getMoney() + " " + "收入类型：" + bill[i].getType() + "\n");
                                success = true;
                            }

                        }
                    }
                }

                Mythread mythread = new Mythread();
                mythread.start();
                try {
                    mythread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (success) {
                    for (int i = 0; i < put.size(); i++) {
                        TextView textView = new TextView(home_page.this);
                        textView.setText(put.get(i));
                        if (bill[i].getMoney() > 0)
                            textView.setTextColor(Color.parseColor("#FF0000"));
                        else
                            textView.setTextColor(Color.parseColor("#008000"));

                        tx.add(textView);
                        putbill.addView(textView);

                    }
                    String income11 = String.valueOf(income);
                    String outcome11 = String.valueOf(outcome);
                    expnum.setText("￥" + income11);
                    incomum.setText("￥" + outcome11);

                } else {
                    Toast.makeText(getApplicationContext(), "获取账单失败，请检查网络", Toast.LENGTH_LONG);
                }
        }});

    }

    public int weekDay(int year,int month,int day)
    {
        int x = year;
        int y = month;
        int z = day;
        if (month == 1) {
            month = 13;
            year = year - 1;
        }
        if (month == 12) {
            month = 14;
            year = year - 1;
        }
        // 蔡勒公式
        int h = ((day + (26 * (month + 1) / 10) + (year % 100) + ((year % 100) / 4) + ((year / 100) / 4) + 5 * (year / 100)) % 7);
        if(h>=2)
            return h-1;
        else
            return h+6;
    }


}
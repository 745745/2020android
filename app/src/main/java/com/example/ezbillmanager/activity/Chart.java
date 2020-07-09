package com.example.ezbillmanager.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.ezbillmanager.utils.* ;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ezbillmanager.R;
import com.hedan.piechart_library.PieChart_View;

public class Chart extends AppCompatActivity {
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
    private NetManager netManager;

    private TextView tv_cost;

    int mark=-1,year,month,day;//mark=1代表查询收入，-1代表查询支出

    private PieChart_View pieView;//饼图

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        btn_add = findViewById(R.id.btn_add);
        btn_piechart = findViewById(R.id.btn_piechart);
        btn_payment = findViewById(R.id.btn_payment);

        btn_account_expend = findViewById(R.id.btn_account_expend);
        btn_account_income = findViewById(R.id.btn_account_income);
        btn_down=findViewById(R.id.btn_up);
        btn_up=findViewById(R.id.btn_down);
        textView_month=findViewById(R.id.tv_month);
        expnum=findViewById(R.id.tv_expendnum);
        incomum=findViewById(R.id.tv_incomenum);

        //添加键:返回记账页
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Chart.this, Account.class);
                startActivity(intent);
            }
        });

        //收入栏
        btn_account_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mark=1;
                btn_account_income.setBackgroundColor(Color.parseColor("#ffffff"));
                btn_account_expend.setBackgroundColor(Color.parseColor("#ececec"));
            }
        });

        //支出栏
        btn_account_expend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mark=-1;
                btn_account_expend.setBackgroundColor(Color.parseColor("#ffffff"));
                btn_account_income.setBackgroundColor(Color.parseColor("#ececec"));
            }
        });

        //记账按键：返回主页；变换按钮颜色
        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Chart.this, home_page.class);
                btn_piechart.setBackgroundResource(R.drawable.piechart);
                btn_payment.setBackgroundResource(R.drawable.payment1);
                startActivity(intent);
            }
        });

        //图标按键:请求数据并显示图标
        btn_piechart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        //左键：由本月->本周->本日
        btn_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示饼状图

            }
        });

        //右键：由本月->本季度->本年
        btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示饼状图
                String startTime=year+"."+month+"."+"1";
                String endTime=year+"."+month+"."+"31";
                userInfo userinfo=userInfo.getInstance();
                BillInfo[] billInfo=netManager.findBillByTimeval(userinfo.userid,startTime,endTime);

            }
        });

        //获取当前时间
        date = new Time();
        date.setToNow();
        year=date.year;
        month=date.month;
        day=date.monthDay;
        netManager=NetManager.getInstance();
        //chart页面选取的饼状图实现具体实现参考：https://github.com/ithedan/HeDan_Piechart/blob/master/README.md

    }

}

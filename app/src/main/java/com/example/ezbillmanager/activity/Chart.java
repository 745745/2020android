package com.example.ezbillmanager.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.ezbillmanager.utils.* ;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ezbillmanager.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.hedan.piechart_library.PieChartBean;
import com.hedan.piechart_library.PieChart_View;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Chart extends AppCompatActivity {
    private Button btn_add;
    private Button btn_piechart;
    private Button btn_payment;
    private Button btn_account_expend;
    private Button btn_account_income;
    private Button btn_down;
    private Button btn_up;
    private TextView textView_month;
    private TextView expendnum;
    private TextView incomenum;
    private android.text.format.Time date;
    private NetManager netManager;
    private BillInfo[] bill=null;
    private TextView tv_cost;

    private RelativeLayout relativeLayout;
    private PieChart pieChart;

    int mark=-1,year,month,day;//mark=1代表查询收入，-1代表查询支出

    int dateMark=2;//0当天,1当周，2当月，3当季度，4当年
    String[] text={"今天","本周","本月","本季度","本年度","全部"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        init();


        //添加键:返回记账页
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Chart.this, Account.class);
                startActivity(intent);
                Chart.this.finish();
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
                //获取当前时间
                date = initDate();
                netManager=NetManager.getInstance();

                String startTime="";
                String endTime="";


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

                final userInfo userinfo=userInfo.getInstance();
                //BillInfo[] billInfo=netManager.findBillByTimeval(userinfo.userid,startTime,endTime);
                //先用1代替

                final String startTime1=startTime;
                final String endTime1=endTime;
                class Mythread extends Thread{
                    @Override

                    public void run() {
                        super.run();
                        bill=netManager.findBillByTimeval(userinfo.userid,startTime1,endTime1);
                    }
                }
                Mythread mythread=new Mythread();
                mythread.start();
                try {
                    mythread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(bill.length==0)
                    return;
                //找出是支出的账单
                BillInfo[] billExpand=chooseBill.chooseExpand(bill);
                BillInfo[] billIncome=chooseBill.chooseIncome(bill);
                int income=chooseBill.sumBill(billIncome);
                int expand=chooseBill.sumBill(billExpand);
                expendnum.setText(String.valueOf(expand));
                incomenum.setText(String.valueOf(income));
                if(mark==-1)
                {
                    bill = chooseBill.chooseExpand(bill);
                }
                else {
                    bill = chooseBill.chooseIncome(bill);
                }

                //相同类型的账单聚合到一起
                ArrayList<Integer> listSum=new ArrayList<Integer>();
                ArrayList<String> listType=new ArrayList<String>();
                int sum=0;
                String name=bill[0].getType();
                for(int i=0;i<bill.length;i++)
                {
                    sum+=bill[i].getMoney();
                    if(i==bill.length-1)
                    {
                        listSum.add(sum);
                        listType.add(bill[i].getType());
                        break;
                    }
                    if(name.equals(bill[i+1].getType()))
                       continue;
                    else
                    {
                        listSum.add(sum);
                        sum=0;
                        listType.add(bill[i].getType());
                        name=bill[i+1].getType();
                    }
                }

                List<PieEntry>list=new ArrayList<>();
                List<Integer>colors=new ArrayList<>();
                //设置数据
                for(int i=0;i< listSum.size();i++)
                {
                    int money=listSum.get(i);
                    String type=listType.get(i);
                    String color=randomHexStr(6);
                    list.add(new PieEntry(money,type));
                    colors.add(Color.parseColor(color));
                }

                PieDataSet pieDataSet=new PieDataSet(list,"");
                PieData pieData=new PieData(pieDataSet);
                pieDataSet.setColors(colors);

                pieData.setValueTextSize(10f);
                pieChart.setDrawEntryLabels(true);
                pieChart.setEntryLabelColor(Color.parseColor("#FFFFFF"));
                pieChart.setHighlightPerTapEnabled(true);
                pieChart.setUsePercentValues(true);
                Description description=new Description();
                description.setText("消费/收入统计,以百分比统计");
                pieChart.setDescription(description);
                pieChart.setData(pieData);
                pieChart.postInvalidate();
            }
        });

        //左键：由本月->本周->本日
        btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dateMark>0)
                {
                    dateMark--;
                    btn_up.setVisibility(View.VISIBLE);
                }
                if(dateMark==0)
                    btn_down.setVisibility(View.INVISIBLE);
                textView_month.setText(text[dateMark]);
            }
        });


        //右键：由本月->本季度->本年
        btn_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示饼状图

                if(dateMark<5)
                {
                    dateMark++;
                    btn_down.setVisibility(View.VISIBLE);
                }
                else
                    btn_up.setVisibility(View.INVISIBLE);
                textView_month.setText(text[dateMark]);
            }
        });

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



    //随机颜色
    public static String randomHexStr(int len) {
        try {
            StringBuffer result = new StringBuffer();
            result.append('#');
            for(int i=0;i<len;i++) {
                //随机生成0-15的数值并转换成16进制
                result.append(Integer.toHexString(new Random().nextInt(16)));
            }
            return result.toString().toUpperCase();
        } catch (Exception e) {
            System.out.println("获取16进制字符串异常，返回默认...");
            return "#00CCCC";
        }
    }

    //获取时间
    public android.text.format.Time initDate()
    {
        date = new Time();
        date.setToNow();
        year=date.year;
        month=date.month;
        day=date.monthDay;
        return date;
    }

    //初始化
    void init()
    {

        expendnum=findViewById(R.id.tv_expendnum);
        incomenum=findViewById(R.id.tv_incomenum);
        btn_add = findViewById(R.id.btn_add);
        btn_piechart = findViewById(R.id.btn_piechart);
        btn_payment = findViewById(R.id.btn_payment);

        btn_account_expend = findViewById(R.id.btn_account_expend);
        btn_account_income = findViewById(R.id.btn_account_income);
        btn_down=findViewById(R.id.btn_up);
        btn_up=findViewById(R.id.btn_down);
        textView_month=findViewById(R.id.tv_month);
        relativeLayout=(RelativeLayout)findViewById(R.id.layout_three);
        pieChart=findViewById(R.id.pirChart);
    }
}

class chooseBill
{
    //选择支出的账单
    public static BillInfo[] chooseExpand(BillInfo[] bill)
    {
        ArrayList<Integer>x=new ArrayList<Integer>();
        for(int i=0;i<bill.length;i++)
        {
            if(bill[i].getMoney()>0)
            {
                x.add(i);
            }
        }
        BillInfo[] billInfos=new BillInfo[x.size()];
        for(int i=0;i<x.size();i++)
        {
            billInfos[i]=new BillInfo();
            int p=x.get(i);
            billInfos[i].set_usrID(bill[p].getUsrID());
            billInfos[i].setTime(bill[p].getTime());
            billInfos[i].setType(bill[p].getType());
            billInfos[i].setMoney(bill[p].getMoney());
        }
        Arrays.sort(billInfos);
        return billInfos;
    }

    public static BillInfo[] chooseIncome(BillInfo[] bill) {
        ArrayList<Integer> x = new ArrayList<Integer>();
        for (int i = 0; i < bill.length; i++) {
            if (bill[i].getMoney() < 0) {
                x.add(i);
            }
        }
        BillInfo[] billInfos = new BillInfo[x.size()];
        for (int i = 0; i < x.size(); i++) {
            billInfos[i] = new BillInfo();
            int p = x.get(i);
            billInfos[i].set_usrID(bill[p].getUsrID());
            billInfos[i].setTime(bill[p].getTime());
            billInfos[i].setType(bill[p].getType());
            billInfos[i].setMoney(-1 * bill[p].getMoney());
        }
        Arrays.sort(billInfos);
        return billInfos;
    }
    public static int sumBill(BillInfo[] bill)
    {
        int sum=0;
        for(int i=0;i<bill.length;i++)
        {
            sum+=bill[i].getMoney();
        }
        return sum;
    }

}

package com.example.ezbillmanager.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ezbillmanager.R;
import com.example.ezbillmanager.utils.BillInfo;
import com.example.ezbillmanager.utils.NetManager;
import com.example.ezbillmanager.utils.userInfo;

public class modifyBill extends AppCompatActivity {

    private EditText tv_time;
    private EditText tv_type;
    private EditText tv_money;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_bill);
        Button modify = findViewById(R.id.modifymod);
        Button delete = findViewById(R.id.deletemod);
        tv_time=findViewById(R.id.timemod);
        tv_type=findViewById(R.id.typemod);
        tv_money=findViewById(R.id.moneymod);


        //时间 钱 类型
        Intent intent=getIntent();
        final String[] bill1=intent.getStringExtra("data").split(" ");
        final String[] bill={bill1[0].substring(3),bill1[1].substring(3),bill1[2].substring(5)};
        final String in_out=bill1[2].substring(0,2);



        BillInfo billInfo=new BillInfo();
        billInfo.setTime(bill[0]);
        billInfo.setMoney(Integer.parseInt(bill[1]));
        billInfo.setType(bill[2]);
        tv_time.setText(bill[0]);
        tv_type.setText(bill[2]);
        tv_money.setText(bill[1]);

        //修改
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetManager netManager=NetManager.getInstance();
                userInfo userinfo=userInfo.getInstance();
                final int userid=userinfo.userid;
                class myThread extends Thread{
                    @Override
                    public void run() {
                        super.run();
                        int moneyNew;   //要修改的钱
                        int moneyOld; //现在的钱
                        NetManager netManager=NetManager.getInstance();
                        if(!in_out.equals("花费"))//如果是收入
                        {
                           moneyOld=Integer.parseInt(bill[1])*-1;
                           moneyNew=Integer.parseInt(tv_money.getText().toString())*-1;
                        }
                        else
                        {
                            moneyOld = Integer.parseInt(bill[1]);
                            moneyNew=Integer.parseInt(tv_money.getText().toString());
                        }
                        netManager.modifyBill(userid,bill[0],bill[2],moneyOld,userid,tv_time.getText().toString(),tv_type.getText().toString(),moneyNew); //id,time,type,money
                    }
                }
                myThread mythread=new myThread();
                mythread.start();
                try {
                    mythread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent1=new Intent(modifyBill.this, home_page.class);
                startActivity(intent1);
                //modifyBill.this.finish();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userInfo userinfo=userInfo.getInstance();
                final int userid=userinfo.userid;
                class myThread extends Thread{
                    @Override
                    public void run() {
                        super.run();
                        int moneyOld; //现在的钱
                        NetManager netManager=NetManager.getInstance();
                        if(!in_out.equals("花费"))//如果是收入
                        {
                            moneyOld=Integer.parseInt(bill[1])*-1;
                        }
                        else
                        {
                            moneyOld = Integer.parseInt(bill[1]);
                        }
                        netManager.deleteBill(userid,bill[0],bill[2],moneyOld);
                    }
                }
                myThread mythread=new myThread();
                mythread.start();
                try {
                    mythread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent1=new Intent(modifyBill.this, home_page.class);
                startActivity(intent1);
                modifyBill.this.finish();
            }
        });

    }
}
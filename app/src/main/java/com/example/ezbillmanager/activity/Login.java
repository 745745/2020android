package com.example.ezbillmanager.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.ezbillmanager.utils.*;
import com.example.ezbillmanager.R;
import com.example.ezbillmanager.utils.NetManager;

public class Login extends AppCompatActivity {


    private Button btn;
    private TextView tv_userid;
    private TextView tv_password;
    public boolean success=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int userid=Integer.parseInt(tv_userid.getText().toString());
                final String password=tv_password.getText().toString();

                class Mythread extends Thread
                {
                    @Override
                    public void run() {
                        super.run();
                        NetManager netManager=NetManager.getInstance();
                        if(netManager.usrLogin(userid,password)==1)
                        {
                            success=true;
                            userInfo userinfo=userInfo.getInstance();
                            userinfo.setId(userid);
                            userinfo.setPassword(password);
                        }
                        else
                        {
                            int x=netManager.usrRegister(userid,password);
                            success=(x==1);
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
                    Login.this.finish();
                }
                else
                {
                    AlertDialog.Builder builder=new AlertDialog.Builder(Login.this);
                    builder.setTitle("错误");
                    builder.setMessage("登录失败，检测账户密码是否正确");
                    builder.setPositiveButton("确定",null);
                    builder.show();
                }
            }
        });

        tv_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_password.setFocusable(true);
                tv_password.setFocusableInTouchMode(true);
                tv_password.requestFocus();
                tv_password.requestFocusFromTouch();
                RelativeLayout relativeLayout=findViewById(R.id.login_layout);
                relativeLayout.setBackgroundResource(R.drawable.login);
            }
        });



    }



    public void init()
    {
        btn=findViewById(R.id.ensure1);
        tv_userid=findViewById(R.id.user1);
        tv_password=findViewById(R.id.password1);
        tv_userid.setBackgroundColor(Color.WHITE);
        tv_password.setBackgroundColor(Color.WHITE);
    }
}
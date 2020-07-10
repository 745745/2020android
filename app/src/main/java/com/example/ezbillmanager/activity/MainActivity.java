package com.example.ezbillmanager.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ezbillmanager.R;
import com.example.ezbillmanager.utils.* ;


public class MainActivity extends AppCompatActivity {

    private Button btn_start;
    private int userid;
    private String password;
    private Button btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_start = findViewById(R.id.btn_start);
        btn_login=findViewById(R.id.btn_login);
        //进入主页
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, home_page.class);
                if(!userInfo.getInstance().isNew())
                {
                    startActivity(intent);
                    MainActivity.this.finish();
                }
                else
                {
                    android.app.AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("提示");
                    builder.setMessage("请先登录后使用");
                    builder.setPositiveButton("确定",null);
                    builder.show();

                }
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Intent intent=new Intent(MainActivity.this,Login.class);
               startActivity(intent);
            }
        });

    }

}

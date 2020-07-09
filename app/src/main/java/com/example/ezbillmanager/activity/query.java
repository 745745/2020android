package com.example.ezbillmanager.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ezbillmanager.R;

public class query extends AppCompatActivity {

    private TextView Date1;
    private TextView Date2;
    private TextView Date3;
    private TextView Date4;
    private TextView Date5;
    private TextView Date6;
    private Button btn_1;
    private Button btn_2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        //获取所有组件
        Date1 = findViewById(R.id.query_editTextNumber1);
        Date2 = findViewById(R.id.query_editTextNumber2);
        Date3 = findViewById(R.id.query_editTextNumber3);
        Date4 = findViewById(R.id.query_editTextNumber4);
        Date5 = findViewById(R.id.query_editTextNumber5);
        Date6 = findViewById(R.id.query_editTextNumber6);
        btn_1 = findViewById(R.id.query_btn1);
        btn_2 = findViewById(R.id.query_btn2);


        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Date1.getText().length()!=0&&Date2.getText().length()!=0&&Date3.getText().length()!=0
                &&Date4.getText().length()!=0&&Date5.getText().length()!=0&&Date6.getText().length()!=0)
                {
                    Intent intent = new Intent(query.this,result.class);
                   intent.putExtra("startdate",Date1.getText()+"."+Date2.getText()+"."+Date3.getText());
                    intent.putExtra("enddate",Date4.getText()+"."+Date5.getText()+"."+Date6.getText());
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(query.this,"请将日期填写完整！",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
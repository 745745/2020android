package com.example.ezbillmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ezbillmanager.R;

public class newlabel extends AppCompatActivity {


    private Button btn_c_return;
    private Button btn_label1;
    private Button btn_label2;
    private Button btn_label3;
    private Button btn_label4;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newlabel);

        btn_c_return = findViewById(R.id.btn_c_return);
        btn_label1=findViewById(R.id.btn_label1);
        btn_label2=findViewById(R.id.btn_label2);
        btn_label3=findViewById(R.id.btn_label3);
        btn_label4=findViewById(R.id.btn_label4);

        btn_c_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(newlabel.this, Account.class);
                startActivity(intent);
            }
        });

        btn_label1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                //intent.putExtra("int","1");
                intent.setClass(newlabel.this, Accountnext1.class);
                //intent.putExtras(data);
                startActivity(intent);
            }
        });
        btn_label2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(newlabel.this, Accountnext2.class);
                startActivity(intent);
            }
        });
        btn_label3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(newlabel.this, Accountnext3.class);
                startActivity(intent);
            }
        });
        btn_label4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(newlabel.this, Accountnext4.class);
                startActivity(intent);
            }
        });



    }


}

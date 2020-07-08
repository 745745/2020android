package com.example.ezbillmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
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
    private Time date;
    private boolean inorout = true;

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

        if (checkIntentAvailable(getIntent())) {
            if (getIntent().getIntExtra("chart", 0) == 3) {
                et_money.setText(getIntent().getStringExtra("money"));
                btn_classify.setText(getIntent().getStringExtra("costTile"));
                inorout = getIntent().getBooleanExtra("costInOut", true);
            }
        }
    }

    private boolean checkIntentAvailable(Intent intent) {
        if (intent.resolveActivity(getPackageManager()) != null) {
            return true;

        } else {
            return false;
        }

    }
}

package com.example.ezbillmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.animation.AnimationUtils;

import com.example.ezbillmanager.R;


public class label extends AppCompatActivity {
    private Button btn_c_return;
    private Button btn_add_item;
    private CheckBox recylerView_long_checkbox;
    private RecyclerView mRecyclerView;

    //是否显示CheckBox
    private static boolean isShow;

    //checkBox三个键
    private Button btn_redo;
    private Button btn_edit;
    private Button btn_delete;

    //点击加号后弹出的编辑栏
    private RelativeLayout lay;
    private Button btn_lay_return;
    private Button btn_lay_yes;
    private EditText et_classify;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label);

        btn_c_return = findViewById(R.id.btn_c_return);
        btn_redo = findViewById(R.id.btn_redo);
        btn_edit = findViewById(R.id.btn_edit);
        btn_delete = findViewById(R.id.btn_delete);
        btn_add_item = findViewById(R.id.btn_add_item);

        lay = findViewById(R.id.lay);
        btn_lay_return = findViewById(R.id.btn_lay_return);
        btn_lay_yes = findViewById(R.id.btn_lay_yes);
        et_classify = findViewById(R.id.et_classify);

        mRecyclerView = findViewById(R.id.id_recylerView);
        recylerView_long_checkbox = findViewById(R.id.recylerView_long_checkbox);

        //<键：返回记账页
        btn_c_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isShow = false;
                Intent intent = new Intent(label.this, Account.class);
                startActivity(intent);
            }
        });

        //加号键：添加分类项
        btn_add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(label.this,"添加栏",Toast.LENGTH_SHORT).show();
                ShowLay(1);//从下弹出添加栏，实现类别台南佳
            }
        });

        //设置RecyclerView的布局管理
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //设置RecyclerView的Item分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));


        //对某项类别item的选中监听
        //点击为选中，返回记账页Account
        //长按为编辑，显示操作页面

        //长按后显示操作界面
        /*private void showOperate(){
            btn_delete.setVisibility(View.VISIBLE);
            btn_redo.setVisibility(View.VISIBLE);
            btn_edit.setVisibility(View.VISIBLE);
            btn_add_item.setVisibility(View.GONE);

            btn_redo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //隐藏操作界面，即取消操作界面
                }
            });


            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //删除当前选中列别
                }
            });

            btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //修改当前类别名称
                    if(sList != null && sList.size()>0)
                    {
                        ShowLay(0);//上滑添加栏
                    }
                    else
                    {
                        Toast.makeText(label.this,"请选择item编辑",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        */

    }

    //上滑添加栏
    private void ShowLay(int code)
    {
        btn_add_item.setVisibility(View.GONE);
        if(code == 0)
            //长按类别并选中后对类别edit
        {
            btn_add_item.setVisibility(View.GONE);
            lay.setVisibility(View.VISIBLE);
            Animation animation = AnimationUtils.loadAnimation(this,R.anim.operate_in);
            lay.setAnimation(animation);
            btn_lay_return.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DismissLay();
                }
            });

            btn_lay_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //修改当前类别名称
                }

            });
        }
        else if(code == 1)
        //加号点击后添加新类别
        {
            lay.setVisibility(View.VISIBLE);
            Animation animation = AnimationUtils.loadAnimation(this,R.anim.operate_in);
            lay.setAnimation(animation);
            btn_lay_return.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DismissLay();
                }
            });

            btn_lay_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //添加新类别名称
                }
            });
        }

    }

    //取消上滑的添加栏
    private void DismissLay()
    {
        lay.setVisibility(View.GONE);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.operate_out);
        lay.setAnimation(animation);
        btn_add_item.setVisibility(View.VISIBLE);
    }

}

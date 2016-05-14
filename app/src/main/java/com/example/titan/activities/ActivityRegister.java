package com.example.titan.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.titan.data_base_op.DBOperation;
import com.example.titan.materialtest.R;

public class ActivityRegister extends ActionBarActivity {

    private Toolbar toolbar;
    private Button registerButton;

    private EditText mEditTextEmail;
    private EditText mEditTextUsername;
    private EditText mEditTextPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("注册用户");

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mEditTextEmail = (EditText) findViewById(R.id.et_emil_reg);
        mEditTextUsername = (EditText) findViewById(R.id.et_name_reg);
        mEditTextPassword = (EditText) findViewById(R.id.et_password_reg);
        registerButton = (Button) findViewById(R.id.btn_register);


        //数据校验....

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Email = mEditTextEmail.getText().toString().trim();
                String UserName = mEditTextUsername.getText().toString().trim();
                String Password = mEditTextPassword.getText().toString().trim();

                if(UserName.equals("") || UserName == null){
                    Toast.makeText(ActivityRegister.this,"用户名不能为空!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Password.equals("") || Password == null){
                    Toast.makeText(ActivityRegister.this,"密码不能为空!",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(Password.length() < 8){
                    Toast.makeText(ActivityRegister.this,"密码长度不能低于8个字符!",Toast.LENGTH_SHORT).show();
                    return;
                }

                //输出到数据库
                DBOperation dbOperation = DBOperation.getInstance();

                ContentValues cValues = new ContentValues();
                cValues.put("username", UserName);
                cValues.put("password", Password);

                //调用insert插入数据库
                dbOperation.insertUserIntoMyDB(cValues);
                Toast.makeText(ActivityRegister.this,"注册完毕！",Toast.LENGTH_SHORT).show();
            }
        });


        //返回主页
//        Intent intent = new Intent(this,MainActivity.class);
//        startActivity(intent);

    }

}

package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    private EditText email,pwd;
    private Button login,login_g,login_f;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        EditText email=findViewById(R.id.email_log);
        EditText pwd=findViewById(R.id.pwd_log);

        final Button login=findViewById(R.id.button_log);
        Button login_g=findViewById(R.id.button_logg);
        Button login_f=findViewById(R.id.button_logf);

        TextView tv_r=findViewById(R.id.textView_logr);
        TextView tv_sk=findViewById(R.id.textView_logsk);



        tv_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

        tv_sk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,home.class);
                startActivity(intent);
            }
        });
    }
}

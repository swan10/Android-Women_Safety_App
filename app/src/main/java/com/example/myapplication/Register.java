package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        EditText email=findViewById(R.id.email_reg);
        EditText pwd=findViewById(R.id.pwd_reg);

        final Button login=findViewById(R.id.button_reg);
        Button login_g=findViewById(R.id.button_regg);
        Button login_f=findViewById(R.id.button_regf);

        TextView tv_l=findViewById(R.id.textView_regl);
        TextView tv_sk=findViewById(R.id.textView_regsk);



        tv_l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });

        tv_sk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Register.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}

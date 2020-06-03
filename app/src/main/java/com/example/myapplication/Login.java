package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class Login extends AppCompatActivity {

    private long backPressedTime;
    private EditText name,pwd;
    private Button login,login_g,login_f;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myDb=new DatabaseHelper(this);
        name=findViewById(R.id.name_log);
        pwd=findViewById(R.id.pwd_log);

        login=findViewById(R.id.button_log);
        login_g=findViewById(R.id.button_logg);
        login_f=findViewById(R.id.button_logf);

        TextView tv_r=findViewById(R.id.textView_logr);
        TextView tv_sk=findViewById(R.id.textView_logsk);

        login_g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Login.this,"This sevice is not currently activated",Toast.LENGTH_SHORT).show();
            }
        });
        login_f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Login.this,"This sevice is not currently activated",Toast.LENGTH_SHORT).show();
            }
        });

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
                Intent intent=new Intent(Login.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(Login.this);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Loging in....");
                progressDialog.show();


                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                String usernameValue = name.getText().toString();
                                String passwordValue = pwd.getText().toString();
                                if (myDb.isLoginValid(usernameValue, passwordValue)) {

                                    Intent intent = new Intent(Login.this, HomeActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(Login.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                    //Save.save(getApplicationContext(),"session","true");

                                } else {
                                    Toast.makeText(Login.this, "Invalid Username or Password!", Toast.LENGTH_SHORT).show();
                                }
                                progressDialog.dismiss();
                            }
                        }, 3000);



            }
        });
    }
    //back press activity
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finish();
            this.finishAffinity();
            //System.exit(1);


        } else {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Press again to Exit...", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}

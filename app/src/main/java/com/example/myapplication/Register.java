package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    private Button reg;
    ImageView reg_g,reg_f,reg_t;
    private EditText email,pwd,name;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        myDb=new DatabaseHelper(this);

        name=findViewById(R.id.name_reg);
        email=findViewById(R.id.email_reg);
        pwd=findViewById(R.id.pwd_reg);

        reg=findViewById(R.id.button_reg);
        reg_g=findViewById(R.id.button_regg);
        reg_f=findViewById(R.id.button_regf);
        reg_t=findViewById(R.id.button_regt);

        //remove action bar
        getSupportActionBar().hide();

        TextView tv_l=findViewById(R.id.textView_regl);
        TextView tv_sk=findViewById(R.id.textView_regsk);

        reg_g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Register.this,"This sevice is not currently activated",Toast.LENGTH_SHORT).show();
            }
        });
        reg_f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Register.this,"This sevice is not currently activated",Toast.LENGTH_SHORT).show();
            }
        });
        reg_t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Register.this,"This sevice is not currently activated",Toast.LENGTH_SHORT).show();
            }
        });

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
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }
    public void signUp(){

        reg.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(Register.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String namer = name.getText().toString();
        String emailr = email.getText().toString();
        String passwordr = pwd.getText().toString();

        if (!validate()) {
            onSignupFailed();
            return;
        }

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        boolean isInserted = myDb.insertData(name.getText().toString(),email.getText().toString(),pwd.getText().toString());
                        //Save.save(getApplicationContext(),"session","true");
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);

    }
    public void onSignupSuccess() {
        reg.setEnabled(true);
        Toast.makeText(getApplicationContext(),"Registered Successfully",Toast.LENGTH_LONG).show();
        Intent intent=new Intent(Register.this,Login.class);
        startActivity(intent);
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Registration failed", Toast.LENGTH_LONG).show();

        reg.setEnabled(true);
    }
    public boolean validate() {
        boolean valid = true;
        String namer = name.getText().toString();
        String emailr = email.getText().toString();
        String passwordr = pwd.getText().toString();

        if (namer.isEmpty() || namer.length() < 3) {
            name.setError("at least 3 characters");
            valid = false;
        } else {
            name.setError(null);
        }

        if (emailr.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailr).matches()) {
            email.setError("enter a valid email address");
            valid = false;
        } else {
            email.setError(null);
        }

        if (passwordr.isEmpty() || passwordr.length() < 4 || passwordr.length() > 10) {
            pwd.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            pwd.setError(null);
        }

        return valid;
    }

    //back button
    public void onBackPressed(){
        Intent i=new Intent(Register.this,Login.class);
        startActivity(i);
    }
}

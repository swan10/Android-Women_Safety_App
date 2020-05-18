package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class custom_sms extends AppCompatActivity {
    EditText cs2,cs3;
    Button cs4,cs5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_sms);

        cs2=findViewById(R.id.cs2);
        cs3=findViewById(R.id.cs3);
        cs4=findViewById(R.id.cs4);
        cs5=findViewById(R.id.cs5);

        cs4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(custom_sms.this,Menu.class);
                startActivity(i);
            }
        });

        cs5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cs2.getText().toString().length()==0){
                    aletDailog();
                }
                else{
                    try{
                        SmsManager smgr = SmsManager.getDefault();
                        smgr.sendTextMessage(cs2.getText().toString().trim(),null,cs3.getText().toString(),null,null);

                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "SMS Sent Successfully", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                    catch (Exception e){
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "SMS Failed to Send, Please try again", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }
            }
        });
    }
    //back button
    public void onBackPressed(){
        Intent i=new Intent(custom_sms.this,Menu.class);
        startActivity(i);
    }
    //alert message for phone number null validation
    public void aletDailog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Information")
                .setMessage("Please Enter a valid Phone number.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Enter a valid phone number...", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }).show();

    }
}

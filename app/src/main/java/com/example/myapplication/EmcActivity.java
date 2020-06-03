package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EmcActivity extends AppCompatActivity {
    EditText emc1,emc2,emc3,emc4,emc5,emc6,emc7;
    Button emcb1;
    Emergency eDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emc);

        emc1=findViewById(R.id.emc1);
        emc2=findViewById(R.id.emc2);
        emc3=findViewById(R.id.emc3);
        emc4=findViewById(R.id.emc4);
        emc5=findViewById(R.id.emc5);
        emc6=findViewById(R.id.emc6);
        emc7=findViewById(R.id.emc7);
        emcb1=findViewById(R.id.emcb1);
        eDb=new Emergency(this);

        //fetch

        Cursor cursor=eDb.getAllData();
        StringBuilder stringBuilder=new StringBuilder();
        if(cursor != null && cursor.moveToFirst()){
            cursor.moveToFirst();
            do{
                EditText emc1=findViewById(R.id.emc1);
                EditText emc2=findViewById(R.id.emc2);
                EditText emc3=findViewById(R.id.emc3);
                EditText emc4=findViewById(R.id.emc4);
                EditText emc5=findViewById(R.id.emc5);
                EditText emc6=findViewById(R.id.emc6);
                EditText emc7=findViewById(R.id.emc7);
                emc1.setText(cursor.getString(cursor.getColumnIndexOrThrow("P1")));
                emc2.setText(cursor.getString(cursor.getColumnIndexOrThrow("P2")));
                emc3.setText(cursor.getString(cursor.getColumnIndexOrThrow("P3")));
                emc4.setText(cursor.getString(cursor.getColumnIndexOrThrow("P4")));
                emc5.setText(cursor.getString(cursor.getColumnIndexOrThrow("P5")));
                emc6.setText(cursor.getString(cursor.getColumnIndexOrThrow("P6")));
                emc7.setText(cursor.getString(cursor.getColumnIndexOrThrow("P7")));

            }while (cursor.moveToNext());

            cursor.close();
        }


        //insert

        emcb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEmc();
            }
        });
    }
    public void saveEmc(){
        final ProgressDialog progressDialog = new ProgressDialog(EmcActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Saving...");
        progressDialog.show();

        String p1 = emc1.getText().toString();
        String p2 = emc2.getText().toString();
        String p3 = emc3.getText().toString();
        String p4 = emc4.getText().toString();
        String p5 = emc5.getText().toString();
        String p6 = emc6.getText().toString();
        String p7 = emc7.getText().toString();

        if (!validate()) {
            onSaveFailed();
            return;
        }
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        boolean isInserted = eDb.insertData(emc1.getText().toString(),emc2.getText().toString(),emc3.getText().toString(),
                                emc4.getText().toString(),emc5.getText().toString(),emc6.getText().toString(),emc7.getText().toString());
                        //Save.save(getApplicationContext(),"session","true");
                        onSaveSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);

    }
    public void onSaveSuccess() {
        emcb1.setEnabled(true);
        Toast.makeText(getApplicationContext(),"Data Saved Successfully",Toast.LENGTH_LONG).show();
        Intent intent=new Intent(EmcActivity.this,EditActivity.class);
        startActivity(intent);
    }
    public void onSaveFailed() {
        Toast.makeText(getBaseContext(), "Some error occured..\nTry again later", Toast.LENGTH_LONG).show();

        emcb1.setEnabled(true);
    }
    public boolean validate() {
        boolean valid = true;
        String p1 = emc1.getText().toString();
        String p2 = emc2.getText().toString();
        String p3 = emc3.getText().toString();
        String p4 = emc4.getText().toString();
        String p5 = emc5.getText().toString();
        String p6 = emc6.getText().toString();
        String p7 = emc7.getText().toString();

        if (p1.isEmpty() || p1.length() < 3) {
            emc1.setError("Enter a valid phone number");
            valid = false;
        } else {
            emc1.setError(null);
        }

        if (p2.isEmpty() || !Patterns.PHONE.matcher(p2).matches()) {
            emc2.setError("Enter a valid phone number");
            valid = false;
        } else {
            emc2.setError(null);
        }

        if (p3.isEmpty() || !Patterns.PHONE.matcher(p3).matches()) {
            emc3.setError("Enter a valid phone number");
            valid = false;
        } else {
            emc3.setError(null);
        }
        if (p4.isEmpty() || !Patterns.PHONE.matcher(p4).matches()) {
            emc4.setError("Enter a valid phone number");
            valid = false;
        } else {
            emc4.setError(null);
        }
        if (p5.isEmpty() || !Patterns.PHONE.matcher(p5).matches()) {
            emc5.setError("Enter a valid phone number");
            valid = false;
        } else {
            emc5.setError(null);
        }
        if (p6.isEmpty() || !Patterns.PHONE.matcher(p6).matches()) {
            emc6.setError("Enter a valid phone number");
            valid = false;
        } else {
            emc6.setError(null);
        }
        if (p7.isEmpty() || !Patterns.PHONE.matcher(p7).matches()) {
            emc7.setError("Enter a valid phone number");
            valid = false;
        } else {
            emc7.setError(null);
        }

        return valid;
    }


}

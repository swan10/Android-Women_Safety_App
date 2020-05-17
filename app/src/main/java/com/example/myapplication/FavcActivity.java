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
import android.widget.Toast;

public class FavcActivity extends AppCompatActivity {
    EditText p1ed1,p1ed2,p2ed1,p2ed2,p3ed1,p3ed2;
    Button favcbtn;
    FavContactDB fDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favc);

        p1ed1=findViewById(R.id.p1ed1);
        p1ed2=findViewById(R.id.p1ed2);
        p2ed1=findViewById(R.id.p2ed1);
        p2ed2=findViewById(R.id.p2ed2);
        p3ed1=findViewById(R.id.p3ed1);
        p3ed2=findViewById(R.id.p3ed2);

        favcbtn=findViewById(R.id.favcbtn);
        fDb=new FavContactDB(this);

        //fetch

        Cursor cursor=fDb.getAllData();
        StringBuilder stringBuilder=new StringBuilder();
        if(cursor != null && cursor.moveToFirst()){
            cursor.moveToFirst();
            do{
                EditText p1ed1=findViewById(R.id.p1ed1);
                EditText p1ed2=findViewById(R.id.p1ed2);
                EditText p2ed1=findViewById(R.id.p2ed1);
                EditText p2ed2=findViewById(R.id.p2ed2);
                EditText p3ed1=findViewById(R.id.p3ed1);
                EditText p3ed2=findViewById(R.id.p3ed2);
                p1ed1.setText(cursor.getString(cursor.getColumnIndexOrThrow("P1")));
                p1ed2.setText(cursor.getString(cursor.getColumnIndexOrThrow("PC1")));
                p2ed1.setText(cursor.getString(cursor.getColumnIndexOrThrow("P2")));
                p2ed2.setText(cursor.getString(cursor.getColumnIndexOrThrow("PC2")));
                p3ed1.setText(cursor.getString(cursor.getColumnIndexOrThrow("P3")));
                p3ed2.setText(cursor.getString(cursor.getColumnIndexOrThrow("PC3")));

            }while (cursor.moveToNext());

            cursor.close();
        }

        favcbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFavc();
            }
        });

    }
    public void saveFavc(){
        final ProgressDialog progressDialog = new ProgressDialog(FavcActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Saving...");
        progressDialog.show();

        String p1 = p1ed1.getText().toString();
        String p2 = p1ed2.getText().toString();
        String p3 = p2ed1.getText().toString();
        String p4 = p2ed2.getText().toString();
        String p5 = p3ed1.getText().toString();
        String p6 = p3ed2.getText().toString();

        if (!validate()) {
            onSaveFailed();
            return;
        }
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        boolean isInserted = fDb.insertData(p1ed1.getText().toString(),p1ed2.getText().toString(),p2ed1.getText().toString(),
                                p2ed2.getText().toString(),p3ed1.getText().toString(),p3ed2.getText().toString());
                        //Save.save(getApplicationContext(),"session","true");
                        onSaveSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);

    }
    public void onSaveSuccess() {
        favcbtn.setEnabled(true);
        Toast.makeText(getApplicationContext(),"Data Saved Successfully",Toast.LENGTH_LONG).show();
        Intent intent=new Intent(FavcActivity.this,EditActivity.class);
        startActivity(intent);
    }
    public void onSaveFailed() {
        Toast.makeText(getBaseContext(), "Some error occured..\nTry again later", Toast.LENGTH_LONG).show();

        favcbtn.setEnabled(true);
    }
    public boolean validate() {
        boolean valid = true;
        String p1 = p1ed1.getText().toString();
        String p2 = p1ed2.getText().toString();
        String p3 = p2ed1.getText().toString();
        String p4 = p2ed2.getText().toString();
        String p5 = p3ed1.getText().toString();
        String p6 = p3ed2.getText().toString();


        if (p2.isEmpty() || !Patterns.PHONE.matcher(p2).matches()) {
            p1ed2.setError("Enter a valid phone number");
            valid = false;
        } else {
            p1ed2.setError(null);
        }
        if (p4.isEmpty() || !Patterns.PHONE.matcher(p4).matches()) {
            p2ed2.setError("Enter a valid phone number");
            valid = false;
        } else {
            p2ed2.setError(null);
        }
        if (p6.isEmpty() || !Patterns.PHONE.matcher(p6).matches()) {
            p3ed2.setError("Enter a valid phone number");
            valid = false;
        } else {
            p3ed2.setError(null);
        }

        return valid;
    }
    //back button
    public void onBackPressed(){
        Intent i=new Intent(FavcActivity.this,EditActivity.class);
        startActivity(i);
    }

}

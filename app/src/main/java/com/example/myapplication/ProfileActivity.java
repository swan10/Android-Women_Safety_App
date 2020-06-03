package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    private TextView tv1,tv2,tv3;
    private Button bt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tv1=findViewById(R.id.edp1);
        tv2=findViewById(R.id.edp2);
        tv3=findViewById(R.id.edp3);
        bt1=findViewById(R.id.bp1);
        myDb=new DatabaseHelper(this);

        //back button
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //fetching
        Cursor cursor=myDb.getAllData();
        StringBuilder stringBuilder=new StringBuilder();
        cursor.moveToFirst();
        do{
            TextView tv1=findViewById(R.id.edp1);
            TextView tv2=findViewById(R.id.edp2);
            TextView tv3=findViewById(R.id.edp3);
            tv1.setText(cursor.getString(cursor.getColumnIndexOrThrow("NAME")));
            tv2.setText(cursor.getString(cursor.getColumnIndexOrThrow("EMAIL")));
            tv3.setText(cursor.getString(cursor.getColumnIndexOrThrow("PWD")));
        }while (cursor.moveToNext());

        cursor.close();

        //update


    }
}

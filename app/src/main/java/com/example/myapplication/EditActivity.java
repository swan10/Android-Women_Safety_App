package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class EditActivity extends AppCompatActivity {
    RelativeLayout er1,er2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        er1=findViewById(R.id.er1);
        er2=findViewById(R.id.er2);

        er1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(EditActivity.this,FavcActivity.class);
                startActivity(i);
            }
        });
        er2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(EditActivity.this,EmcActivity.class);
                startActivity(i);
            }
        });
    }


}

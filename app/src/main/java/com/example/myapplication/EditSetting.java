package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class EditSetting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_setting);
    }
    public void onBackPressed(){
        Intent i=new Intent(EditSetting.this,Setting.class);
        startActivity(i);
    }
}

package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Setting extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{
    DatabaseHelper myDb;
    private TextView name;
    RelativeLayout r1,sr1,sr2,sr3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        myDb=new DatabaseHelper(this);
        name=findViewById(R.id.sname);

        //profile
        Cursor cursor=myDb.getAllData();
        StringBuilder stringBuilder=new StringBuilder();
        while (cursor.moveToNext()){
            stringBuilder.append(cursor.getString(1));
        }
        name.setText(stringBuilder);

        r1=findViewById(R.id.r1);
        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Setting.this,ProfileActivity.class);
                startActivity(i);
            }
        });

        //edit
        sr1=findViewById(R.id.sr1);
        sr1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Setting.this,EditActivity.class);
                startActivity(i);
            }
        });

        //setting
        sr2=findViewById(R.id.sr2);
        sr2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Setting.this,EditSetting.class);
                startActivity(i);
            }
        });

        //info
        sr3=findViewById(R.id.sr3);
        sr3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Setting.this,EditInfo.class);
                startActivity(i);
            }
        });


        //bottom navigation
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.settings);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu:
                        startActivity(new Intent(getApplicationContext(), Menu.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.settings:

                        return true;
                    case R.id.home:
                        Intent intent=new Intent(Setting.this,HomeActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;


                }
                return false;
            }
        });
    }

    //back button
    public void onBackPressed(){
        Intent i=new Intent(Setting.this,Menu.class);
        overridePendingTransition(0, 0);
        startActivity(i);
        overridePendingTransition(0, 0);
    }


    //logout exit popup
    public void showExit(View v){
        PopupMenu popup=new PopupMenu(this,v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_exit);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                this.finishAffinity();
                return true;
            case R.id.item2:
                Intent i=new Intent(Setting.this,Login.class);
                startActivity(i);
                return true;
            default:
                return false;
        }
    }
}

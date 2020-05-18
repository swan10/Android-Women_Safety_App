package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Menu extends AppCompatActivity {

    GridLayout g1,g2,g3,g4,g5,g6,g7,g8;
    Emergency eDb;
    public static final String[] s={"P1","P2","P3","P4","P5","P6","P7"};
    private static final int REQUEST_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        eDb=new Emergency(this);

        g1=findViewById(R.id.g2);
        g2=findViewById(R.id.g3);
        g3=findViewById(R.id.g4);
        g4=findViewById(R.id.g5);
        g5=findViewById(R.id.g6);
        g6=findViewById(R.id.g7);
        g7=findViewById(R.id.g8);
        g8=findViewById(R.id.g9);

        //g1
        g1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String res = eDb.getData(s[0]);
                makePhCall(res);
            }
        });
        //g2
        g2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String res = eDb.getData(s[1]);
                makePhCall(res);
            }
        });
        //g3
        g3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String res = eDb.getData(s[2]);
                makePhCall(res);
            }
        });
        //g4
        g4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String res = eDb.getData(s[3]);
                makePhCall(res);
            }
        });
        //g5
        g5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String res = eDb.getData(s[4]);
                makePhCall(res);
            }
        });
        //g6
        g6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String res = eDb.getData(s[5]);
                makePhCall(res);
            }
        });
        //g7
        g7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String res = eDb.getData(s[6]);
                makePhCall(res);
            }
        });

        //bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.menu);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        Intent intent=new Intent(Menu.this,HomeActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.menu:

                        return true;
                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(), Setting.class));
                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            }
        });
    }

    //back button
    public void onBackPressed(){
        Intent i=new Intent(Menu.this,HomeActivity.class);
        startActivity(i);
    }

    //phone call
    private void makePhCall(String res) {
        if (ContextCompat.checkSelfPermission(Menu.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Menu.this,
                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        } else {
            String dial = "tel:" + res;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }

}

package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import static android.Manifest.permission;

public class Menu extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    GridLayout g1,g2,g3,g4,g5,g6,g7,g8;
    Emergency eDb;
    FavContactDB fDb;
    public static final String[] s={"P1","P2","P3","P4","P5","P6","P7","PC1","PC2","PC3"};
    private static final int REQUEST_CALL = 1;
    private static final int REQUEST_SEND_SMS = 1;

    ImageView im1,im2;
    TextView g1im1,g1im2,g1im3,g1tv1,g1tv2,g1tv3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        eDb=new Emergency(this);
        fDb=new FavContactDB(this);

        g1=findViewById(R.id.g1);
        g2=findViewById(R.id.g2);
        g3=findViewById(R.id.g3);
        g4=findViewById(R.id.g4);
        g5=findViewById(R.id.g5);
        g6=findViewById(R.id.g6);
        g7=findViewById(R.id.g7);
        g8=findViewById(R.id.g8);

        im1=findViewById(R.id.im1);
        im2=findViewById(R.id.im2);

        g1im1=findViewById(R.id.g1im1);
        g1im2=findViewById(R.id.g1im2);
        g1im3=findViewById(R.id.g1im3);
        g1tv1=findViewById(R.id.g1tv1);
        g1tv2=findViewById(R.id.g1tv2);
        g1tv3=findViewById(R.id.g1tv3);

        //check contacts are given or not
        checkContact();

        //remove action bar
        getSupportActionBar().hide();

        //fetching contacts
        Cursor cursor=fDb.getAllData();

        cursor.moveToFirst();
        do{
            TextView g1im1=findViewById(R.id.g1im1);
            TextView g1im2=findViewById(R.id.g1im2);
            TextView g1im3=findViewById(R.id.g1im3);
            TextView g1tv1=findViewById(R.id.g1tv1);
            TextView g1tv2=findViewById(R.id.g1tv2);
            TextView g1tv3=findViewById(R.id.g1tv3);
            //g1im1.setText(cursor.getString(cursor.getColumnIndexOrThrow("P1")));
            if(cursor.getString(cursor.getColumnIndexOrThrow("P1")).length()==0){
                return;
            }else{
                g1im1.setText(cursor.getString(cursor.getColumnIndexOrThrow("P1")));
            }
            g1tv1.setText(cursor.getString(cursor.getColumnIndexOrThrow("PC1")));
            //g1im2.setText(cursor.getString(cursor.getColumnIndexOrThrow("P2")));
            if(cursor.getString(cursor.getColumnIndexOrThrow("P2")).length()==0){
                return;
            }else{
                g1im2.setText(cursor.getString(cursor.getColumnIndexOrThrow("P2")));
            }
            g1tv2.setText(cursor.getString(cursor.getColumnIndexOrThrow("PC2")));
            //g1im3.setText(cursor.getString(cursor.getColumnIndexOrThrow("P3")));
            if(cursor.getString(cursor.getColumnIndexOrThrow("P3")).length()==0){
                return;
            }else{
                g1im3.setText(cursor.getString(cursor.getColumnIndexOrThrow("P3")));
            }
            g1tv3.setText(cursor.getString(cursor.getColumnIndexOrThrow("PC3")));
        }while (cursor.moveToNext());

        cursor.close();

        //im1
        im1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Menu.this,EditActivity.class);
                startActivity(i);
            }
        });
        //im2
        im2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Menu.this,EditActivity.class);
                startActivity(i);
            }
        });

        //g1
        g1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String res = eDb.getData(s[0]);
                if(res.length()==0){
                    aletDailog();
                }else{
                    makePhCall(res);
                }
            }
        });
        //g2
        g2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String res = eDb.getData(s[1]);
                if(res.length()==0){
                    aletDailog();
                }else{
                    makePhCall(res);
                }
            }
        });
        //g3
        g3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String res = eDb.getData(s[2]);
                if(res.length()==0){
                    aletDailog();
                }else{
                    makePhCall(res);
                }
            }
        });
        //g4
        g4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String res = eDb.getData(s[3]);
                if(res.length()==0){
                    aletDailog();
                }else{
                    makePhCall(res);
                }
            }
        });
        //g5
        g5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String res = eDb.getData(s[4]);
                if(res.length()==0){
                    aletDailog();
                }else{
                    makePhCall(res);
                }
            }
        });
        //g6
        g6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String res = eDb.getData(s[5]);
                if(res.length()==0){
                    aletDailog();
                }else{
                    makePhCall(res);
                }
            }
        });
        //g7
        g7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String res = eDb.getData(s[6]);
                if(res.length()==0){
                    aletDailog();
                }else{
                    makePhCall(res);
                }
            }
        });
        //g8
        g8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Menu.this, Gov_help_call.class);
                startActivity(i);
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
        overridePendingTransition(0, 0);
        startActivity(i);
        overridePendingTransition(0, 0);
    }

    //check contacts are given or not
    public void checkContact(){
        if(fDb.getCount()==0){
            Intent i=new Intent(Menu.this,Setting.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }else{
            //do nothing
        }
    }

    //phone call
    private void makePhCall(String res) {
        if (ContextCompat.checkSelfPermission(Menu.this,
                permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Menu.this,
                    new String[]{permission.CALL_PHONE}, REQUEST_CALL);
        } else {
            String dial = "tel:" + res;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }

    //popup menu contact
    public void showMenu1(View v){
        PopupMenu popup=new PopupMenu(this,v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.show_menu1);
        popup.show();
    }
    public void showMenu2(View v){
        PopupMenu popup=new PopupMenu(this,v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.show_menu2);
        popup.show();
    }
    public void showMenu3(View v){
        PopupMenu popup=new PopupMenu(this,v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.show_menu3);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.c11:
                String res1 = fDb.getData(s[7]);
                if(res1.length()==0){
                    aletDailog();
                }else{
                    makePhCall(res1);
                }
                return true;
            case R.id.c12:
            case R.id.c13:
                String res12 = fDb.getData(s[7]);
                if(res12.length()==0){
                    aletDailog();
                }else{
                    checkSmsPermission(res12);
                }
                return true;
            case R.id.c21:
                String res2 = fDb.getData(s[8]);
                if(res2.length()==0){
                    aletDailog();
                }else{
                    makePhCall(res2);
                }
                return true;
            case R.id.c22:
            case R.id.c23:
                String res22 = fDb.getData(s[8]);
                if(res22.length()==0){
                    aletDailog();
                }else{
                    checkSmsPermission(res22);
                }
                return true;
            case R.id.c31:
                String res3 = fDb.getData(s[9]);
                if(res3.length()==0){
                    aletDailog();
                }else{
                    makePhCall(res3);
                }
                return true;
            case R.id.c32:
            case R.id.c33:
                String res32 = fDb.getData(s[9]);
                if(res32.length()==0){
                    aletDailog();
                }else{
                    checkSmsPermission(res32);
                }
                return true;
            default:
                return false;
        }
    }
    //alert message for phone number null validation
    public void aletDailog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Information")
                .setMessage("Please Enter a valid Phone number.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent in =new Intent(Menu.this,EditActivity.class);
                        startActivity(in);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Cancel pressed...", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }).show();
    }


    //check for sms permission
    private void checkSmsPermission(String res) {
        if (ContextCompat.checkSelfPermission(Menu.this,
                permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Menu.this,
                    new String[]{permission.SEND_SMS}, REQUEST_SEND_SMS);
        } else {
            makeSms(res);
        }
    }

    //sending sms to intent

    public void makeSms(String s){
        Intent intent=new Intent(Menu.this, Custom_sms.class);
        intent.putExtra("no",s);
        startActivity(intent);
    }
}

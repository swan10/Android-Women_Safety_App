package com.example.myapplication;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.snackbar.Snackbar;

public class Custom_sms extends AppCompatActivity {
    EditText cs2,cs3;
    Button cs4,cs5,csgps;
    private LocationManager locationManager;
    private LocationListener listener;
    private static final int REQUEST_GPS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_sms);

        cs2=findViewById(R.id.cs2);
        cs3=findViewById(R.id.cs3);
        cs4=findViewById(R.id.cs4);
        cs5=findViewById(R.id.cs5);
        csgps=findViewById(R.id.csgps);

        //back button
        getSupportActionBar().setTitle("SMS");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //fetch previous details
        Intent intent=getIntent();
        String no=intent.getStringExtra("no");
        cs2.setText(no);

        //onclick actions

        cs4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Custom_sms.this,Menu.class);
                startActivity(i);
            }
        });

        //add gps onclick
        csgps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkGpsPermission();
            }
        });

        //onclick send sms

        cs5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cs2.getText().toString().length()==0){
                    aletDailog();
                }
                else{
                    String num=cs2.getText().toString().trim();
                    String body=cs3.getText().toString();
                    makeSms(num,body);
                }
            }
        });

        //fetch location
        //location
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                String body = cs3.getText().toString()+"\nhttp://maps.google.com/maps?saddr=" + location.getLatitude()+","+location.getLongitude();
                cs3.setText(body);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
            }

            @Override
            public void onProviderEnabled(String s) {
            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };
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


    //check gps permission
    private void checkGpsPermission(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                        , REQUEST_GPS);
            }
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);
    }

    //send sms
    public void makeSms(String num,String body){
        try{
            SmsManager smgr = SmsManager.getDefault();
            smgr.sendTextMessage(num,null,body,null,null);

            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "SMS Sent Successfully", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        catch (Exception e){
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "SMS Failed to Send, Please try again", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }
}

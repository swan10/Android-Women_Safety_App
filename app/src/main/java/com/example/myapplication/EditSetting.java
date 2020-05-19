package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.PermissionToken;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.SEND_SMS;

public class EditSetting extends AppCompatActivity {
    ImageView bt1,bt2,bt3,bt4;
    TextView tv1,tv2,tv3;
    private static final int REQUEST_PERM = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_setting);
        bt1=findViewById(R.id.es1);
        bt2=findViewById(R.id.es2);
        bt3=findViewById(R.id.es3);
        bt4=findViewById(R.id.es4);
        tv1=findViewById(R.id.et1);
        tv2=findViewById(R.id.et2);
        tv3=findViewById(R.id.et3);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phonePermission();
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smsPermission();
            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gpsPermission();
            }
        });

        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestMultiplePermission();
            }
        });
    }

    private void phonePermission() {
        if (ActivityCompat.checkSelfPermission(this, CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    tv1.setText("Permission Denied");
                    tv1.setTextColor(ContextCompat.getColor(this,R.color.permission_denied));
        }
        else{
            tv1.setText("Permission Granted");
            tv1.setTextColor(ContextCompat.getColor(this,R.color.permission_granted));
        }
    }

    private void smsPermission() {
        if (ActivityCompat.checkSelfPermission(this, SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    tv2.setText("Permission Denied");
                    tv2.setTextColor(ContextCompat.getColor(this,R.color.permission_denied));
        }
        else{
            tv2.setText("Permission Granted");
            tv2.setTextColor(ContextCompat.getColor(this,R.color.permission_granted));
        }
    }

    private void gpsPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                        , REQUEST_PERM);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    tv3.setText("Permission Denied");
                    tv3.setTextColor(ContextCompat.getColor(this,R.color.permission_denied));

                }else{
                    tv3.setText("Permission Granted");
                    tv3.setTextColor(ContextCompat.getColor(this,R.color.permission_granted));
                }
                }
            return;
        }
        else{
            tv3.setText("Permission Granted");
            tv3.setTextColor(ContextCompat.getColor(this,R.color.permission_granted));
        }
    }

    private void RequestMultiplePermission() {

        // Creating String Array with Permissions.
        ActivityCompat.requestPermissions(this, new String[]
                {
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.SEND_SMS,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                }, REQUEST_PERM);
    }

    // Calling override method.
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case REQUEST_PERM:

                if (grantResults.length > 0) {

                    boolean CallPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean PhonePermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean SMSPermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean FineLocationPermission = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    boolean CoarseLocationPermission = grantResults[4] == PackageManager.PERMISSION_GRANTED;
                    boolean InternetPermission = grantResults[5] == PackageManager.PERMISSION_GRANTED;


                    if (CallPermission && PhonePermission && SMSPermission && FineLocationPermission && CoarseLocationPermission && InternetPermission) {

                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "All Permissions are granted....", Snackbar.LENGTH_LONG);
                        snackbar.show();                    }
                    else {
                        alertDialog();                }
                }

                break;
        }
    }

    //Creating CheckingPermissionIsEnabledOrNot() method to check permission is already enable or not
    public boolean CheckingPermissionIsEnabledOrNot() {

        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), CALL_PHONE);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_PHONE_STATE);
        int ThirdPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), SEND_SMS);
        int ForthPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int FifthPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_COARSE_LOCATION);
        int SixthPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), INTERNET);

        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SecondPermissionResult == PackageManager.PERMISSION_GRANTED &&
                ThirdPermissionResult == PackageManager.PERMISSION_GRANTED &&
                ForthPermissionResult == PackageManager.PERMISSION_GRANTED &&
                FifthPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SixthPermissionResult == PackageManager.PERMISSION_GRANTED ;
    }

    //alert message for phone number null validation
    public void alertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissions denied!!!")
                .setMessage("All those permissions are needed for doing some magic")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                })
                .show();
    }
}

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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class Gov_help_call extends AppCompatActivity {
    private static final int REQUEST_CALL = 1;
    EditText editText;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gov_help);
        editText=findViewById(R.id.editTextPhone);
        imageView=findViewById(R.id.call);

        //back button
        getSupportActionBar().setTitle("Government Helplines");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });
    }
    private void makePhoneCall() {
        String number = editText.getText().toString();
        if (number.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(Gov_help_call.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Gov_help_call.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        } else {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Enter a valid phone number", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Permission denied", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }
    }
}
package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class EditInfo extends AppCompatActivity {

    private BottomSheetBehavior bottomSheetBehavior1,bottomSheetBehavior2,bottomSheetBehavior3;
    RelativeLayout r1,r2,r3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);

        r1=findViewById(R.id.edi1);
        r2=findViewById(R.id.edi2);
        r3=findViewById(R.id.edi3);

        View bottomSheet1 = findViewById(R.id.bottom_sheet1);
        bottomSheetBehavior1 = BottomSheetBehavior.from(bottomSheet1);


        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior1.setState(BottomSheetBehavior.STATE_EXPANDED);
                bottomSheetBehavior2.setState(BottomSheetBehavior.STATE_HIDDEN);
                bottomSheetBehavior3.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        View bottomSheet2 = findViewById(R.id.bottom_sheet2);
        bottomSheetBehavior2 = BottomSheetBehavior.from(bottomSheet2);


        r2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                bottomSheetBehavior2.setState(BottomSheetBehavior.STATE_EXPANDED);
                bottomSheetBehavior1.setState(BottomSheetBehavior.STATE_HIDDEN);
                bottomSheetBehavior3.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        View bottomSheet3 = findViewById(R.id.bottom_sheet3);
        bottomSheetBehavior3 = BottomSheetBehavior.from(bottomSheet3);


        r3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                bottomSheetBehavior3.setState(BottomSheetBehavior.STATE_EXPANDED);
                bottomSheetBehavior1.setState(BottomSheetBehavior.STATE_HIDDEN);
                bottomSheetBehavior2.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
    }

}

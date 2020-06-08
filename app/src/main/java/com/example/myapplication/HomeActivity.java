package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.jakewharton.processphoenix.ProcessPhoenix;

import java.util.Objects;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.SEND_SMS;

public class HomeActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private long backPressedTime;
    private static final int REQUEST_PERM = 1;
    private LocationManager locationManager;
    private LocationListener listener;
    FavContactDB fDb;
    public static final String[] s = {"PC1", "PC2", "PC3","NAME"};
    DatabaseHelper myDb;
    ImageView custom_stop;

    Integer d,t,distValue,timeValue;
    String distanceString,timeString,htvs1,htvs2;
    TextView htv1,htv2;


    //sharedpreferences
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String SWITCH1 = "switchEnableButton";
    private boolean switchOnOff;
    private Switch switchEnableButton;

    //shake event
    private SensorManager mSensorManager;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fDb = new FavContactDB(this);
        myDb=new DatabaseHelper(this);
        custom_stop=findViewById(R.id.custom_stop);

        d=0;t=0;
        timeString="Disable";
        distanceString="Disable";
        htv1=findViewById(R.id.htv1);
        htv2=findViewById(R.id.htv2);

        //time and distance
        SharedPreferences sp1 = getSharedPreferences("MYKEY1",0);
        htvs1=sp1.getString("timeStatus",null);
        timeValue=sp1.getInt("time",0);
        SharedPreferences sp2 = getSharedPreferences("MYKEY2",0);
        htvs2=sp2.getString("distanceStatus",null);
        distValue=sp2.getInt("distance",0);
        htv1.setText(htvs1);
        htv2.setText(htvs2);

        //check contacts are given or not
        checkContact();

        //remove action bar
        getSupportActionBar().hide();


        //custom stop




        //bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:

                        return true;
                    case R.id.menu:
                        startActivity(new Intent(getApplicationContext(), Menu.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(), Setting.class));
                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            }
        });

        //onclick functions
        final Button customButton = findViewById(R.id.custom_button);
        switchEnableButton = findViewById(R.id.custom_switch);

        customButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customButton.setVisibility(View.INVISIBLE);
                // Adding if condition inside button.

                // If All permission is enabled successfully then this block will execute.
                if (CheckingPermissionIsEnabledOrNot()) {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Panic button pressed....", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, timeValue, distValue, listener);
                    custom_stop.setVisibility(View.VISIBLE);

                }

                // If, If permission is not enabled then else condition will execute.
                else {

                    //Calling method to enable permission.
                    RequestMultiplePermission();

                }
            }
        });

        switchEnableButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    RequestMultiplePermission();
                    if(CheckingPermissionIsEnabledOrNot()){
                        switchEnableButton.setChecked(true);

                        customButton.setEnabled(true);


                        //shake event
                        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                        Objects.requireNonNull(mSensorManager).registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                                SensorManager.SENSOR_DELAY_NORMAL);
                        mAccel = 10f;
                        mAccelCurrent = SensorManager.GRAVITY_EARTH;
                        mAccelLast = SensorManager.GRAVITY_EARTH;

                    }else{
                        switchEnableButton.setChecked(false);
                    }

                } else {
                    customButton.setEnabled(false);
                }
                saveData();
            }
        });

        //fetch location
        //location
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                String m=myDb.getData(s[3]);
                String loc = m+" is in great Danger.\nPlease Help!!!\nhttp://maps.google.com/maps?saddr=" + location.getLatitude()+","+location.getLongitude();
                fetchContact(loc);
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

        //sharedpreferences
        loadData();
        updateViews();

        //first time check permission
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);
        if (firstStart) {
            RequestMultiplePermission();
        }


    }

    //back press activity
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finish();
            //clear check permissions session data
            SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.commit();

            this.finishAffinity();
            //System.exit(1);


        } else {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Press again to Exit...", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    //check contacts are given or not
    public void checkContact(){
        if(fDb.getCount()==0){
            Intent i=new Intent(HomeActivity.this,Setting.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }else{
            //do nothing
        }
    }


    //fetch contact
    public void fetchContact(String body){
        String message= body;
        String c1 = fDb.getData(s[0]);
        String c2 = fDb.getData(s[1]);
        String c3 = fDb.getData(s[2]);
        if (c1.length()==0||c2.length()==0||c3.length()==0){
            aletDailog();
        }else {
            makeSms(c1,message);
            makeSms(c2,message);
            makeSms(c3,message);
            makeCall(c1);
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
                        Intent in =new Intent(HomeActivity.this,EditActivity.class);
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

    //sms
    //send sms
    public void makeSms(String num,String body){
            try{
                SmsManager smgr = SmsManager.getDefault();
                smgr.sendTextMessage(num,null,body,null,null);
                Toast.makeText(HomeActivity.this,"SMS sent Successfully",Toast.LENGTH_SHORT).show();
            }
            catch (Exception e){
                Toast.makeText(HomeActivity.this,"SMS Failed to Send, Please try again",Toast.LENGTH_SHORT).show();
            }

    }

    //phone call
    private void makeCall(String res) {
        if (ContextCompat.checkSelfPermission(this, CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{CALL_PHONE}, REQUEST_PERM);
        } else {
            String dial = "tel:" + res;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }

    //shake event
    private final SensorEventListener mSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;
            if (mAccel > 12) {
                shake();
            }
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    private void shake() {
        String c1 = fDb.getData(s[0]);
        if (c1.length()==0){
            aletDailog();
        }else {
            makeCall(c1);
        }
    }


    //Creating RequestMultiplePermission() method to request multiple permissions
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
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
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

                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Permission granted....", Snackbar.LENGTH_SHORT);
                        snackbar.show();                    }
                    else {
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Permission denied....", Snackbar.LENGTH_SHORT);
                        snackbar.show();                    }
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

    //custom stop
    public void custom_Exit(View view) {

            ProcessPhoenix.triggerRebirth(HomeActivity.this);

    }

    //switch sharedPreferences
    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SWITCH1, switchEnableButton.isChecked());
        editor.apply();
    }
    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        switchOnOff = sharedPreferences.getBoolean(SWITCH1, false);
    }
    public void updateViews() {
        switchEnableButton.setChecked(switchOnOff);
    }

    //show timer
    public void showTime(View v){
        PopupMenu popup=new PopupMenu(this,v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.show_clock);
        popup.show();
    }

    //show distance
    public void showDistance(View v){
        PopupMenu popup=new PopupMenu(this,v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.show_dist);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        d=0;t=0;
        timeString="Disable";
        distanceString="Disable";
        switch (item.getItemId()) {
            case R.id.clock1:
                t=0;
                timeString="Disable";
                htv1.setText("Disable");
                timeSharedPref();
                return true;
            case R.id.clock2:
                t=60*100;
                timeString="1 Minute";
                htv1.setText("1 Minute");
                timeSharedPref();
                return true;
            case R.id.clock3:
                t=180*100;
                timeString="3 Minute";
                htv1.setText("3 Minute");
                timeSharedPref();
                return true;
            case R.id.clock4:
                timeString="5 Minute";
                t=300*100;
                htv1.setText("5 Minute");
                timeSharedPref();
                return true;
            case R.id.clock5:
                timeString="7 Minute";
                t=420*100;
                htv1.setText("7 Minute");
                timeSharedPref();
                return true;
            case R.id.clock6:
                timeString="10 Minute";
                t=600*100;
                htv1.setText("10 Minute");
                timeSharedPref();
                return true;
            case R.id.clock7:
                timeString="15 Minute";
                t=900*100;
                htv1.setText("15 Minute");
                timeSharedPref();
                return true;
            case R.id.dist1:
                d=0;
                distanceString="Disable";
                htv2.setText("Disable");
                distanceSharedPref();
                return true;
            case R.id.dist2:
                d=5;
                distanceString="5 Meter";
                htv2.setText("5 Meter");
                distanceSharedPref();
                return true;
            case R.id.dist3:
                d=10;
                distanceString="10 Meter";
                htv2.setText("10 Meter");
                distanceSharedPref();
                return true;
            case R.id.dist4:
                d=20;
                distanceString="20 Meter";
                htv2.setText("20 Meter");
                distanceSharedPref();
                return true;
            case R.id.dist5:
                d=30;
                distanceString="30 Meter";
                htv2.setText("30 Meter");
                distanceSharedPref();
                return true;
            case R.id.dist6:
                d=40;
                distanceString="40 Meter";
                htv2.setText("40 Meter");
                distanceSharedPref();
                return true;
            case R.id.dist7:
                d=50;
                distanceString="50 Meter";
                htv2.setText("50 Meter");
                distanceSharedPref();
                return true;
            default:
                return false;
        }
    }
    public void timeSharedPref(){
        SharedPreferences sp1 = getSharedPreferences("MYKEY1",0);
        SharedPreferences.Editor editor = sp1.edit();
        editor.putString("timeStatus" , timeString);
        editor.putInt("time",t);
        editor.apply();
        editor.commit();
    }
    public void distanceSharedPref(){
        SharedPreferences sp2 = getSharedPreferences("MYKEY2",0);
        SharedPreferences.Editor editor = sp2.edit();
        editor.putString("distanceStatus" , distanceString);
        editor.putInt("distance",d);
        editor.apply();
        editor.commit();
    }
}

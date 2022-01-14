package com.example.star;

import static java.lang.Math.round;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;



import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Run extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;

    private double distance = 0.0;
    private FusedLocationProviderClient fusedLocationClient;
    private GPSTracker gps;
    Location location;
    double totalDistance = 0;
    double speed = 0;
    double avrSpeed = 0;
    ArrayList<Location> locations = new ArrayList<Location>();
    boolean stop = false;
    TextView speedView;
    TextView distanceView;
    TextView avrSpeedView;
    Handler handler = new Handler();
    Runnable runnable;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    int delay = 1000; //Delay for 15 seconds.  One second = 1000 milliseconds.
    long startStampSeconds;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_run);
        super.onCreate(savedInstanceState);
        speedView = (TextView) findViewById(R.id.speed);
        distanceView = (TextView) findViewById(R.id.distance);
        avrSpeedView = (TextView) findViewById(R.id.avrspeed);
        gps = new GPSTracker(this);
        distance = 0.0;
        Instant instant = Instant.now();
        startStampSeconds = instant.getEpochSecond();
        locations.add(gps.getLocation());





    }




    @Override
    protected void onResume() {
        //start handler as activity become visible
        if(stop == false) {
            if (checkLocationPermission()) {
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    handler.postDelayed(runnable = new Runnable() {

                        @RequiresApi(api = Build.VERSION_CODES.O)
                        public void run() {


                            handler.postDelayed(runnable, delay);

                            try {
                                caluclateDistance();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }, delay);
                }
            }
        }

        super.onResume();
    }

// If onPause() is not included the threads will double up when you
// reload the activity

    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable); //stop handler when activity not visible
        super.onPause();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void caluclateDistance() throws InterruptedException {
            Location p1 = locations.get(locations.size() - 1);
            Location p2 = gps.getLocation();
            locations.add(p2);
            double distance=p1.distanceTo(p2);
            totalDistance =Math.round( (totalDistance + distance)* 100.0) / 100.0;
            speed = Math.round(distance * 100.0) / 100.0;
            speedView.setText(String.valueOf(speed)+" m/s");
            distanceView.setText(String.valueOf(totalDistance)+" m");
             Instant instant = Instant.now();
             if(totalDistance!=0) {
                 avrSpeed =   totalDistance/(instant.getEpochSecond() - startStampSeconds);

             }
             avrSpeed = Math.round(avrSpeed * 100.0) / 100.0;
             avrSpeedView.setText(String.valueOf(avrSpeed)+" m/s");



    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void stop(View view) {
        stop = true;

    }



    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission. ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission. ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Lack of permisions!")
                        .setMessage("Grant permission")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(Run.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission. ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission. ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {


                        // do your stuff here
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }
}
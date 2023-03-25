package com.trystar.keepincheck.Worker;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.trystar.keepincheck.Attendance.ViewAttendance;
import com.trystar.keepincheck.R;
import com.trystar.keepincheck.login;
import com.trystar.keepincheck.mapfiles.LocationHelper;
import com.trystar.keepincheck.mapfiles.MapsActivity;

public class BottomNav extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, LocationListener {

    BottomNavigationView bottomNavigationView;
    android.os.Handler customHandler = new android.os.Handler();
    Location lastKnownLocation;
    FirebaseUser user;
    String uid;
    JobAssigned firstFragment = new JobAssigned();
    WorkerProfile secondFragment = new WorkerProfile();
    ViewAttendance thirdFragment = new ViewAttendance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.item3);

        //For Saving Location in Firebase
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        customHandler.postDelayed(updateTimerThread, 0);
        onLocationChanged(lastKnownLocation);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item3:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, firstFragment).commit();
                return true;

            case R.id.item1:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, secondFragment).commit();
                return true;

            case R.id.item2:
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    String mobile = user.getPhoneNumber();
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("Worker detail")
                            .whereEqualTo("Phone Number",mobile)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            String name =document.getString("Name");
                                            getSupportFragmentManager().beginTransaction().replace(R.id.container, thirdFragment).commit();
                                            Intent intent = new Intent(BottomNav.this, ViewAttendance.class);
                                            intent.putExtra("name",  name);
                                            startActivity(intent);
                                        }
                                    } else {
                                        Toast.makeText(BottomNav.this,"error",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else {
                    Toast.makeText(BottomNav.this, "error", Toast.LENGTH_SHORT).show();
                }

                return true;

            case R.id.item4:
                startActivity(new Intent(BottomNav.this, MapsActivity.class));
                return true;

            case R.id.item5:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(BottomNav.this, login.class));
                Toast.makeText(BottomNav.this, "You have logged out", Toast.LENGTH_SHORT).show();
                return true;

        }
        return false;
    }

    private final Runnable updateTimerThread = new Runnable() {
        public void run() {
            onLocationChanged(lastKnownLocation);
            customHandler.postDelayed(this, 10000);
        }
    };


    @Override
    public void onLocationChanged(@NonNull Location location) {
        LocationHelper helper = new LocationHelper(

                location.getLongitude(), location.getLatitude()
        );
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getPhoneNumber();
        }
        FirebaseDatabase.getInstance().getReference(uid)
                .setValue(helper).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                    } else {
                        Toast.makeText(BottomNav.this, "Location Not Saved", Toast.LENGTH_SHORT).show();
                    }

                });
    }



    @Override
    public void onFlushComplete(int requestCode) {
        LocationListener.super.onFlushComplete(requestCode);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }
}
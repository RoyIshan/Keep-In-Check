package com.trystar.keepincheck;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.trystar.keepincheck.mapfiles.MapsActivity;

public class WorkerDashboard extends AppCompatActivity implements LocationListener {



    private LocationManager locationManager;

    String uid;
    FirebaseUser user;
    Button signOutwr;
    Button showloc;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_dashboard);

        showloc=findViewById(R.id.showbtn);
        showloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent =new Intent(WorkerDashboard.this,MapsActivity.class);
                startActivity(myIntent);

            }
        });


        signOutwr=findViewById(R.id.signOutwr);

        signOutwr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(WorkerDashboard.this,SelectIdentity.class));
                Toast.makeText(WorkerDashboard.this,"Signing Out",Toast.LENGTH_SHORT).show();


            }
        });




    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        onLocationChanged(lastKnownLocation);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        double longitude=location.getLongitude();
        double latitude=location.getLatitude();


        LocationHelper helper= new LocationHelper(

                location.getLongitude(), location.getLatitude()
        );
        OnCompleteListener<Void> onCompleteListener;
       user=FirebaseAuth.getInstance().getCurrentUser();
        if(user != null)
       {
           uid=user.getPhoneNumber();
      }
        FirebaseDatabase.getInstance().getReference(uid)
                .setValue(helper).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(WorkerDashboard.this, "Locations Saved", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(WorkerDashboard.this, "Location Not Saved", Toast.LENGTH_SHORT).show();
                        }

                    }

                });
    }


    @Override
    public void onProviderDisabled(String provider) {


    }





    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled( String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }


    public class LocationHelper {

        private double longitude;
        private double latitude;

        public LocationHelper(double longitude, double latitude) {
            this.longitude = longitude;
            this.latitude = latitude;
        }

        public double getLongitude() {

            return longitude;
        }

        public void setLongitude(double longitude) {

            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }


    }

}


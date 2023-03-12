package com.trystar.keepincheck.mapfiles;

import static android.content.ContentValues.TAG;
import static android.widget.Toast.LENGTH_LONG;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.trystar.keepincheck.R;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Map<String, Object> user = new HashMap<>();
    List<Location> list= new ArrayList<>();
    ViewPager viewPager;
    List<Marker> markerList= new ArrayList<>();
    ViewPagerAdapter viewPagerAdapter;
    int oldPosition =0;
    ArrayList<Model> datalist;
    FirebaseFirestore db;
    DatabaseReference databaseReference;
    double longitude,latitude;
    String  phone,name;
    List<Location> Locationlist ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_worker);
        Locationlist= new ArrayList<Location>();
        //Locationlist.add(new Location(phone, new LatLng(22.9574946, 76.0371021)));
        datalist =new ArrayList<Model>();
        db=FirebaseFirestore.getInstance();
        db.collection("Worker detail")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                phone=document.getString("Phone Number");

                                DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child(phone);
                                reff.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        try {
                                            String num=phone;
                                            latitude = snapshot.child("latitude").getValue(Double.class);
                                            longitude = snapshot.child("longitude").getValue(Double.class);

                                            Toast.makeText(MapsActivity.this,"vasu"+latitude+longitude,Toast.LENGTH_SHORT).show();


                                            Locationlist.add(new Location("Location", new LatLng(latitude, longitude)));
                                            //list=getLocationList();
                                            initMarkers();
                                            viewPagerAdapter= new ViewPagerAdapter(getSupportFragmentManager(),Locationlist);
                                            viewPager.setAdapter(viewPagerAdapter);
                                            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                                @Override
                                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                                                }

                                                @Override
                                                public void onPageSelected(int position) {
                                                    updateView(position);

                                                }

                                                @Override
                                                public void onPageScrollStateChanged(int state) {

                                                }
                                            });

                                        }
                                        catch (Exception e)
                                        {
                                            Toast.makeText(MapsActivity.this,e.getMessage(), LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(MapsActivity.this,"Data not Found", LENGTH_LONG).show();
                                    }
                                });
                            }
                        } else {

                        }
                    }
                });




        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment =(SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        viewPager=findViewById(R.id.viewPager);
        viewPager.setPageMargin(15);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
 /*       for (int i=0 ;i<datalist.size();i++){
            phone=datalist.get(i).getPhone();
            //add string value
        databaseReference= FirebaseDatabase.getInstance().getReference().child(phone);
        ValueEventListener listener =databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                latitude=snapshot.child("latitude").getValue(double.class);
                longitude=snapshot.child("longitude").getValue(double.class);
                Toast.makeText(MapsActivity.this,"vasu",Toast.LENGTH_SHORT).show();
                Log.d(TAG,"OnLocationSaved"+latitude);
                Log.d(TAG,"OnLocationSaved"+longitude);

                Locationlist.add(new Location(phone, new LatLng(latitude, longitude)));
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

            //use get location list


        });
        }*/
        mMap = googleMap;


    }
//view pager for scrolling
    private void updateView(int position) {
        markerList.get(position).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_selected));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Locationlist.get(position).getLatLng(), 15));


        markerList.get(oldPosition).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_unselected));
        oldPosition = position;
    }
//location markers
    private void initMarkers() {

        for (int i = 0; i < Locationlist.size(); i++) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(Locationlist.get(i).getLatLng());
            if (i == 0)
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_selected));
            else
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_unselected));
            markerList.add(mMap.addMarker(markerOptions));
        }

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Locationlist.get(0).getLatLng(), 15));

    }

    //random locations
    //change it to worker location
//    public void getLocationList() {

  //      Locationlist.add(new Location(phone, new LatLng(latitude, longitude)));

  //  }


}
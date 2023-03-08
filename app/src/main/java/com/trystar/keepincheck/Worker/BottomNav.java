package com.trystar.keepincheck.Worker;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.trystar.keepincheck.R;
import com.trystar.keepincheck.login;
import com.trystar.keepincheck.mapfiles.MapsActivity;

public class BottomNav extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener  {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.item3);

    }
    JobAssigned firstFragment = new JobAssigned();
    WorkerProfile secondFragment = new WorkerProfile();

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
                getSupportFragmentManager().beginTransaction().replace(R.id.container, firstFragment).commit();
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
}

package com.trystar.keepincheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectIdentity extends AppCompatActivity {

    Button owner,worker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_identity);

        owner = findViewById(R.id.Owner);
        worker = findViewById(R.id.Worker);

        owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectIdentity.this,OwnerRegister.class));
            }
        });
        worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectIdentity.this,Register.class));
            }
        });
    }
}
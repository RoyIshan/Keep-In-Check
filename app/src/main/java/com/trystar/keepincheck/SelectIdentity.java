package com.trystar.keepincheck;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.trystar.keepincheck.OwnerPart.OwnerRegister;
import com.trystar.keepincheck.WorkerPart.Register;

public class SelectIdentity extends AppCompatActivity {

    Button owner,worker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_identity);

        owner = findViewById(R.id.Owner);
        worker = findViewById(R.id.Worker);

        try {
            owner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        startActivity(new Intent(SelectIdentity.this, OwnerRegister.class));
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(SelectIdentity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            worker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        startActivity(new Intent(SelectIdentity.this, Register.class));
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(SelectIdentity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
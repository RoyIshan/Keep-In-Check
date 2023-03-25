package com.trystar.keepincheck;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.trystar.keepincheck.Owner.OwnerRegister;
import com.trystar.keepincheck.Worker.Register;

public class SelectIdentity extends AppCompatActivity {

    Button owner,worker,login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_identity);

        owner = findViewById(R.id.Owner);
        worker = findViewById(R.id.Worker);
        login = findViewById(R.id.gotoLogin);
        login.setOnClickListener(view -> startActivity(new Intent(SelectIdentity.this, login.class)));
        try {
            owner.setOnClickListener(view -> {
                try {
                    startActivity(new Intent(SelectIdentity.this, OwnerRegister.class));
                }
                catch (Exception e)
                {
                    Toast.makeText(SelectIdentity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            worker.setOnClickListener(view -> {
                try {
                    startActivity(new Intent(SelectIdentity.this, Register.class));
                }
                catch (Exception e)
                {
                    Toast.makeText(SelectIdentity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
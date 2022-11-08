package com.trystar.keepincheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    EditText number,name,inviteCode;
    Button btn;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fAuth = FirebaseAuth.getInstance();
        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        number=(EditText)findViewById(R.id.number);
        name = findViewById(R.id.name);
        btn = (Button) findViewById(R.id.btn);
        inviteCode = findViewById(R.id.invite);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = inviteCode.getText().toString();
                try{
                if(code.equals("1234")) {
                    Intent intent = new Intent(Register.this, Otp.class);
                    intent.putExtra("mobile", "+91" + number.getText().toString());
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(Register.this,"Invalide invite code",Toast.LENGTH_SHORT).show();
                }
                }catch (Exception e)
                {
                    Toast.makeText(Register.this,e.getMessage().toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
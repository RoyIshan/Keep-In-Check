package com.trystar.keepincheck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class OwnerRegister extends AppCompatActivity {

    EditText number,name,inviteCode,companyName;
    Button btn;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_register);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        number=findViewById(R.id.number);
        name = findViewById(R.id.name);
        btn =  findViewById(R.id.btn);
        inviteCode = findViewById(R.id.invite);
        companyName = findViewById(R.id.cName);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = inviteCode.getText().toString();
                String Name = name.getText().toString();
                String cName = companyName.getText().toString();
                String mNumber = number.getText().toString();
                if(code.length() != 6)
                {
                    inviteCode.setError("Invite code should be length of 6");
                    inviteCode.requestFocus();
                }
                if(Name.length()<1){
                    name.setError("Name can not be NULL");
                    name.requestFocus();
                }
                if(cName.length()<1){
                    companyName.setError("Company Name can not be NULL");
                    companyName.requestFocus();
                }
                if (mNumber.length() != 10)
                {
                    number.setError("Length of Number should be 10");
                    number.requestFocus();
                }
                if(code.length()==6 && Name.length()>1 && cName.length()>1)
                {
                    if(mNumber.length() == 10)
                    {
                        saveOnFirestore();
                        Intent intent = new Intent(OwnerRegister.this, Otp.class);
                        intent.putExtra("mobile", "+91" + mNumber);
                        startActivity(intent);
                    }
                }
            }

            private void saveOnFirestore() {
                Map<String, Object> user = new HashMap<>();
                user.put("Name", name.getText().toString());
                user.put("Company Name", companyName.getText().toString());
                user.put("Phone Number", number.getText().toString());
                user.put("Invite Code",inviteCode.getText().toString());

                // Add a new document with a generated ID
                db.collection("Owner detail")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(OwnerRegister.this,"Data added", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(OwnerRegister.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
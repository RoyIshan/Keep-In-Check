package com.trystar.keepincheck.Worker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.trystar.keepincheck.R;

public class WorkerProfile extends AppCompatActivity {

    String mobile;
    TextView name,phoneNumber,companyCode,companyName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_profile);
        name = findViewById(R.id.ownerName);
        phoneNumber = findViewById(R.id.ownerPhoneNumber);
        companyCode = findViewById(R.id.ownerCC);
        companyName = findViewById(R.id.ownerCN);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            mobile = user.getPhoneNumber();
            Toast.makeText(WorkerProfile.this,mobile,Toast.LENGTH_SHORT).show();
        }
        updateProfile();
    }

    private void updateProfile() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Worker detail")
                .whereEqualTo("Phone Number",mobile)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                name.setText(document.getString("Name"));
                                phoneNumber.setText(mobile);
                                companyCode.setText(document.getString("Invite Code"));
                                companyName.setText(document.getString("Company Name"));
                                Toast.makeText(WorkerProfile.this,"chal ra",Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(WorkerProfile.this,"error",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
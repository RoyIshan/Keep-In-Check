package com.trystar.keepincheck.Owner;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.trystar.keepincheck.R;

public class EditProfile extends AppCompatActivity {

    private static final String TAG = "EditProfile";

    Button editProfileSubmit;
    EditText updateName, updateCompanyName, updateCompanyCode;
    TextView viewPhoneNumber;
    String mobile;
    String newName, newCompanyName, newCompanyCode;
    String oldName, oldCompanyName, oldCompanyCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        editProfileSubmit = findViewById(R.id.editProfileSubmit);
        updateName = findViewById(R.id.updateName);
        viewPhoneNumber = findViewById(R.id.viewPhoneNumber);
        updateCompanyName = findViewById(R.id.updateCompanyName);
        updateCompanyCode = findViewById(R.id.updateCompanyCode);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            mobile = user.getPhoneNumber();
            Toast.makeText(EditProfile.this,mobile,Toast.LENGTH_SHORT).show();
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Owner detail")
                .whereEqualTo("Phone Number",mobile)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                updateName.setText(document.getString("Name"));
                                oldName = document.getString("Name");

                                viewPhoneNumber.setText(mobile);

                                updateCompanyName.setText(document.getString("Company Name"));
                                oldCompanyName = document.getString("Company Name");

                                updateCompanyCode.setText(document.getString("Invite Code"));
                                oldCompanyCode = document.getString("Invite Code");
                            }
                        }
                    }
                });

        editProfileSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newName = updateName.getText().toString();
                db.collection("Owner detail")
                        .whereEqualTo("Name", oldName)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        db.collection("Owner detail")
                                                .document(document.getId())
                                                .update("Name", newName);
                                    }
                                    Toast.makeText(EditProfile.this, "Profile has been updated",Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(EditProfile.this,"Error",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                newCompanyName = updateCompanyName.getText().toString();
                db.collection("Owner detail")
                        .whereEqualTo("Company Name", oldCompanyName)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        db.collection("Owner detail")
                                                .document(document.getId())
                                                .update("Company Name", newCompanyName);
                                    }
                                }
                            }
                        });

                newCompanyCode = updateCompanyCode.getText().toString();
                db.collection("Owner detail")
                        .whereEqualTo("Invite Code", oldCompanyCode)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        db.collection("Owner detail")
                                                .document(document.getId())
                                                .update("Invite Code", newCompanyCode);
                                    }
                                }
                            }
                        });
            }
        });
    }
}

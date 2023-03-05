package com.trystar.keepincheck.Worker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class WorkerProfile extends Fragment {

    String mobile;
    TextView name,phoneNumber,companyCode,companyName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View views = inflater.inflate(R.layout.activity_worker_profile, container, false);

        name = views.findViewById(R.id.ownerName);
        phoneNumber = views.findViewById(R.id.ownerPhoneNumber);
        companyCode = views.findViewById(R.id.ownerCC);
        companyName = views.findViewById(R.id.ownerCN);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            mobile = user.getPhoneNumber();
            Toast.makeText(getContext(),mobile,Toast.LENGTH_SHORT).show();
        }
        updateProfile();
        return views;

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
                                Toast.makeText(getActivity(),"chal ra",Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(),"error",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
package com.trystar.keepincheck.Owner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.trystar.keepincheck.Attendance.OwnerViewAttendance;
import com.trystar.keepincheck.Attendance.ViewAttendance;
import com.trystar.keepincheck.R;

import java.util.ArrayList;

public class WorkerList extends AppCompatActivity {

    ListView wList;
    ArrayAdapter<String> adapter;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_list);

        wList = findViewById(R.id.workerList);
        db = FirebaseFirestore.getInstance();
        adapter = new ArrayAdapter<>(this,
                R.layout.workerlist_resource, R.id.textView2,new ArrayList<String>());
        wList.setAdapter(adapter);
        db.collection("Worker detail")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                adapter.add(document.getString("Name"));
                            }
                        } else {

                        }
                    }
                });
        wList.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = (String) parent.getItemAtPosition(position);
            Intent intent = new Intent(WorkerList.this, OwnerViewAttendance.class);
            intent.putExtra("name", selectedItem);
            startActivity(intent);
            //textView.setText("The best football player is : " + selectedItem);
        });
    }
}
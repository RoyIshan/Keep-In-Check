package com.trystar.keepincheck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.trystar.keepincheck.Worker.WorkerGiveAttendance;

import java.util.ArrayList;

public class ViewAttendance extends AppCompatActivity {

    ListView attendanceView;
    String name,date;
    Long status;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);

        name = getIntent().getStringExtra("name");

        attendanceView  = findViewById(R.id.attendanceView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,new ArrayList<String>());
        attendanceView.setAdapter(adapter);
        Toast.makeText(ViewAttendance.this,"name :"+name,Toast.LENGTH_SHORT).show();
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        db.collection("task")
                .whereEqualTo("worker",name)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                //taskName.setText(document.getString("task"));
                                date = document.getString("deadline");
                                //cNumber.setText(document.getString("number"));
                               // cAddress.setText(document.getString("address"));
                                //vcode = document.getString("code");
                                status =  document.getLong("status");
                                adapter.add(document.getString("deadline"));
                                Toast.makeText(ViewAttendance.this,"name :"+status,Toast.LENGTH_SHORT).show();
                            }
                        } else {

                        }
                    }
                });
    }
}
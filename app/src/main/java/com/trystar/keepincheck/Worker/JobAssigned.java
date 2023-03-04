package com.trystar.keepincheck.Worker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.trystar.keepincheck.R;
import com.trystar.keepincheck.WorkerGiveAttendance;

import java.util.ArrayList;
import java.util.HashMap;

public class JobAssigned extends AppCompatActivity {

    ListView listView;
    ArrayList<HashMap<String, Object>> list;
    ArrayAdapter<String> adapter;
    //ArrayList<Worker> workerArrayList;
    //WorkerAdapter workerAdapter;
    FirebaseFirestore db;
    String mobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checking);

        listView = findViewById(R.id.taskList);
        list = new ArrayList<>();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            mobile = user.getPhoneNumber();
            Toast.makeText(JobAssigned.this, mobile, Toast.LENGTH_SHORT).show();
        }

        db = FirebaseFirestore.getInstance();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,new ArrayList<String>());
        listView.setAdapter(adapter);
        db.collection("Worker detail")
                .whereEqualTo("Phone Number",mobile)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String name = document.getString("Name");
                                Toast.makeText(JobAssigned.this,name,Toast.LENGTH_SHORT).show();
                                db.collection("task")
                                        .whereEqualTo("worker",name)
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                        adapter.add(document.getString("task"));
                                                        Toast.makeText(JobAssigned.this,"chalu hai",Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {

                                                }
                                            }
                                        });
                            }
                        } else {

                        }
                    }
                });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                Toast.makeText(JobAssigned.this,selectedItem,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(JobAssigned.this, WorkerGiveAttendance.class);
                intent.putExtra("task", selectedItem);
                startActivity(intent);
                //textView.setText("The best football player is : " + selectedItem);
            }
        });
    }
}
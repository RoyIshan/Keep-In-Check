package com.trystar.keepincheck.Worker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.trystar.keepincheck.Owner.WorkerList;
import com.trystar.keepincheck.R;
import com.trystar.keepincheck.SelectIdentity;
import com.trystar.keepincheck.mapfiles.MapsActivity;

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
        setContentView(R.layout.activity_job_assign);

        listView = findViewById(R.id.taskList);
        list = new ArrayList<>();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            mobile = user.getPhoneNumber();
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
                Intent intent = new Intent(JobAssigned.this, WorkerGiveAttendance.class);
                intent.putExtra("task", selectedItem);
                startActivity(intent);
                //textView.setText("The best football player is : " + selectedItem);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_worker, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item1:
                Toast.makeText(getApplicationContext(), "View Profile", Toast.LENGTH_LONG).show();
                startActivity(new Intent(JobAssigned.this, WorkerProfile.class));
                return true;
            case R.id.item2:
                try {
                    startActivity(new Intent(JobAssigned.this, WorkerList.class));
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.item3:
                Toast.makeText(getApplicationContext(), "Item 3 Selected", Toast.LENGTH_LONG).show();
                startActivity(new Intent(JobAssigned.this, JobAssigned.class));
                return true;
            case R.id.item4:
                Toast.makeText(getApplicationContext(), "show location", Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(JobAssigned.this, MapsActivity.class);
                startActivity(myIntent);
                return true;
            case R.id.item5:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(JobAssigned.this, SelectIdentity.class));
                Toast.makeText(JobAssigned.this, "Signing Out", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
package com.trystar.keepincheck.Worker;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.trystar.keepincheck.R;

import java.util.ArrayList;
import java.util.HashMap;

public class JobAssigned extends Fragment {

    ListView listView;
    ArrayList<HashMap<String, Object>> list;
    ArrayAdapter<String> adapter;
    //ArrayList<Worker> workerArrayList;
    //WorkerAdapter workerAdapter;
    FirebaseFirestore db;
    String mobile;

    public JobAssigned(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View views = inflater.inflate(R.layout.activity_job_assign, container, false);

        listView = views.findViewById(R.id.taskList);
        list = new ArrayList<>();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            mobile = user.getPhoneNumber();
        }

        db = FirebaseFirestore.getInstance();
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,new ArrayList<String>());
        listView.setAdapter(adapter);
        db.collection("Worker detail")
                .whereEqualTo("Phone Number",mobile)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String name = document.getString("Name");
                            //Toast.makeText(JobAssigned.this,name,Toast.LENGTH_SHORT).show();
                            db.collection("task")
                                    .whereEqualTo("worker",name)
                                    .get()
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            for (QueryDocumentSnapshot document1 : task1.getResult()) {
                                                adapter.add(document1.getString("task"));
                                            }
                                        }
                                    });
                        }
                    }
                });
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = (String) parent.getItemAtPosition(position);
            Intent intent = new Intent(getActivity(), WorkerGiveAttendance.class);
            intent.putExtra("task", selectedItem);
            startActivity(intent);
            //textView.setText("The best football player is : " + selectedItem);
        });
        return views;
    }
}
package com.trystar.keepincheck.Attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.trystar.keepincheck.R;

import java.util.ArrayList;

public class OwnerViewAttendance extends AppCompatActivity {

    ListView attendanceView;
    String name,date;
    Long status;
    MyListAdapter adapter;
    ArrayList<MyItem> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);

        name = getIntent().getStringExtra("name");
        items = new ArrayList<>();

        //items.add(new MyItem("date", "Present"));
        attendanceView  = findViewById(R.id.attendanceView);
        items.add(new MyItem("Date", "Status"));

        adapter = new MyListAdapter(this, items);

        Toast.makeText(OwnerViewAttendance.this,"name :"+name,Toast.LENGTH_SHORT).show();
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
                                if(status==0)
                                {
                                    items.add(new MyItem(date, "Present"));
                                }
                                else{
                                    items.add(new MyItem(date, "Absent"));
                                }
                                Toast.makeText(OwnerViewAttendance.this,"name :"+status,Toast.LENGTH_SHORT).show();
                                attendanceView.setAdapter(adapter);
                            }
                        } else {

                        }
                    }
                });

    }
}
package com.trystar.keepincheck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class WorkerGiveAttendance extends AppCompatActivity {

    String task;
    TextView taskName,deadline,cAddress,cNumber;
    EditText code;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_give_attendance);

        task = getIntent().getStringExtra("task");
        taskName = findViewById(R.id.taskName);
        deadline = findViewById(R.id.deadline);
        cAddress = findViewById(R.id.address);
        cNumber = findViewById(R.id.number);
        code = findViewById(R.id.code);
        button = findViewById(R.id.button);

        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        db.collection("task")
                .whereEqualTo("task",task)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                taskName.setText(document.getString("task"));
                                deadline.setText(document.getString("deadline"));
                                cNumber.setText(document.getString("number"));
                                cAddress.setText(document.getString("address"));
                                Toast.makeText(WorkerGiveAttendance.this,"chalu hai",Toast.LENGTH_SHORT).show();
                            }
                        } else {

                        }
                    }
                });
    }
}
package com.trystar.keepincheck.Worker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.trystar.keepincheck.R;

public class WorkerGiveAttendance extends AppCompatActivity {

    String task,vcode;
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
                                vcode = document.getString("code");
                            }
                        } else {

                        }
                    }
                });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String verificationCode = code.getText().toString();
                if(verificationCode.equals(vcode))
                {
                    Toast.makeText(WorkerGiveAttendance.this,"attendance recorded",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(WorkerGiveAttendance.this,"Wrong Code",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
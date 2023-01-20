package com.trystar.keepincheck.OwnerPart;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.trystar.keepincheck.R;

public class WorkerList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_list);
        RecyclerView wList = findViewById(R.id.workerList);
    }
}
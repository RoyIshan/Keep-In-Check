package com.trystar.keepincheck.Attendance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.trystar.keepincheck.R;

import java.util.ArrayList;

public class ViewAttendance extends Fragment {

    ListView attendanceView;
    String name,date;
    Long status;
    MyListAdapter adapter;
    ArrayList<MyItem> items;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View views = inflater.inflate(R.layout.activity_view_attendance, container, false);

        name = getActivity().getIntent().getStringExtra("name");
        items = new ArrayList<>();

        //items.add(new MyItem("date", "Present"));
        attendanceView  = views.findViewById(R.id.attendanceView);
        items.add(new MyItem("Date", "Status"));

        adapter = new MyListAdapter(getContext(), items);

        Toast.makeText(getContext(),"name :"+name,Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(getContext(),"name :"+status,Toast.LENGTH_SHORT).show();
                                attendanceView.setAdapter(adapter);
                            }
                        }
                    }
                });

        return views;
    }
}

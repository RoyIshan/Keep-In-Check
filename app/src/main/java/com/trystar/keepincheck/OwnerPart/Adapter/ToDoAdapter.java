package com.trystar.keepincheck.OwnerPart.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.trystar.keepincheck.OwnerPart.AssignTask;
import com.trystar.keepincheck.OwnerPart.Model.ToDoModel;
import com.trystar.keepincheck.R;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {

    private List<ToDoModel> todoList;
    private AssignTask activity;
    private FirebaseFirestore firestore;
    private View view;
    FirebaseFirestore db;

    public ToDoAdapter(AssignTask assignTask, List<ToDoModel> todoList){
        this.todoList = todoList;
        activity = assignTask;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(activity).inflate(R.layout.each_task, parent, false);
        firestore = FirebaseFirestore.getInstance();
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ToDoModel toDoModel = todoList.get(position);
        holder.wCheckBox.setText(toDoModel.getTask());
        holder.wDeadline.setText("Deadline: " + toDoModel.getDeadline());
        String s = holder.wCheckBox.getText().toString();

        db = FirebaseFirestore.getInstance();
        db.collection("task")
                .whereEqualTo("task",s)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                document.getString("Name");
                                holder.wAssignedWorker.setText("Worker: " + document.getString("worker"));
                            }
                        } else {

                        }
                    }
                });
        //holder.wCheckBox.setChecked(toBoolean(toDoModel.getStatus()));
        /*holder.wCheckBox.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked){
                firestore.collection("task").document(toDoModel.TaskId).update("status", 1);
            }else{
                firestore.collection("task").document(toDoModel.TaskId).update("status", 0);
            }
        });*/
    }

    private boolean toBoolean(int status){
        return status != 0;
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView wDeadline, wCheckBox, wAssignedWorker;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            wDeadline = itemView.findViewById(R.id.deadline);
            wCheckBox = itemView.findViewById(R.id.mcheckbox);

            wAssignedWorker = itemView.findViewById(R.id.worker);
        }
    }
}

package com.trystar.keepincheck.OwnerPart.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.trystar.keepincheck.OwnerPart.AssignTask;
import com.trystar.keepincheck.OwnerPart.Model.ToDoModel;
import com.trystar.keepincheck.R;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {

    private List<ToDoModel> todoList;
    private AssignTask activity;
    private FirebaseFirestore firestore;
    private View view;

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

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView wDeadline;
        TextView wCheckBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            wDeadline = itemView.findViewById(R.id.deadline);
            wCheckBox = itemView.findViewById(R.id.mcheckbox);
        }
    }
}

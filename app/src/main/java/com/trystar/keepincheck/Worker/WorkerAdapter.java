package com.trystar.keepincheck.Worker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trystar.keepincheck.R;

import java.util.ArrayList;

public class WorkerAdapter extends RecyclerView.Adapter<WorkerAdapter.MyViewHolder> {

    Context context;
    ArrayList<Worker> workerArrayList;

    public WorkerAdapter(Context context, ArrayList<Worker> workerArrayList) {
        this.context = context;
        this.workerArrayList = workerArrayList;
    }

    @NonNull
    @Override
    public WorkerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.each_task,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkerAdapter.MyViewHolder holder, int position) {

        Worker workerobj = workerArrayList.get(position);

        holder.task.setText(workerobj.task);
        holder.deadline.setText(workerobj.deadline);
        holder.worker.setText(workerobj.worker);

    }

    @Override
    public int getItemCount() {
        return workerArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView task, deadline, worker;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            task = itemView.findViewById(R.id.mcheckbox);
            deadline = itemView.findViewById(R.id.deadline);
            worker = itemView.findViewById(R.id.worker);
        }
    }
}

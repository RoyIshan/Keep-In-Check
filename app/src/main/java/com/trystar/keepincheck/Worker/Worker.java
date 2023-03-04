package com.trystar.keepincheck.Worker;

public class Worker {

    String task, deadline, worker;

    public Worker(){}

    public Worker(String task, String deadline, String worker) {
        this.task = task;
        this.deadline = deadline;
        this.worker = worker;
    }

    public String getTask() {
        return task;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getWorker() {
        return worker;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }
}

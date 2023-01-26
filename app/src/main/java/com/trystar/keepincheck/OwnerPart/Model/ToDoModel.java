package com.trystar.keepincheck.OwnerPart.Model;

public class ToDoModel extends TaskId{

    private String task, deadline, worker;
    private int status;

    public String getTask() {
        return task;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getWorkerAssigned() {
        return worker;
    }

    public int getStatus() {
        return status;
    }
}

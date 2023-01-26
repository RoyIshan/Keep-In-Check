package com.trystar.keepincheck.OwnerPart.Model;

public class ToDoModel extends TaskId{

    private String task, deadline, workername;
    private int status;

    public String getTask() {
        return task;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getWorkerAssigned() {
        return workername;
    }

    public int getStatus() {
        return status;
    }
}

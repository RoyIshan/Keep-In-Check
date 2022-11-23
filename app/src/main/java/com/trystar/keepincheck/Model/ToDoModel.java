package com.trystar.keepincheck.Model;

public class ToDoModel extends TaskId{

    private String task, deadline;
    private int status;

    public String getTask() {
        return task;
    }

    public String getDeadline() {
        return deadline;
    }

    public int getStatus() {
        return status;
    }
}

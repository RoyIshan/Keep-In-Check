package com.trystar.keepincheck.Attendance;

public class MyItem {
    private final String title;
    private String description;

    public MyItem(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}

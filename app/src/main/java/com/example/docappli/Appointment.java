package com.example.docappli;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {
    private String id;
    private String user;
    private String time;
    private String date;
    private boolean urgent;

    public Appointment(){}
    public Appointment(String user, String time, String date) {
        this.user = user;
        this.time = time;
        this.date = date;
        this.urgent = false;
    }

    public String getUser() {return user;}

    public String getDate() {return date;}

    public String getTime() {return time;}


    public boolean isUrgent() {return urgent;}

    public String _getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}


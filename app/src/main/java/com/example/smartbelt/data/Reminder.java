package com.example.smartbelt.data;

public class Reminder {
    private String time;
    private boolean alarmOn;

    public Reminder(String time, boolean alarmOn) {
        this.time = time;
        this.alarmOn = alarmOn;
    }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public boolean isAlarmOn() { return alarmOn; }
    public void setAlarmOn(boolean alarmOn) { this.alarmOn = alarmOn; }

}

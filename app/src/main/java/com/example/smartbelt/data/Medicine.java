package com.example.smartbelt.data;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;



@Entity(tableName = "medicines", indices={@Index(value={"title"}, unique = true)})
public class Medicine {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String type;
    private String dose;
    private int amount;
   private String reminders;
   private boolean alarmEnabled;
    //private int times;
   // private String timeValues;


//    public Medicine(String title, int times, String timeValues) {
//        this.title = title;
//        this.times = times;
//        this.timeValues = timeValues;
//    }
public Medicine(String title, String type, String dose, int amount, String reminders, boolean alarmEnabled) {
    this.title = title;
    this.type = type;
    this.dose = dose;
    this.amount = amount;
    this.reminders = reminders;
    this.alarmEnabled = alarmEnabled;
}
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getDose() { return dose; }
    public void setDose(String dose) { this.dose = dose; }
    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }
    public String getReminders() { return reminders; }
    public void setReminders(String reminders) { this.reminders = reminders; }
    public boolean isAlarmEnabled() { return alarmEnabled; }
    public void setAlarmEnabled(boolean alarmEnabled) { this.alarmEnabled = alarmEnabled; }

//    public int getTimes() { return times; }
//
//    public void setTimes(int times) {
//        this.times = times;
//    }
//    public String getTimeValues() { return timeValues; }
//    public void setTimeValues(String timeValues) {
//        this.timeValues = timeValues;
//    }
}

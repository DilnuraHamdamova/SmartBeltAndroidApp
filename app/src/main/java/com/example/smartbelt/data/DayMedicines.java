package com.example.smartbelt.data;

import java.util.List;

public class DayMedicines {
    private String day; // Monday, Tuesday...
    private String date; // 15 Nov
    private List<Medicine> medicines;

    public DayMedicines(String day, String date, List<Medicine> medicines) {
        this.day = day;
        this.date = date;
        this.medicines = medicines;
    }

    public String getDay() {
        return day;
    }

    public String getDate() {
        return date;
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }
}

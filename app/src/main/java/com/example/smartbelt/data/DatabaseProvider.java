package com.example.smartbelt.data;

import android.content.Context;

public class DatabaseProvider {

    private static MedicineDatabase database;

    public static MedicineDatabase get(Context context) {
        if (database == null) {
            database = MedicineDatabase.getInstance(context);
        }
        return database;
    }
}

package com.example.smartbelt.ui;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.smartbelt.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MedicineActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);

        BottomNavigationView bottomNav = findViewById(R.id.medicineBottomNav);
        bottomNav.setItemIconTintList(null);
        bottomNav.setItemTextColor(ColorStateList.valueOf(Color.BLACK
        ));
        // Default fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.medicineFragmentContainer, new MyMedsFragment())
                .commit();

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selected = null;
            int itemId = item.getItemId(); // ID'ni bir marta o'zgaruvchiga olamiz

            if (itemId == R.id.nav_my_meds) {
                selected = new MyMedsFragment();
            } else if (itemId == R.id.nav_add_med) {
                selected = new AddMedFragment();
            } else if (itemId == R.id.nav_calendar) {
                selected = new CalendarFragment();
            }

            if (selected != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.medicineFragmentContainer, selected)
                        .commit();
            }
            return true;
        });

    }
}

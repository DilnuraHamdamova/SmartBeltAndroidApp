package com.example.smartbelt.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartbelt.R;
import com.example.smartbelt.adapter.MyMedsAdapter;
import com.example.smartbelt.data.DayMedicines;
import com.example.smartbelt.data.Medicine;
import com.example.smartbelt.data.MedicineDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class MyMedsFragment extends Fragment {

    private RecyclerView recyclerMyMeds;
    private MyMedsAdapter adapter;
    private List<DayMedicines> dayMedicinesList;
    private MedicineDatabase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_meds, container, false);
        recyclerMyMeds = view.findViewById(R.id.recyclerMyMeds);
        db = MedicineDatabase.getInstance(requireContext());

        dayMedicinesList = new ArrayList<>(); // boshida bo‘sh list
        adapter = new MyMedsAdapter(requireContext(), dayMedicinesList);
        recyclerMyMeds.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerMyMeds.setAdapter(adapter);
        // Room DB-dan barcha dorilarni olish background thread-da
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Medicine> allMeds = db.medicineDao().getAllMedicines();
            if (allMeds == null) allMeds = new ArrayList<>();

            if (!allMeds.isEmpty()) {
                // Test uchun kun qo‘shish yoki kerakli logic bilan guruhlash
                Medicine testMed = new Medicine("Paracetamol", "Tablet", "500mg", 2, "", false);
                long id = db.medicineDao().insert(testMed);
                testMed.setId((int) id);
                allMeds.add(testMed);
            }
            List<DayMedicines> tempList = new ArrayList<>();
            tempList.add(new DayMedicines("Monday", "15 Nov", allMeds));

            // UI thread-da adapterni yangilash
            requireActivity().runOnUiThread(() -> {
                dayMedicinesList.clear();
                dayMedicinesList.addAll(tempList);
                adapter.notifyDataSetChanged();
            });
        });
        return view;
    }
}







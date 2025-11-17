package com.example.smartbelt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartbelt.R;
import com.example.smartbelt.data.DayMedicines;
import com.example.smartbelt.data.Medicine;

import java.util.List;

public class MyMedsAdapter extends RecyclerView.Adapter<MyMedsAdapter.DayVH> {

    private final Context context;
    private final List<DayMedicines> data; // DayMedicines = kun + sana + List<Medicine>

    public MyMedsAdapter(Context context, List<DayMedicines> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public DayVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_med, parent, false);
        return new DayVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayVH holder, int position) {
        DayMedicines dayMed = data.get(position);
        holder.tvDay.setText(dayMed.getDay());
        holder.tvDate.setText(dayMed.getDate());

        holder.llMedicinesContainer.removeAllViews(); // clear old items

        for (Medicine med : dayMed.getMedicines()) {
            View medView = LayoutInflater.from(context).inflate(R.layout.item_medicine_box, holder.llMedicinesContainer, false);
            ((TextView) medView.findViewById(R.id.tvMedicineName)).setText(med.getTitle());
            ((TextView) medView.findViewById(R.id.tvMedicineType)).setText(med.getType());
            ((TextView) medView.findViewById(R.id.tvMedicineDose)).setText(med.getDose());
            ((TextView) medView.findViewById(R.id.tvMedicineAmount)).setText(String.valueOf(med.getAmount()));
            holder.llMedicinesContainer.addView(medView);
        }
    }

    @Override
    public int getItemCount() { return data.size(); }

    static class DayVH extends RecyclerView.ViewHolder {
        TextView tvDay, tvDate;
        LinearLayout llMedicinesContainer;

        public DayVH(@NonNull View itemView) {
            super(itemView);
            tvDay = itemView.findViewById(R.id.tvDay);
            tvDate = itemView.findViewById(R.id.tvDate);
            llMedicinesContainer = itemView.findViewById(R.id.llMedicinesContainer);
        }
    }
}

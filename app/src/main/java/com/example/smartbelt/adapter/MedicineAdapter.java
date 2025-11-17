package com.example.smartbelt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.smartbelt.data.Medicine;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartbelt.R;


import java.util.List;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.ViewHolder> {
    private List<Medicine> medicines;
    private Context context;


    public MedicineAdapter(Context context, List<Medicine> medicines){
        this.context=context;
        this.medicines=medicines;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_medicine, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Medicine medicine = medicines.get(position);
        holder.title.setText(medicine.getTitle());
        holder.times.setText("Times: " + medicine.getType());
        holder.timeValues.setText("At:" + medicine.getReminders());


    }
    @Override
    public int getItemCount() {
        return medicines.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, times, timeValues;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.medicineTitle);
            times = itemView.findViewById(R.id.medicineTimes);
            timeValues=itemView.findViewById(R.id.medicineTimeValues);
        }
    }
}

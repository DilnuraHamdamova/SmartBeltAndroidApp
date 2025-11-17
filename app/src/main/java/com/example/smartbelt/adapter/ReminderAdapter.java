package com.example.smartbelt.adapter;

import android.app.TimePickerDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartbelt.R;
import com.example.smartbelt.data.Reminder;

import java.util.Calendar;
import java.util.List;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder> {

    private final List<Reminder> reminders;

    public ReminderAdapter(List<Reminder> reminders) {
        this.reminders = reminders;
    }
    @NonNull
    @Override
    public ReminderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reminder, parent, false);
        return new ReminderViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ReminderViewHolder holder, int position) {
        Reminder reminder = reminders.get(position);
        holder.tvTime.setText(reminder.getTime());

        holder.btnEdit.setOnClickListener(v -> {
            // Edit qilish: yana TimePicker ochish

            Calendar calendar = Calendar.getInstance();
            String[] parts = reminder.getTime().split(":| ");
            int hour = Integer.parseInt(parts[0]);
            int minute = Integer.parseInt(parts[1]);
            if(parts[2].equals("PM") && hour != 12) hour += 12;
            if(parts[2].equals("AM") && hour == 12) hour = 0;

            TimePickerDialog timePicker = new TimePickerDialog(v.getContext(), (view, selectedHour, selectedMinute) -> {
                String amPm = selectedHour >= 12 ? "PM" : "AM";
                int hourIn12 = selectedHour % 12;
                if(hourIn12 == 0) hourIn12 = 12;
                String newTime = String.format("%02d:%02d %s", hourIn12, selectedMinute, amPm);
                reminder.setTime(newTime);
                notifyItemChanged(position);

                // Firebase yoki local update mumkin
                // updateReminder(reminder);

            }, hour, minute, false);
            timePicker.show();
        });
    }
    @Override
    public int getItemCount() {
        return reminders.size();
    }
    public void addReminder(Reminder reminder) {
        reminders.add(reminder);
        notifyItemInserted(reminders.size() - 1);
    }
    static class ReminderViewHolder extends RecyclerView.ViewHolder {
        TextView tvTime;
        ImageButton btnEdit;

        public ReminderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tvReminderTime);
            btnEdit = itemView.findViewById(R.id.btnEditReminder);
        }
    }
}


package com.example.smartbelt.ui;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartbelt.MedicineReceiver;
import com.example.smartbelt.R;
import com.example.smartbelt.data.Medicine;
import com.example.smartbelt.data.MedicineDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.Executors;

public class AddMedFragment extends Fragment {
    private EditText edtName, edtDose, edtAmount;
    private Spinner spinnerType;
    private Button btnAddReminder, btnSave;
    private ToggleButton toggleAlarm;
    private RecyclerView rvReminders;
    private RemindersAdapter remindersAdapter;
    private List<String> reminders = new ArrayList<>();
    private MedicineDatabase db;
    private final SimpleDateFormat iso = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault());

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_med, container, false);
        edtName = view.findViewById(R.id.edtMedicineName);
        edtDose = view.findViewById(R.id.edtDose);
        edtAmount = view.findViewById(R.id.edtAmount);
        spinnerType = view.findViewById(R.id.spinnerType);
        btnAddReminder = view.findViewById(R.id.btnAddReminder);
        btnSave = view.findViewById(R.id.btnSaveMedicine);
        toggleAlarm = view.findViewById(R.id.toggleAlarm);
        rvReminders = view.findViewById(R.id.rvReminders);

        db = MedicineDatabase.getInstance(requireContext());

        // Spinner setup
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item,
                Arrays.asList("Drop","Tablet","Capsule","Liquid","Patch","Cream"));
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(typeAdapter);

        // Reminders list
        remindersAdapter = new RemindersAdapter(reminders);
        rvReminders.setLayoutManager(new LinearLayoutManager(getContext()));
        rvReminders.setAdapter(remindersAdapter);

        btnAddReminder.setOnClickListener(v -> showDateTimePicker());

        btnSave.setOnClickListener(v -> saveMedicine());

        return view;
    }
    private void showDateTimePicker() {
        final Calendar cal = Calendar.getInstance();
        DatePickerDialog dp = new DatePickerDialog(requireContext(),
                (view, year, month, dayOfMonth) -> {
                    cal.set(Calendar.YEAR, year);
                    cal.set(Calendar.MONTH, month);
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    TimePickerDialog tp = new TimePickerDialog(requireContext(),
                            (timeView, hourOfDay, minute) -> {
                                cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                cal.set(Calendar.MINUTE, minute);
                                cal.set(Calendar.SECOND, 0);
                                String isoString = iso.format(cal.getTime());
                                reminders.add(isoString);
                                remindersAdapter.notifyDataSetChanged();
                            }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true);
                    tp.show();
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        dp.show();
    }

    private void saveMedicine() {
        String title = edtName.getText().toString().trim();
        String type = spinnerType.getSelectedItem() != null ? spinnerType.getSelectedItem().toString() : "";
        String dose = edtDose.getText().toString().trim();
        String amountStr = edtAmount.getText().toString().trim();
        boolean alarmEnabled = toggleAlarm.isChecked();
        // Validation
        if (title.isEmpty()) {
            edtName.setError("Required");
            edtName.requestFocus();
            return;
        }
        if (type.isEmpty()) {
            Toast.makeText(getContext(), "Choose type", Toast.LENGTH_SHORT).show();
            return;
        }
        int amount = 0;
        if (!amountStr.isEmpty()) {
            try { amount = Integer.parseInt(amountStr); }
            catch (NumberFormatException e) { edtAmount.setError("Only numbers"); return; }
        }
        String remindersCsv = String.join(",", reminders);
        Medicine med = new Medicine(title, type, dose, amount, remindersCsv, alarmEnabled);

        // DB insert off main thread
        Executors.newSingleThreadExecutor().execute(() -> {
            long rowId = db.medicineDao().insert(med);
            med.setId((int)rowId);

            // schedule notifications on background thread (but use AlarmManager on main thread if needed)
            if (alarmEnabled && !reminders.isEmpty()) {
                requireActivity().runOnUiThread(() -> {
                    scheduleNotifications(med);
                });
            }
        });
        Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
        // clear UI
        edtName.setText("");
        edtDose.setText("");
        edtAmount.setText("");
        reminders.clear();
        remindersAdapter.notifyDataSetChanged();
    }
    private void scheduleNotifications(Medicine med) {
        if (med.getReminders() == null || med.getReminders().isEmpty()) return;
        String[] arr = med.getReminders().split(",");
        AlarmManager alarmManager = (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);
        for (int i = 0; i < arr.length; i++) {
            try {
                Date d = iso.parse(arr[i]);
                if (d == null) continue;
                long when = d.getTime();
                if (when < System.currentTimeMillis()) {
                    // if past, schedule for next day/time or skip — here add one day:
                    when += 24L * 60L * 60L * 1000L;
                }
                Intent intent = new Intent(requireContext(), MedicineReceiver.class);
                intent.putExtra("title", med.getTitle());
                intent.putExtra("medicineId", med.getId());
                int req = Objects.hash(med.getId(), i);

                PendingIntent pi = PendingIntent.getBroadcast(requireContext(), req, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT | (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? PendingIntent.FLAG_IMMUTABLE : 0));
                if (alarmManager != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, when, pi);
                    } else {
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, when, pi);
                    }
                }
            } catch (Exception e) { e.printStackTrace(); }
        }
    }
    // Adapter for reminders list (inner class)
    private class RemindersAdapter extends RecyclerView.Adapter<RemindersAdapter.VH> {
        private final List<String> list;
        RemindersAdapter(List<String> list) { this.list = list; }
        @NonNull
        @Override public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reminder, parent, false);
            return new VH(v);
        }
        @Override public void onBindViewHolder(@NonNull VH holder, int position) {
            String isoS = list.get(position);
            try {
                Date d = iso.parse(isoS);
                holder.tv.setText(android.text.format.DateFormat.format("dd MMM yyyy, HH:mm", d));
            } catch (Exception e) { holder.tv.setText(isoS); }
            holder.btnDel.setOnClickListener(v -> { list.remove(position); notifyDataSetChanged(); });
            holder.btnEdit.setOnClickListener(v -> {
                // edit: parse and re-open date/time pickers then replace
                try {
                    Date d = iso.parse(list.get(position));
                    Calendar cal = Calendar.getInstance(); if (d!=null) cal.setTime(d);
                    DatePickerDialog dp = new DatePickerDialog(requireContext(),
                            (view, y, m, day) -> {
                                cal.set(Calendar.YEAR, y); cal.set(Calendar.MONTH, m); cal.set(Calendar.DAY_OF_MONTH, day);
                                TimePickerDialog tp = new TimePickerDialog(requireContext(),
                                        (timeView, hour, minute) -> {
                                            cal.set(Calendar.HOUR_OF_DAY, hour); cal.set(Calendar.MINUTE, minute);
                                            list.set(position, iso.format(cal.getTime()));
                                            notifyDataSetChanged();
                                        }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true);
                                tp.show();
                            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                    dp.show();
                } catch (Exception ex) { ex.printStackTrace(); }
            });
        }
        @Override public int getItemCount() { return list.size(); }
        class VH extends RecyclerView.ViewHolder {
            TextView tv; ImageButton btnEdit, btnDel;
            VH(@NonNull View v) {
                super(v);
                tv = v.findViewById(R.id.tvReminderTime);
                btnEdit = v.findViewById(R.id.btnEditReminder);
                btnDel = v.findViewById(R.id.btnDeleteReminder);
            }
        }
    }
}
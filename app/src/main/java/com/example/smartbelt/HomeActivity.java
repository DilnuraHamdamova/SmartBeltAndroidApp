package com.example.smartbelt;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.graphics.Color;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.smartbelt.ui.MedicineActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class HomeActivity extends AppCompatActivity {

    private TextView angleView, weightView, timeView, tempView, statusView;
    //private BluetoothAdapter bluetoothAdapter;
    // private BluetoothGatt bluetoothGatt;
    private DatabaseReference ref;

    // private static final UUID SERVICE_UUID = UUID.fromString("6E400001-B5A3-F393-E0A9-E50E24DCCA9E");
    //private static final UUID CHARACTERISTIC_UUID = UUID.fromString("6E400003-B5A3-F393-E0A9-E50E24DCCA9E");

    // private static final String ESP32_MAC = "F4:65:01:30:A3:6A"; //esp32 manzili
    // private static final int REQUEST_PERMISSIONS = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initUI();
        setupBottomNavigation();
        setupFirebase();
    }
    private void initUI() {
        angleView = findViewById(R.id.angleView);
        weightView = findViewById(R.id.weightView);
        tempView = findViewById(R.id.tempView);
        timeView = findViewById(R.id.timeView);
        statusView = findViewById(R.id.statusView);
        statusView.setText("Connecting to Firebase...");
        statusView.setTextColor(Color.parseColor("#FACC15"));
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_exercise) {
                startActivity(new Intent(this, ExerciseActivity.class));
                return true;
            } else if (id == R.id.nav_medicine) {
                startActivity(new Intent(this, MedicineActivity.class));
                return true;
          //  } else if (id == R.id.nav_profile) {
            //    startActivity(new Intent(this, ProfileActivity.class));
           //     return true;
          //  } else if (id == R.id.nav_statistics) {
           //     startActivity(new Intent(this, StatisticsActivity.class));
           //     return true;
            } else if (id == R.id.nav_home) {
                return true;
            }
            return false;
        });
    }
    private void setupFirebase() {
       ref = FirebaseDatabase.getInstance().getReference("smartbelt");
        // Firebase realtime listener
      ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String pitch = snapshot.child("pitch").getValue(String.class);
                    String roll = snapshot.child("roll").getValue(String.class);
                    String weight = snapshot.child("weight").getValue(String.class);
                    String temp = snapshot.child("temp").getValue(String.class);
                    String time = snapshot.child("time").getValue(String.class);

                    angleView.setText("Pitch: " + pitch + "°  Roll: " + roll + "°");
                    weightView.setText("Weight: " + weight + " kg");
                    tempView.setText("Temp: " + temp + " °C");
                    timeView.setText(time);

                    statusView.setText("Connected to Firebase");
                    statusView.setTextColor(Color.parseColor("#22C55E"));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity.this, "Firebase xato: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                statusView.setText("Disconnected");
                statusView.setTextColor(Color.RED);
            }
        });
    }
}
//private final BluetoothGattCallback gattCallback = new BluetoothGattCallback() {
//        @SuppressLint("MissingPermission")
//        @Override
//        public void onConnectionStateChange(@NonNull BluetoothGatt gatt, int status, int newState) {
//            if (newState == BluetoothProfile.STATE_CONNECTED) {
//                runOnUiThread(() -> {
//                    statusView.setText("Connected!");
//                    statusView.setTextColor(Color.parseColor("#22C55E"));
//                });
//                gatt.discoverServices();
//            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
//                runOnUiThread(() -> {
//                    statusView.setText("Disconnected");
//                    statusView.setTextColor(Color.RED);
//                });
//            }
//        }
//
//        @SuppressLint("MissingPermission")
//        @Override
//        public void onServicesDiscovered(@NonNull BluetoothGatt gatt, int status) {
//            BluetoothGattCharacteristic ch = gatt
//                    .getService(SERVICE_UUID)
//                    .getCharacteristic(CHARACTERISTIC_UUID);
//            gatt.setCharacteristicNotification(ch, true);
//        }
//
//        @Override
//        public void onCharacteristicChanged(@NonNull BluetoothGatt gatt, @NonNull BluetoothGattCharacteristic characteristic) {
//            String data = new String(characteristic.getValue());
//            runOnUiThread(() -> updateData(data));
//        }
//    };

//    private void updateData(String data) {
//        // Arduino tomonidan: "Pitch=12.34 | Roll=3.22 | Weight=56.7kg"
//        Log.d("Klab", "updateData: " + data);
//        try {
//            String[] parts = data.split("\\|");
//            String pitch = parts[0].split("=")[1].trim();
//            String roll = parts[1].split("=")[1].trim();
//            String weight = parts[2].split("=")[1].replace("kg", "").trim();
//
//            angleView.setText("Pitch: " + pitch + "°  Roll: " + roll + "°");
//            weightView.setText("Weight: " + weight + " kg");
//            tempView.setText("Temp: -- °C");
//            timeView.setText("--:--");
//        } catch (Exception e) {
//            statusView.setText("Xato format: " + data);
//            statusView.setTextColor(Color.RED);
//        }
//    }


//    private void initializeBluetooth() {
//        BluetoothManager manager = getSystemService(BluetoothManager.class);
//        bluetoothAdapter = manager.getAdapter();
//        if (bluetoothAdapter == null) {
//            showToast("Bluetooth support mavjud emas!");
//            finish();
//        }
//    }
//    private void requestPermissionsIfNeeded() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED ||
//                    ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED ||
//                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//                ActivityCompat.requestPermissions(this, new String[]{
//                        Manifest.permission.BLUETOOTH_SCAN,
//                        Manifest.permission.BLUETOOTH_CONNECT,
//                        Manifest.permission.ACCESS_FINE_LOCATION
//                }, REQUEST_PERMISSIONS);
//            } else {
//                startScanning();
//            }
//        } else {
//            startScanning();
//        }
//    }
//
//    private void showToast(String msg) {
//        runOnUiThread(() -> Toast.makeText(this, msg, Toast.LENGTH_SHORT).show());
//    }
//
//    private void startScanning() {
//        showToast("BLE qurilmalarni skanerlash boshlandi...");
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
//            showToast("BLUETOOTH_SCAN ruxsati yo‘q");
//            return;
//        }
//
//        bluetoothAdapter.getBluetoothLeScanner().startScan(new android.bluetooth.le.ScanCallback() {
//            @SuppressLint("MissingPermission")
//            @Override
//            public void onScanResult(int callbackType, android.bluetooth.le.ScanResult result) {
//                BluetoothDevice device = result.getDevice();
//                String deviceAddress = device.getAddress();
//
//                if (ESP32_MAC.equals(deviceAddress)) {
//                    showToast("ESP32 topildi: " + deviceAddress);
//                    bluetoothAdapter.getBluetoothLeScanner().stopScan(this);
//                    connectToDevice(device);
//                }
//            }
//
//            @Override
//            public void onScanFailed(int errorCode) {
//                showToast("Skaner xato: " + errorCode);
//            }
//        });
//    }
//
//    @SuppressLint("MissingPermission")
//    private void connectToDevice(BluetoothDevice device) {
//        showToast("Ulanish harakati: " + device.getAddress());
//        bluetoothGatt = device.connectGatt(this, false, gattCallback);
//    }
//    }

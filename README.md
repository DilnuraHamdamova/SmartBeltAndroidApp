# Smart Belt Android App

Smart Belt is an Android application that monitors posture, provides exercise
videos, and helps users manage their medication schedules.

The app receives data from the Smart Belt system through Firebase Realtime
Database and displays it on the home screen in real time.

## Download the APK

[📥 Download SmartBelt v1.0.0 APK](https://github.com/DilnuraHamdamova/SmartBeltAndroidApp/releases/download/v1.0.0/app-release.apk)

This is a release build. After downloading the APK from GitHub, you may need to
allow installation from unknown sources on your Android device.

SHA-256:

```text
094c80b7ab001cf72ad1eeba64c083e4c7ca518d2b244b47e00d7afcb6471548
```

## Key Features

- Displays the belt’s `pitch` and `roll` angles
- Tracks weight, temperature, and time data
- Shows the Firebase connection status
- Provides seven exercise videos within the app
- Stores medication names, types, dosages, and quantities
- Schedules medication dates and times
- Creates medication reminders and notifications
- Stores medication data locally using Room Database

## QR Code

Scan the Smart Belt project QR code with your phone’s camera:

<p align="center">
  <img src="assets/smartbelt-android-qr.png" alt="Smart Belt project QR code" width="310">
</p>

## How It Works

1. The Smart Belt device collects sensor data.
2. The data is sent to the `smartbelt` node in Firebase Realtime Database.
3. The Android app listens for changes and updates the `pitch`, `roll`, `weight`,
   `temp`, and `time` values on the home screen.
4. Users can watch videos in the exercises section and schedule medication in
   the medications section.

## Technologies

- Java 11 and XML
- Android SDK 24–35
- Firebase Realtime Database and Analytics
- Room Database
- AlarmManager and Android Notifications
- Material Components and RecyclerView
- Gradle

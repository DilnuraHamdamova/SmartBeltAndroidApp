# Smart Belt Android App

Smart Belt — bel holatini kuzatish, foydalanuvchiga mashqlarni ko‘rsatish va
dori qabul qilish vaqtlarini boshqarishga yordam beruvchi Android ilova.

Ilova Smart Belt tizimidan keladigan ko‘rsatkichlarni Firebase Realtime Database
orqali qabul qiladi va asosiy ekranda real vaqt rejimida namoyish etadi.

## APK'ni yuklab olish

[📥 SmartBelt v1.0.0 APK'ni yuklab olish](https://github.com/DilnuraHamdamova/SmartBeltAndroidApp/releases/download/v1.0.0/SmartBelt-v1.0.0.apk)

Bu ishlab chiqish uchun debug versiya. APK'ni GitHub'dan yuklab olgach, Android
qurilmada noma'lum manbadan ilova o‘rnatishga ruxsat berish talab qilinishi mumkin.

SHA-256:

```text
59f711af23e82d448bdcef4981a62695c05514b6b302232a6ca6602d99e4fd0e
```

## Asosiy imkoniyatlar

- belning `pitch` va `roll` burchaklarini ko‘rsatish;
- vazn, harorat va vaqt ma’lumotlarini kuzatish;
- Firebase bilan ulanish holatini ko‘rsatish;
- 7 ta foydali mashq videosini ilova ichida ko‘rish;
- dorilarni nomi, turi, dozasi va miqdori bilan saqlash;
- dori qabul qilish sanasi va vaqtini belgilash;
- dori uchun eslatma va bildirishnomalar yaratish;
- dorilarni lokal Room ma’lumotlar bazasida saqlash.

## QR-kod

Smart Belt loyihasining QR-kodini telefon kamerasi bilan skanerlang:

<p align="center">
  <img src="assets/smartbelt-android-qr.png" alt="Smart Belt loyiha QR-kodi" width="310">
</p>

## Ilova qanday ishlaydi?

1. Smart Belt qurilmasi sensor ma’lumotlarini yig‘adi.
2. Ma’lumotlar Firebase Realtime Database ichidagi `smartbelt` bo‘limiga uzatiladi.
3. Android ilova o‘zgarishlarni tinglab, `pitch`, `roll`, `weight`, `temp` va
   `time` qiymatlarini bosh ekranda yangilaydi.
4. Foydalanuvchi mashqlar bo‘limida videolarni ko‘rishi hamda dorilar bo‘limida
   qabul vaqtlarini rejalashtirishi mumkin.

## Texnologiyalar

- Java 11 va XML
- Android SDK 24–35
- Firebase Realtime Database va Analytics
- Room Database
- AlarmManager va Android Notifications
- Material Components va RecyclerView
- Gradle

## Loyihani ishga tushirish

### Talablar

- Android Studio
- JDK 11 yoki undan yangi versiya
- Android SDK 35
- internetga ulangan Android 7.0 (`API 24`) yoki undan yangi qurilma/emulyator
- sozlangan Firebase loyihasi va `app/google-services.json` fayli

### O‘rnatish

```bash
git clone https://github.com/DilnuraHamdamova/SmartBeltAndroidApp.git
cd SmartBeltAndroidApp
bash gradlew assembleDebug
```

Yaratilgan debug APK quyidagi manzilda bo‘ladi:

```text
app/build/outputs/apk/debug/app-debug.apk
```

Loyihani Android Studio orqali ham ochib, qurilma yoki emulyatorda `Run` tugmasi
bilan ishga tushirish mumkin.

## Kerakli ruxsatlar

Ilova internet, bildirishnoma, Bluetooth/BLE va qurilma joylashuvi ruxsatlaridan
foydalanadi. Android versiyasiga qarab ayrim ruxsatlarni foydalanuvchi ilova
ichida tasdiqlashi kerak bo‘lishi mumkin.

## Bog‘liq loyiha

ESP32 va MPU6050 asosidagi Smart Belt qurilma kodi:
[SmartBelt-Project](https://github.com/DilnuraHamdamova/SmartBelt-Project)

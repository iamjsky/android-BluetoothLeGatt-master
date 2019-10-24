
Android BluetoothLeGatt Sample
==============================

This repo has been migrated to [github.com/android/connectivity][1]. Please check that repo for future updates. Thank you!

[1]: https://github.com/android/connectivity

Notify, Write uuid, Properties 자동 설정
플러그인 설계전 주요 클래스, 메소드 설계중 Android Native App Test 완료, 플러그인화 진행중
기본 샘플 출처 : Google Developer Github (GoogleSample)
BLE4.0 이상 
 E/ViewRootImpl: sendUserActionEvent() returned.
2019-10-24 18:04:38.465 21296-21296/com.example.android.bluetoothlegatt E/BluetoothAdapter: LE Scan has already started
2019-10-24 18:04:41.015 21296-21296/com.example.android.bluetoothlegatt E/APPTEST: device : D4:36:39:DB:3B:A6
2019-10-24 18:04:41.108 21296-21296/com.example.android.bluetoothlegatt E/ViewRootImpl: sendUserActionEvent() returned.
2019-10-24 18:04:41.475 21296-21296/com.example.android.bluetoothlegatt E/playtango: uuid : 00001800-0000-1000-8000-00805f9b34fb
2019-10-24 18:04:41.477 21296-21296/com.example.android.bluetoothlegatt E/APPTEST: writeable : 00002a02-0000-1000-8000-00805f9b34fb // 10
2019-10-24 18:04:41.478 21296-21296/com.example.android.bluetoothlegatt E/playtango: writeable : 00002a02-0000-1000-8000-00805f9b34fb Properties : 10
2019-10-24 18:04:41.479 21296-21296/com.example.android.bluetoothlegatt E/APPTEST: writeable : 00002a03-0000-1000-8000-00805f9b34fb // 10
2019-10-24 18:04:41.479 21296-21296/com.example.android.bluetoothlegatt E/playtango: writeable : 00002a03-0000-1000-8000-00805f9b34fb Properties : 10
2019-10-24 18:04:41.480 21296-21296/com.example.android.bluetoothlegatt E/playtango: uuid : 00001801-0000-1000-8000-00805f9b34fb
2019-10-24 18:04:41.480 21296-21296/com.example.android.bluetoothlegatt E/playtango: uuid : 0000ffe0-0000-1000-8000-00805f9b34fb
2019-10-24 18:04:41.485 21296-21296/com.example.android.bluetoothlegatt E/APPTEST: BluetoothLeService : 00002902-0000-1000-8000-00805f9b34fb
2019-10-24 18:04:41.488 21296-21296/com.example.android.bluetoothlegatt E/APPTEST: BluetoothLeService : 00002901-0000-1000-8000-00805f9b34fb
2019-10-24 18:04:41.488 21296-21296/com.example.android.bluetoothlegatt E/APPTEST: notify : 0000ffe1-0000-1000-8000-00805f9b34fb // 22
2019-10-24 18:04:41.488 21296-21296/com.example.android.bluetoothlegatt E/playtango: notify : 0000ffe1-0000-1000-8000-00805f9b34fb Properties : 22
2019-10-24 18:04:41.489 21296-21296/com.example.android.bluetoothlegatt E/APPTEST: writeable : 0000ffe1-0000-1000-8000-00805f9b34fb // 22
2019-10-24 18:04:41.490 21296-21296/com.example.android.bluetoothlegatt E/playtango: writeable : 0000ffe1-0000-1000-8000-00805f9b34fb Properties : 22
2019-10-24 18:04:44.435 21296-21310/com.example.android.bluetoothlegatt E/APPTEST: CHANGE
2019-10-24 18:04:44.436 21296-21296/com.example.android.bluetoothlegatt E/APPTEST: com.example.bluetooth.le.ACTION_DATA_AVAILABLE
2019-10-24 18:04:44.438 21296-21296/com.example.android.bluetoothlegatt E/APPTEST: Receive Data : s/i/1/e
2019-10-24 18:04:44.438 21296-21296/com.example.android.bluetoothlegatt E/playtango: Receive Data : s/i/1/e
2019-10-24 18:04:44.548 21296-21567/com.example.android.bluetoothlegatt E/APPTEST: CHANGE
2019-10-24 18:04:44.549 21296-21296/com.example.android.bluetoothlegatt E/APPTEST: com.example.bluetooth.le.ACTION_DATA_AVAILABLE
2019-10-24 18:04:44.550 21296-21296/com.example.android.bluetoothlegatt E/APPTEST: Receive Data : s/o/1/e
2019-10-24 18:04:44.550 21296-21296/com.example.android.bluetoothlegatt E/playtango: Receive Data : s/o/1/e
2019-10-24 18:04:44.658 21296-21310/com.example.android.bluetoothlegatt E/APPTEST: CHANGE
2019-10-24 18:04:44.659 21296-21296/com.example.android.bluetoothlegatt E/APPTEST: com.example.bluetooth.le.ACTION_DATA_AVAILABLE
2019-10-24 18:04:44.660 21296-21296/com.example.android.bluetoothlegatt E/APPTEST: Receive Data : s/i/1/e
2019-10-24 18:04:44.660 21296-21296/com.example.android.bluetoothlegatt E/playtango: Receive Data : s/i/1/e
2019-10-24 18:04:44.737 21296-21567/com.example.android.bluetoothlegatt E/APPTEST: CHANGE

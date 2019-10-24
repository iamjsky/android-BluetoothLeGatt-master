package com.example.android;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;


public class Utils {
    public static final String[] permissions = {"android.permission.BLUETOOTH", "android.permission.BLUETOOTH_ADMIN", "android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"};


    public static String getUUID(byte[] a) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < a.length; i++) {
            if (i >= 9 && i < 25)
                sb.append(String.format("%02x", new Object[]{Integer.valueOf(a[i] & 0xFF)}));
        }
        return sb.toString();
    }


    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder();
        for (final byte b : a)
            sb.append(String.format("%02x ", b & 0xff));
        return sb.toString();
    }


    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] =
                    (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }


    public static boolean checkBLEFunction(BluetoothAdapter adapter) {
        if (adapter == null || !adapter.isEnabled()) {
            return false;
        }
        return true;
    }


    public static boolean isGranted(Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            for (int i = 0; i < permissions.length; i++) {
                if (context.checkSelfPermission(permissions[i]) == PackageManager.PERMISSION_DENIED) {
                    return false;
                }
            }
        }
        return true;
    }
}

package com.sm.cmdss;

import android.*;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Created by Rz Rasel on 2017-08-08.
 */

public class UserDeviceInfo {
    private Activity activity;
    private Context context;

    public UserDeviceInfo(Activity argActivity, Context argContext) {
        this.activity = argActivity;
        this.context = argContext;
        //telephonyManager.getDeviceId();
        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        System.out.println("::::::::::::::::::::::::::::::Android_ID:-" + androidId);
        System.out.println("::::::::::::::::::::::::::::::Android_Build_ID:-" + android.os.Build.ID);
        int REQUEST_READ_PHONE_STATE_PERMISSION = 100;
        String teleManagerDeviceID = "";
        String teleManagerDeviceSerial = "";
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        int permissionCheck = ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_PHONE_STATE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE_PERMISSION);
        } else {
            teleManagerDeviceID = telephonyManager.getDeviceId();
            teleManagerDeviceSerial = "" + telephonyManager.getSimSerialNumber();
            System.out.println("::::::::::::::::::::::::::::::Android_Tele_Device_ID:-" + teleManagerDeviceID);
            System.out.println("::::::::::::::::::::::::::::::Android_Tele_Device_Serial-" + teleManagerDeviceSerial);
        }

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) teleManagerDeviceID.hashCode() << 32) | teleManagerDeviceSerial.hashCode());
        String deviceId = deviceUuid.toString();
        System.out.println("::::::::::::::::::::::::::::::Android_UUID_ID:-" + deviceId);
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        deviceId = wifiManager.getConnectionInfo().getMacAddress();
        System.out.println("::::::::::::::::::::::::::::::Android_Wifi_ID:-" + deviceId);
        deviceId = UUID.randomUUID().toString();
        System.out.println("::::::::::::::::::::::::::::::Android_UUID_Random_ID:-" + deviceId);
        String deviceBRAND = android.os.Build.BRAND;
        System.out.println("::::::::::::::::::::::::::::::Android_BRAND_ID:-" + deviceBRAND);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        String deviceModelName = android.os.Build.MODEL;
        Log.v("Model Name", "" + deviceModelName);
        String deviceUSER = android.os.Build.USER;
        Log.v("Name USER", "" + deviceUSER);
        String devicePRODUCT = android.os.Build.PRODUCT;
        Log.v("PRODUCT", "" + devicePRODUCT);
        String deviceHARDWARE = android.os.Build.HARDWARE;
        Log.v("HARDWARE", "" + deviceHARDWARE);
        //String deviceBRAND = android.os.Build.BRAND;
        Log.v("BRAND", "" + deviceBRAND);
        String myVersion = android.os.Build.VERSION.RELEASE;
        Log.v("VERSION.RELEASE", "" + myVersion);
        int sdkVersion = android.os.Build.VERSION.SDK_INT;
        Log.v("VERSION.SDK_INT", "" + sdkVersion);

            /*List<NetworkInterface> interfacesList = null;
            try {
                interfacesList = Collections.list(NetworkInterface.getNetworkInterfaces());
                for (NetworkInterface networkInterface : interfacesList) {
                    // This will give you the interface MAC ADDRESS
                    deviceId = networkInterface.getHardwareAddress().toString();
                    System.out.println("::::::::::::::::::::::::::::::Android_ID:-" + deviceId);
                }
            } catch (SocketException e) {
                e.printStackTrace();
            }*/
        String android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        deviceId = md5(android_id).toUpperCase();
            /*mAdRequest.addTestDevice(deviceId);
            boolean isTestDevice = mAdRequest.isTestDevice(this);*/
        //is Admob Test Device ? C148757B5D2F4FF25294A0A2CA699D76

        Log.v("TAG", "is Admob Test Device ? " + deviceId);
    }

    public String getTestDeviceID() {
        String deviceId = null;
        String androidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        deviceId = md5(androidID).toUpperCase();
        return deviceId;
    }

    public String md5(final String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return "";
    }
        /*
        <uses-permission android:name="android.permission.READ_PHONE_STATE" />
        <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
        <uses-permission android:name="android.permission.INTERNET" />
        */
}
/*
com.sm.cmdss
::::::::::::::::::::::::::::::Android_ID:-3794e4e8ffe7eede
::::::::::::::::::::::::::::::Android_Build_ID:-M4B30Z
::::::::::::::::::::::::::::::Android_Tele_Device_ID:-353490061626335
::::::::::::::::::::::::::::::Android_Tele_Device_Serial-89880020609011661553
::::::::::::::::::::::::::::::Android_UUID_ID:-00000000-39b2-2674-e7e7-07ec32b2ddf2
::::::::::::::::::::::::::::::Android_Wifi_ID:-02:00:00:00:00:00
::::::::::::::::::::::::::::::Android_UUID_Random_ID:-f9f475a7-cede-480d-9ef9-4921e2ee489a
::::::::::::::::::::::::::::::Android_BRAND_ID:-google
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
V/Model Name: Nexus 5
V/Name USER: android-build
V/PRODUCT: hammerhead
V/HARDWARE: hammerhead
V/BRAND: google
V/VERSION.RELEASE: 6.0.1
V/VERSION.SDK_INT: 23
is Admob Test Device ? C148757B5D2F4FF25294A0A2CA699D76
*/
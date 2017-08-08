package com.sm.cmdss;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import java.util.List;

/**
 * Created by Rz Rasel on 2017-08-08.
 */

public class APPPackageInfo {
    private Context context;
    private PackageManager packageManager = null;
    private PackageInfo packageInfo = null;

    public APPPackageInfo(Context argContext) {
        this.context = argContext;
        init();
    }

    private void init() {
        try {
            packageManager = context.getPackageManager();
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getPackageName() {
        String packageName = null;
        if (packageInfo != null) {
            packageName = packageInfo.packageName;
        }
        return packageName;
    }

    public int getVersionCode() {
        int versionCode = 0;
        if (packageInfo != null) {
            //System.out.println("---------------------|" + packageInfo.versionCode);
            versionCode = packageInfo.versionCode;
        }
        return versionCode;
    }

    public String getVersionName() {
        String versionName = null;
        if (packageInfo != null) {
            versionName = packageInfo.versionName;
        }
        return versionName;
    }

    public String getAPPOwnLabelName() {
        String appLabelName = null;
        if (packageInfo != null && packageManager != null) {
            ApplicationInfo applicationInfo = context.getApplicationInfo();
            appLabelName = packageManager.getApplicationLabel(applicationInfo).toString();
        }
        return appLabelName;
    }

    public String getAPPInstalledLabelName(String argPackageName) {
        String appLabelName = null;
        if (packageInfo != null && packageManager != null) {
            //ApplicationInfo applicationInfo = context.getApplicationInfo();
            //appLabelName = packageManager.getApplicationLabel(applicationInfo).toString();
            List<ApplicationInfo> listApplicationInfos = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
            System.out.println("+++++" + listApplicationInfos.toString());
            for (ApplicationInfo applicationInfo : listApplicationInfos) {
                if (applicationInfo.packageName.equalsIgnoreCase(argPackageName)) {
                    //System.out.println("APP_INFO->" + applicationInfo.packageName);
                    appLabelName = packageManager.getApplicationLabel(applicationInfo).toString();
                    //appLabelName = packageManager.getLaunchIntentForPackage(applicationInfo.packageName).toString();
                    try {
                        packageInfo = packageManager.getPackageInfo(applicationInfo.packageName, 0);
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
            /*if (listApplicationInfos.contains(argPackageName)) {
                int position = listApplicationInfos.indexOf(argPackageName);
                if (position > 0) {
                    ApplicationInfo applicationInfo = listApplicationInfos.get(position);
                    appLabelName = packageManager.getApplicationLabel(applicationInfo).toString();
                }
            }*/
        }
        return appLabelName;
    }

    public void getExternalApkInfo(String argApkFileName) {
        String apkName = argApkFileName;
        String fullPath = Environment.getExternalStorageDirectory() + "/" + apkName;
        packageInfo = packageManager.getPackageArchiveInfo(fullPath, 0);
    }

    @Override
    public void finalize() {
        System.out.println("Book instance is getting destroyed");
    }
        /*"PackageName = " + info.packageName + "\nVersionCode = "
                + info.versionCode + "\nVersionName = "
                + info.versionName + "\nPermissions = " + info.permissions*/
}
/*
Download, Installing and Delete .apk file on android device programmatically from a website other than marketplace
https://stackoverflow.com/questions/20065040/download-installing-and-delete-apk-file-on-android-device-programmatically-fro
https://stackoverflow.com/questions/8846861/downloading-apk-from-server-and-installing-it-to-device

<uses-permission android:name="android.permission.GET_TASKS" />
https://stackoverflow.com/questions/7608173/android-how-to-get-the-name-of-apk-file-programmatically
https://stackoverflow.com/questions/16683455/how-to-get-the-package-name-of-an-application-and-then-launch-that-app-using-int


https://stackoverflow.com/questions/16683455/how-to-get-the-package-name-of-an-application-and-then-launch-that-app-using-int
https://stackoverflow.com/questions/12362534/how-to-get-the-application-name-of-the-apk-programmatically-not-installed


APPPackageInfo appPackageInfo = new APPPackageInfo(context);
System.out.println("----------------------|"
    + appPackageInfo.getAPPInstalledLabelName("com.rz.livetv") + " - " + appPackageInfo.getVersionCode());
*/
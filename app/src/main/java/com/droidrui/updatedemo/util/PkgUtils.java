package com.droidrui.updatedemo.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.telephony.TelephonyManager;

import com.droidrui.updatedemo.App;

import java.io.File;

public class PkgUtils {

    public static void installApk(File file) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            App.getContext().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getVersionCode() {
        int versionCode = 0;
        try {
            PackageInfo info = App.getContext().getPackageManager().getPackageInfo(
                    App.getContext().getPackageName(), Context.MODE_PRIVATE);
            versionCode = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    public static String getVersionName() {
        String versionName = "";
        try {
            PackageInfo info = App.getContext().getPackageManager().getPackageInfo(
                    App.getContext().getPackageName(), Context.MODE_PRIVATE);
            versionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public static String getDeviceId() {
        TelephonyManager manager = (TelephonyManager) App
                .getContext().getSystemService(Context.TELEPHONY_SERVICE);
        return manager.getDeviceId();
    }

}

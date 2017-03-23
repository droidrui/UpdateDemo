package com.droidrui.updatedemo.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.droidrui.updatedemo.App;

public class NetUtils {

    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivity = (ConnectivityManager) App.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isWiFi() {
        boolean wifiConnected = false;
        ConnectivityManager connMgr = (ConnectivityManager) App.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected()) {
            wifiConnected = (activeInfo.getType() == ConnectivityManager.TYPE_WIFI);
        }
        return wifiConnected;
    }

}

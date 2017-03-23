package com.droidrui.updatedemo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.droidrui.updatedemo.component.UpdateManager;
import com.droidrui.updatedemo.constant.ACTION;
import com.droidrui.updatedemo.constant.KEY;
import com.droidrui.updatedemo.util.SharedPrefUtils;

/**
 * Created by lr on 2016/7/8.
 */
public class NotificationClickReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(ACTION.UPDATE_NOTIFICATION_CLICK)) {
            String url = (String) SharedPrefUtils.getFromPublicFile(KEY.LATEST_APK_URL, "");
            int versionCode = (int) SharedPrefUtils.getFromPublicFile(KEY.LATEST_VERSION_CODE, 0);
            if (!TextUtils.isEmpty(url) && versionCode != 0) {
                UpdateManager.getInstance().update(url, versionCode, true);
            }
        }
    }
}

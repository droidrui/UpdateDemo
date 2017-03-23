package com.droidrui.updatedemo.task;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.droidrui.updatedemo.R;
import com.droidrui.updatedemo.component.HttpResponse;
import com.droidrui.updatedemo.component.OkHttpHelper;
import com.droidrui.updatedemo.component.Task;
import com.droidrui.updatedemo.constant.API;
import com.droidrui.updatedemo.constant.KEY;
import com.droidrui.updatedemo.model.UpdateInfo;
import com.droidrui.updatedemo.util.FileUtils;
import com.droidrui.updatedemo.util.PkgUtils;
import com.droidrui.updatedemo.util.ResUtils;
import com.droidrui.updatedemo.util.SharedPrefUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by lr on 2016/3/21.
 */
public class UpdateTask {

    public static Task<UpdateInfo> getUpdateInfo() {
        return new Task<UpdateInfo>() {
            @Override
            protected void call() {
                try {
                    HttpResponse response = OkHttpHelper.getInstance().get(API.VERSION);
                    if (response.error != null) {
                        onError(response.error);
                        return;
                    }
                    UpdateInfo info = JSON.parseObject(response.result, UpdateInfo.class);
                    if (info != null) {
                        if (!TextUtils.isEmpty(info.getDownloadurl())) {
                            SharedPrefUtils.putToPublicFile(KEY.LATEST_VERSION_CODE, info.getVersioncode());
                            SharedPrefUtils.putToPublicFile(KEY.LATEST_APK_URL, info.getDownloadurl());
                        }
                        onSuccess(info);

                        String apkName = String.format(ResUtils.getString(R.string.wechat_version_d_apk), PkgUtils.getVersionCode());
                        File file = new File(FileUtils.getDownloadDir(), apkName);
                        if (file.exists()) {
                            file.delete();
                        }
                    } else {
                        onError(ResUtils.getString(R.string.data_error));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    onError(e);
                }
            }
        };
    }

}

package com.droidrui.updatedemo.util;

import android.os.Environment;

public class FileUtils {

    public static String getDownloadDir() {
        return Environment.getDownloadCacheDirectory().getAbsolutePath();
    }

}

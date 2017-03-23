package com.droidrui.updatedemo.util;

import com.droidrui.updatedemo.App;

/**
 * Created by lingrui on 2017/3/19.
 */

public class ResUtils {

    public static String getString(int resId) {
        return App.getContext().getString(resId);
    }

}

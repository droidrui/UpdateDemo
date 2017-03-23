package com.droidrui.updatedemo.component;

import android.app.Activity;

/**
 * Created by lr on 2016/5/30.
 */
public abstract class HttpTaskCallback<T> extends TaskCallback<T> {

    private Activity mActivity;

    public HttpTaskCallback(Activity activity) {
        mActivity = activity;
    }

    /**
     * 统一预处理错误
     * 比如token过期时弹出Dialog
     */
    @Override
    public void onError(TaskError e) {

    }

}

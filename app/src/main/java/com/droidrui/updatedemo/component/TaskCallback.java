package com.droidrui.updatedemo.component;

/**
 * Created by lr on 2016/5/30.
 */
public abstract class TaskCallback<T> {

    public abstract void onError(TaskError e);

    public abstract void onSuccess(T result);

    public void onFinish() {

    }

    public void onMessage(int arg1, int arg2, Object obj) {

    }
}

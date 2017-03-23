package com.droidrui.updatedemo.component;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;

/**
 * Created by lr on 2016/5/30.
 */
public abstract class Task<T> implements Runnable {

    /**
     * 主线程设置和取消回调
     */
    private TaskCallback<T> mCallback;

    private static final int WHAT_MESSAGE = 1;

    /**
     * 主线程的Handler
     */
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (mCallback != null) {
                switch (msg.what) {
                    case WHAT_MESSAGE:
                        mCallback.onMessage(msg.arg1, msg.arg2, msg.obj);
                        break;
                }
            }
        }
    };

    protected abstract void call();

    @Override
    public void run() {
        try {
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
            call();
        } catch (Throwable e) {
            e.printStackTrace();
            onError(e);
        }
    }

    protected void onError(String msg) {
        onError(new TaskError(msg));
    }

    protected void onError(Throwable e) {
        onError(new TaskError(e));
    }

    protected void onError(final TaskError e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mCallback != null) {
                    mCallback.onFinish();
                    mCallback.onError(e);
                }
            }
        });
    }

    protected void onSuccess(final T result) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mCallback != null) {
                    mCallback.onFinish();
                    mCallback.onSuccess(result);
                }
            }
        });
    }

    protected void onMessage(int arg1, int arg2, Object obj) {
        Message.obtain(mHandler, WHAT_MESSAGE, arg1, arg2, obj).sendToTarget();
    }

    public void start() {
        ThreadPool.getInstance().execute(this);
    }

    /**
     * 主线程调用
     */
    public void cancelCallback() {
        mCallback = null;
    }

    public Task<T> setCallback(TaskCallback<T> callback) {
        mCallback = callback;
        return this;
    }

}

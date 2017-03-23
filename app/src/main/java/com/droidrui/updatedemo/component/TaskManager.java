package com.droidrui.updatedemo.component;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class TaskManager {

    private ArrayList<WeakReference<Task>> mRefList;

    public TaskManager() {
        mRefList = new ArrayList<>();
    }

    public void start(Task task) {
        if (mRefList == null) {
            mRefList = new ArrayList<>();
        }
        if (mRefList.size() > 1) {
            ArrayList<WeakReference<Task>> nilList = new ArrayList<>();
            for (WeakReference<Task> ref : mRefList) {
                Task c = ref.get();
                if (c == null) {
                    nilList.add(ref);
                }
            }
            mRefList.removeAll(nilList);
        }
        mRefList.add(new WeakReference<>(task));
        task.start();
    }

    public void onDestroy() {
        if (mRefList == null) {
            return;
        }
        if (mRefList.size() == 0) {
            mRefList = null;
            return;
        }
        for (WeakReference<Task> ref : mRefList) {
            Task task = ref.get();
            if (task != null) {
                task.cancelCallback();
            }
        }
        mRefList.clear();
        mRefList = null;
    }

}

package com.droidrui.updatedemo.component;

/**
 * Created by lr on 2016/5/30.
 */
public class TaskError {

    public int code;
    public String msg;
    public Throwable throwable;
    public String strCode;

    public TaskError() {

    }

    public TaskError(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public TaskError(String msg) {
        this(0, msg);
    }

    public TaskError(String strCode, String msg) {
        this.strCode = strCode;
        this.msg = msg;
    }

    public TaskError(Throwable throwable, boolean showMsg) {
        this.throwable = throwable;
        if (showMsg && throwable != null) {
            this.msg = throwable.getMessage();
        }
    }

    public TaskError(Throwable throwable) {
        this(throwable, true);
    }
}

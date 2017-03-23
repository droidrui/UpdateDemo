package com.droidrui.updatedemo.view;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.droidrui.updatedemo.App;
import com.droidrui.updatedemo.R;


public class Toaster extends Toast {

    private View mView;
    private TextView mTextView;

    private Handler mHandler;

    private static Toaster sToast = new Toaster(App.getContext());

    public Toaster(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        mView = LayoutInflater.from(App.getContext()).inflate(
                R.layout.toaster, null);
        mTextView = (TextView) mView.findViewById(R.id.tv);

        mHandler = new Handler(Looper.getMainLooper());
    }

    public static void show(int resId) {
        show(App.getContext().getString(resId));
    }

    public static void show(final CharSequence text) {
        if (sToast == null) {
            sToast = new Toaster(App.getContext());
        }
        if (Looper.myLooper() == Looper.getMainLooper()) {
            internalShow(text);
        } else {
            sToast.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    internalShow(text);
                }
            });
        }
    }

    private static void internalShow(CharSequence text) {
        sToast.mTextView.setText(text);
        sToast.setGravity(Gravity.CENTER, 0, 0);
        sToast.show();
    }

    @Override
    public void show() {
        setView(mView);
        super.show();
    }

}

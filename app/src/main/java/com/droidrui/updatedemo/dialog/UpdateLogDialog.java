package com.droidrui.updatedemo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.droidrui.updatedemo.R;
import com.droidrui.updatedemo.component.UpdateManager;
import com.droidrui.updatedemo.constant.KEY;
import com.droidrui.updatedemo.model.UpdateInfo;
import com.droidrui.updatedemo.util.NetUtils;
import com.droidrui.updatedemo.util.PkgUtils;
import com.droidrui.updatedemo.util.SharedPrefUtils;
import com.droidrui.updatedemo.view.Toaster;


/**
 * Created by lr on 2016/7/7.
 */
public class UpdateLogDialog extends Dialog {

    private static final int IGNORE_TIMES = 5;
    private static final long SHOW_TIME_INTERVAL = 1 * 60 * 60 * 1000; //1小时
    //private static final long SHOW_TIME_INTERVAL = 2 * 60 * 1000; //2分钟

    private TextView mVersionTv;
    private TextView mContentTv;
    private Button mCancelBtn;

    private UpdateInfo mUpdateInfo;

    private boolean mIsForceUpdate = false; //默认不是强制更新
    private boolean mHasIgnoreTimes = true; //默认有忽略次数，可忽略5次

    private UpdateAlertDialog mAlertDialog;

    public UpdateLogDialog(Context context) {
        super(context, R.style.CommonDialog);
        setContentView(R.layout.dialog_update_log);

        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);

        initLayout();
    }

    private void initLayout() {
        mVersionTv = (TextView) findViewById(R.id.tv_version);
        mContentTv = (TextView) findViewById(R.id.tv_content);
        mCancelBtn = (Button) findViewById(R.id.btn_cancel);

        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

        findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mIsForceUpdate) {
                    dismiss();
                }
                if (mUpdateInfo == null) {
                    return;
                }
                if (!mIsForceUpdate && !NetUtils.isWiFi()) {
                    if (mAlertDialog == null) {
                        mAlertDialog = new UpdateAlertDialog(getContext());
                    }
                    mAlertDialog.show(mUpdateInfo, mHasIgnoreTimes);
                } else {
                    UpdateManager.getInstance().update(mUpdateInfo.getDownloadurl(), mUpdateInfo.getVersioncode(), true);
                }
            }
        });

        setCanceledOnTouchOutside(false);

        setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (mHasIgnoreTimes) {
                    ignoreThisVersion();
                }
                if (NetUtils.isWiFi()) {
                    UpdateManager.getInstance().update(mUpdateInfo.getDownloadurl(), mUpdateInfo.getVersioncode(), false);
                }
            }
        });
    }

    private void ignoreThisVersion() {
        SharedPrefUtils.putToPublicFile(KEY.IGNORE_VERSION_CODE, mUpdateInfo.getVersioncode());
        int times = (int) SharedPrefUtils.getFromPublicFile(KEY.IGNORE_VERSION_TIMES, 0);
        times++;
        SharedPrefUtils.putToPublicFile(KEY.IGNORE_VERSION_TIMES, times);
        SharedPrefUtils.putToPublicFile(KEY.IGNORE_VERSION_TIMESTAMP, System.currentTimeMillis());
    }

    public void show(UpdateInfo info) {
        show(info, true);
    }

    public void show(UpdateInfo info, boolean hasIgnoreTimes) {
        mUpdateInfo = info;
        mHasIgnoreTimes = hasIgnoreTimes;
        int currentVersionCode = PkgUtils.getVersionCode();
        int latestVersionCode = info.getVersioncode();
        if (currentVersionCode < latestVersionCode) {   //当前app的版本不是最新版本
            int forceVersionCode = info.getForceversioncode();
            if (currentVersionCode < forceVersionCode) { //强制更新，每次都弹dialog，不能取消
                mIsForceUpdate = true;
                mCancelBtn.setEnabled(false); //不能按取消按钮
                setCancelable(false); //不能按返回键取消
                setDataAndShow();
            } else { //非强制更新
                if (hasIgnoreTimes) { //有忽略次数
                    int ignoreVersionCode = (int) SharedPrefUtils.getFromPublicFile(KEY.IGNORE_VERSION_CODE, 0);
                    if (ignoreVersionCode != latestVersionCode) {  //没有忽略最新版本就弹出dialog
                        SharedPrefUtils.putToPublicFile(KEY.IGNORE_VERSION_TIMES, 0);
                        setDataAndShow();
                    } else {
                        int times = (int) SharedPrefUtils.getFromPublicFile(KEY.IGNORE_VERSION_TIMES, 0);
                        if (times < IGNORE_TIMES) {
                            long now = System.currentTimeMillis();
                            long timestamp = (long) SharedPrefUtils.getFromPublicFile(KEY.IGNORE_VERSION_TIMESTAMP, now);
                            if (now - timestamp >= SHOW_TIME_INTERVAL) {
                                setDataAndShow();
                            }
                        }
                    }
                } else {
                    setDataAndShow();
                }
            }
        } else {
            if (!mHasIgnoreTimes) {
                Toaster.show(R.string.already_latest_version);
            }
        }
    }

    private void setDataAndShow() {
        String version = mUpdateInfo.getVersionname();
        if (version.contains("最新版本号")) {
            version = version.replace("最新版本号", "V");
        }
        if (!(version.contains("V") || version.contains("v"))) {
            version = "V" + version;
        }
        mVersionTv.setText(version);
        mContentTv.setText(mUpdateInfo.getUpdatecontent());
        show();
    }

    public boolean isShowingAlertDialog() {
        if (mAlertDialog == null) {
            return false;
        }
        return mAlertDialog.isShowing();
    }

}

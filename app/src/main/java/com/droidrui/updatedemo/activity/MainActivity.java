package com.droidrui.updatedemo.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.droidrui.updatedemo.R;
import com.droidrui.updatedemo.component.TaskCallback;
import com.droidrui.updatedemo.component.TaskError;
import com.droidrui.updatedemo.dialog.UpdateLogDialog;
import com.droidrui.updatedemo.model.UpdateInfo;
import com.droidrui.updatedemo.task.UpdateTask;
import com.droidrui.updatedemo.util.ResUtils;
import com.droidrui.updatedemo.view.Toaster;

public class MainActivity extends BaseActivity {

    private Button mButton;
    private UpdateLogDialog mUpdateDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        getUpdateInfoBackground();
    }

    private void initView() {
        mButton = (Button) findViewById(R.id.btn_check);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUpdateInfoForground();
            }
        });
    }

    private void getUpdateInfoBackground() {
        mTaskManager.start(UpdateTask.getUpdateInfo()
                .setCallback(new TaskCallback<UpdateInfo>() {

                    @Override
                    public void onError(TaskError e) {
                        //Toaster.show(e.msg);
                    }

                    @Override
                    public void onSuccess(UpdateInfo result) {
                        if (result.getShowtips() == UpdateInfo.NO_SHOW_DIALOG) {
                            return;
                        }
                        if (mUpdateDialog == null) {
                            mUpdateDialog = new UpdateLogDialog(mActivity);
                        }
                        if (mUpdateDialog.isShowing() || mUpdateDialog.isShowingAlertDialog()) {
                            return;
                        }
                        mUpdateDialog.show(result);
                    }
                }));
    }

    private void getUpdateInfoForground() {
        mButton.setEnabled(false);
        final ProgressDialog dialog = ProgressDialog.show(mActivity, null, ResUtils.getString(R.string.loading), true);
        dialog.show();
        mTaskManager.start(UpdateTask.getUpdateInfo()
                .setCallback(new TaskCallback<UpdateInfo>() {

                    @Override
                    public void onFinish() {
                        mButton.setEnabled(true);
                        dialog.dismiss();
                    }

                    @Override
                    public void onError(TaskError e) {
                        Toaster.show(e.msg);
                    }

                    @Override
                    public void onSuccess(UpdateInfo result) {
                        UpdateLogDialog updateDialog = new UpdateLogDialog(mActivity);
                        updateDialog.show(result, false);
                    }
                }));
    }

}

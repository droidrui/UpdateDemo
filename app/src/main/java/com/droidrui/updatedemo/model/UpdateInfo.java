package com.droidrui.updatedemo.model;

import java.io.Serializable;

/**
 * Created by lr on 2016/3/21.
 */
public class UpdateInfo implements Serializable {

    public static final int SHOW_DIALOG = 1;
    public static final int NO_SHOW_DIALOG = 0;

    /**
     * downloadurl : http://dldir1.qq.com/weixin/android/weixin656android1020.apk
     * title : 发现新版本
     * createtime : 1485166220
     * versioncode : 152
     * forceversioncode : 151
     * versionname : 1.5.2
     * showtips : 1
     * updatecontent : 1.超过八亿人使用的手机应用
     * 2.支持发送语音短信、视频、图片和文字
     * 3.可以群聊，仅耗少量流量，适合大部分智能手机
     */

    private String downloadurl;
    private String title;
    private long createtime;
    private int versioncode;
    private int forceversioncode;
    private String versionname;
    private int showtips;
    private String updatecontent;

    public String getDownloadurl() {
        return downloadurl;
    }

    public void setDownloadurl(String downloadurl) {
        this.downloadurl = downloadurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public int getVersioncode() {
        return versioncode;
    }

    public void setVersioncode(int versioncode) {
        this.versioncode = versioncode;
    }

    public int getForceversioncode() {
        return forceversioncode;
    }

    public void setForceversioncode(int forceversioncode) {
        this.forceversioncode = forceversioncode;
    }

    public String getVersionname() {
        return versionname;
    }

    public void setVersionname(String versionname) {
        this.versionname = versionname;
    }

    public int getShowtips() {
        return showtips;
    }

    public void setShowtips(int showtips) {
        this.showtips = showtips;
    }

    public String getUpdatecontent() {
        return updatecontent;
    }

    public void setUpdatecontent(String updatecontent) {
        this.updatecontent = updatecontent;
    }
}

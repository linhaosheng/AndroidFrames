package com.linhao.androidmodule.bean;

/**
 * Created by ye on 2017/6/17.
 */

public class UpdateInfo {

    /**
     * versioncode : 2
     * updatedesc : 优化视频播放相关功能
     * apkdown : http://ftp.car-boy.com.cn:888/mirroProject/mirrorMobile1/mirrorMobile.apk
     */
    boolean isrun;  //判断软件是否可用。true为可用，false为不可用
    private int versioncode;
    private String updatedesc;
    private String apkdown;


    public boolean isrun() {
        return isrun;
    }

    public void setRun(boolean isrun) {
        isrun = isrun;
    }

    public int getVersioncode() {
        return versioncode;
    }

    public void setVersioncode(int versioncode) {
        this.versioncode = versioncode;
    }

    public String getUpdatedesc() {
        return updatedesc;
    }

    public void setUpdatedesc(String updatedesc) {
        this.updatedesc = updatedesc;
    }

    public String getApkdown() {
        return apkdown;
    }

    public void setApkdown(String apkdown) {
        this.apkdown = apkdown;
    }
}

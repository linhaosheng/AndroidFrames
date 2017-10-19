package com.linhao.androidmodule.http;

/**
 * Created by reeman on 2017/10/18.
 */

public interface OnDownloadListener {

    /**
     * 下载成功
     */
    void onDownloadSuccess();

    /**
     * @param progress
     * 下载进度
     */
    void onDownloading(int progress);

    /**
     * 下载失败
     */
    void onDownloadFailed(String error);

}

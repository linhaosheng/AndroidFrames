package com.linhao.androidmodule.update;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.linhao.androidmodule.FrameApp;
import com.linhao.androidmodule.R;
import com.linhao.androidmodule.bean.UpdateInfo;
import com.linhao.androidmodule.http.OnDownloadListener;
import com.linhao.androidmodule.utils.AppUtils;
import com.linhao.androidmodule.utils.FileUtils;
import com.linhao.androidmodule.utils.MyToastView;
import com.linhao.androidmodule.utils.NetworkUtils;
import com.linhao.androidmodule.view.UpdateDialog;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by reeman on 2017/10/20.
 */

public class UpdateManager {
    Context context;
    private UpdateDialog dialog;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    private String apkDownUrl = "";
    private boolean showToast;
    private UpdateInfo mUpdateInfo;
    private int drawable;
    private String savePath;
    private String checkUrl;
    private String downUrl;

    public UpdateManager(Context context, String path) {
        this.context = context;
        this.savePath = path;
        FileUtils.deleteDirOrFile(new File(path));
        mBuilder = new NotificationCompat.Builder(context);
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    /**
     * 检测软件更新
     */
    public void checkUpdate(boolean showToast, String checkUrl, int drawable) {
        this.drawable = drawable;
        this.checkUrl = checkUrl;
        this.showToast = showToast;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(checkUrl).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Log.e("下载失败=======", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String updateInfo = response.body().string();
                Log.e("更新信息=======", updateInfo);
                try {
                    Gson gson = new Gson();
                    mUpdateInfo = gson.fromJson(updateInfo, UpdateInfo.class);
                    if (mUpdateInfo != null) {
                        handler.sendEmptyMessage(COMPARE_VERSION);
                    } else {
                        MyToastView.getInstance().Toast(context, "更新失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /***
     * 显示dialog
     *
     * @param url       下载地址
     * @param describle 升级描述
     */

    protected void showDialog(final String url, String describle) {
        dialog = new UpdateDialog(context, "升级新版本", describle);
        dialog.show();
        dialog.setOnDialogClickListener(new UpdateDialog.UpdateDialogClick() {
            public void sure() {
                dialog.dissmiss();
                showProgressNotify(); // 延迟一秒,给布局一秒钟的时间来显示
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        downloadService(url);
                    }
                }, 1500);

            }

            public void noSure() {
                dialog.dissmiss();
            }
        });
    }

    public PendingIntent getDefalutIntent(int flags) {
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, new Intent(), flags);
        return pendingIntent;
    }

    int notifyId = 1028;

    public void showProgressNotify() {
        mBuilder.setContentTitle("").setContentText("")
                .setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL)).setTicker("")// 通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
                .setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
                .setOngoing(false)// ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setSmallIcon(drawable);
        Notification mNotification = mBuilder.build();
        mBuilder.setContentTitle("镜善镜美店长版.apk").setContentText("进度:").setTicker("镜善镜美店长版.apk");// 通知首次出现在通知栏，带上升动画效果的
        mBuilder.setProgress(100, 0, false);
        mNotification.flags = Notification.FLAG_ONGOING_EVENT; // 设置常驻
        mNotificationManager.notify(notifyId, mBuilder.build());
    }

    public static final int NOTIFITION_PROGRESS = 0;
    public static final int DOWN_START = 1;
    public static final int DOWN_SUCCESS = 2;
    public static final int DOWN_FAIL = 3;
    public static final int COMPARE_VERSION = 4;

    private Handler handler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_FAIL:
                    mBuilder.setProgress(100, 0, false);
                    mBuilder.setContentText("下载失败");
                    mNotificationManager.notify(notifyId, mBuilder.build());
                    break;
                case DOWN_SUCCESS:
                    mBuilder.setProgress(100, 100, false);
                    mBuilder.setContentText("点击安装");
                    mNotificationManager.notify(notifyId, mBuilder.build());
                    // 下载完成消除notifition
                    mNotificationManager.cancel(notifyId);
                    File apkfile = new File(savePath);
                    if (!apkfile.exists()) {
                        return;
                    }
                    // 通过Intent安装APK文件
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
                    context.startActivity(i);
                    android.os.Process.killProcess(android.os.Process.myPid());
                    break;
                case DOWN_START:
                    mBuilder.setProgress(100, 0, false);
                    mBuilder.setContentText("开始下载");
                    mNotificationManager.notify(notifyId, mBuilder.build());
                    break;
                case NOTIFITION_PROGRESS:
                    int progress_current = (int) msg.obj;
                    mBuilder.setProgress(100, progress_current, false);
                    mBuilder.setContentText("进度:" + progress_current + "%");
                    mNotificationManager.notify(notifyId, mBuilder.build());
                    break;
                case COMPARE_VERSION:
                    int versionCode = mUpdateInfo.getVersioncode();
                    int currentVsesion = AppUtils.getVersionCode(FrameApp.getContext());
                    Log.i("version========", "ver" + versionCode + "curr" + currentVsesion);
                    if (versionCode > currentVsesion) {
                        //由于编码问题，需要进行解码
//                        String updateContent = DecodeUtil.deUnicode(updateDesc);
                        String updateContent = mUpdateInfo.getUpdatedesc();
                        apkDownUrl = mUpdateInfo.getApkdown();
                        showDialog(apkDownUrl, updateContent);
                    } else if (versionCode == currentVsesion) {
                        if (showToast) {
                            MyToastView.getInstance().Toast(context, "当前已经是最新版本了");
                        }
                    }
                default:
                    break;
            }
        }
    };

    private void downloadService(String url) {
        if (NetworkUtils.getNetWork(context) == 1) {
            MyToastView.getInstance().Toast(context, "当前是手机流量,请注意");
        }
        if (NetworkUtils.isAvailable() == false) {
            MyToastView.getInstance().Toast(context, "当前无网络");
        }
        Log.i("down", "===apk进入下载方法，开始下载");
        File filedir = new File(savePath);
        if (!filedir.exists()) {
            boolean mkdirs = filedir.mkdirs();
            System.out.println("mkdirs" + mkdirs + "-----" + filedir.getPath());
        }
        File zipFile = new File(savePath);
        if (!zipFile.exists()) {
            try {
                zipFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        downServiceApk(url, savePath, new OnDownloadListener() {
            @Override
            public void onDownloadSuccess() {
                Message msg = handler.obtainMessage();
                msg.what = DOWN_SUCCESS;
                handler.sendMessage(msg);
                Log.i("down", "==apk下载完成,");
            }

            @Override
            public void onDownloading(int progress) {
                Message msg = handler.obtainMessage();
                msg.what = NOTIFITION_PROGRESS;
                msg.obj = progress;
                handler.handleMessage(msg);
                Log.i("down", "=apk==" + progress);
            }

            @Override
            public void onDownloadFailed(String message) {
                Message msg = new Message();
                msg.what = DOWN_FAIL;
                handler.sendMessage(msg);
                Log.i("down", "==apk=" + "下载失败====..." + message);
            }
        });
    }

    //服务器端下载apk
    public void downServiceApk(final String url, final String saveApkPath, final OnDownloadListener downListener) {
        final OkHttpClient clent = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        clent.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                downListener.onDownloadFailed(e.getMessage());
                Log.i("onFailure------", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("当前线程:========", Thread.currentThread().getName());
                InputStream input = null;
                RandomAccessFile randomAccessFile = null;
                byte[] buf = new byte[2048];
                int len = 0;
                try {
                    input = response.body().byteStream();
                    File file = new File(saveApkPath);
                    long fileLength = file.length();
                    randomAccessFile = new RandomAccessFile(file, "rw");
                    randomAccessFile.seek(fileLength);
                    long total = getDownLoadLength(url);
                    long sum = 0;
                    while ((len = input.read(buf)) != -1) {
                        randomAccessFile.write(buf, 0, len);
                        sum += len;
                        int progress = (int) ((sum + fileLength) * 1.0f / total * 100);
                        downListener.onDownloading(progress);
                    }
                    downListener.onDownloadSuccess();
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        Thread.sleep(2000);
                        final File file = new File(saveApkPath);
                        final long fileLength = file.length();
                        final Request request = new Request.Builder()
                                .url(url)
                                .addHeader("RANGE", "bytes=" + fileLength + "-" + getDownLoadLength(url))
                                .build();
                        clent.newCall(request).enqueue(this);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    downListener.onDownloadFailed(e.getMessage());
                    Log.i("onResponse------", e.getMessage());
                } finally {
                    try {
                        if (input != null) {
                            input.close();
                        }
                        if (randomAccessFile != null) {
                            randomAccessFile.close();
                        }
                    } catch (Exception e) {
                        downListener.onDownloadFailed(e.getMessage());
                    }
                }
            }
        });
    }

    public long getDownLoadLength(String url) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response != null && response.isSuccessful()) {
                long length = response.body().contentLength();
                response.close();
                System.out.println("length" + length);
                return length;
            }
        } catch (IOException e) {
            System.out.println("error-----" + e.getMessage());
            e.printStackTrace();
            return 0;
        }
        return 0;
    }
}

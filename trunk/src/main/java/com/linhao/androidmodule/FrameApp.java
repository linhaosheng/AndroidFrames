package com.linhao.androidmodule;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Created by reeman on 2017/10/18.
 */

public class FrameApp {

    private static Context context;
    public static int screenHeight;
    public static int screenWidth;
    public static FrameApp instance;

    // #log
    public static String tag = "FrameApp";
    public static boolean isDebug = true;


    public static void init(Context context) {
        FrameApp.context = context;
    }

    public FrameApp getInstance() {
        if (instance == null) {
            synchronized (FrameApp.this) {
                if (instance == null) {
                    instance = new FrameApp();
                }
            }
        }
        return instance;
    }

    public static Context getContext() {
        return context;
    }

    public static Resources getResources() {
        return FrameApp.getContext().getResources();
    }

    public static DisplayMetrics getDisplayMetrics() {
        return FrameApp.getResources().getDisplayMetrics();
    }

    public static Object getSystemService(String name){
        return FrameApp.getContext().getSystemService(name);
    }

}

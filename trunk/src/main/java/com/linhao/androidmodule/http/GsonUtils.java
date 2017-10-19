package com.linhao.androidmodule.http;

import com.google.gson.Gson;

/**
 * Created by reeman on 2017/10/18.
 */

public class GsonUtils {

    private static Gson gson;

    public static Gson getGson() {
        if (gson == null) {
            synchronized (GsonUtils.class) {
                if (gson == null) {
                    gson = new Gson();
                }
            }
        }
        return gson;
    }
}

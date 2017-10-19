package com.linhao.androidmodule.utils;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.linhao.androidmodule.FrameApp;

/**
 * Created by reeman on 2017/10/18.
 * Glide加载本地和网络图片
 */

public class GlideImageUtil {

    private static GlideImageUtil mInstance;

    private GlideImageUtil() {
    }

    public static GlideImageUtil getInstance() {
        if (mInstance == null) {
            synchronized (GlideImageUtil.class) {
                if (mInstance == null) {
                    mInstance = new GlideImageUtil();
                }
            }
        }
        return mInstance;
    }

    //加载drawable图片
    public void displayImage(int resId, ImageView imageView) {
        displayImageWithID(resId, imageView, 0, 0);
    }

    //加载drawable图片
    public void displayImage(int resId, ImageView imageView, int place_image) {
        displayImageWithID(resId, imageView, place_image, 0);
    }

    //加载drawable图片
    public void displayImageWithError(int resId, ImageView imageView, int defaultImage) {
        displayImageWithID(resId, imageView, 0, defaultImage);
    }

    //加载drawable图片
    public void displayImageWithID(int resId, ImageView imageView, int place_image, int defaultImage) {
        if (place_image == 0 && defaultImage == 0) {
            Glide.with(FrameApp.getContext()).load(resId).crossFade().into(imageView);
            return;
        }
        if (place_image == 0) {
            Glide.with(FrameApp.getContext()).load(resId).placeholder(place_image).error(defaultImage).crossFade().into(imageView);
        } else if (defaultImage == 0) {
            Glide.with(FrameApp.getContext()).load(resId).placeholder(place_image).crossFade().into(imageView);
        }
    }

    public void displayImage(String url, ImageView imageView) {
        displayImageWithUrl(url, imageView, 0, 0);
    }

    public void displayImage(String url, ImageView imageView, int defResId) {
        displayImageWithUrl(url, imageView, 0, defResId);
    }

    public void displayImageWithError(String url, ImageView imageView, int place_image) {
        displayImageWithUrl(url, imageView, place_image, 0);
    }

    //加载drawable图片
    public void displayImageWithUrl(String url, ImageView imageView, int place_image, int defaultImage) {
        if (place_image == 0 && defaultImage == 0) {
            Glide.with(FrameApp.getContext()).load(url).crossFade().into(imageView);
            return;
        }
        if (place_image == 0) {
            Glide.with(FrameApp.getContext()).load(url).placeholder(place_image).error(defaultImage).crossFade().into(imageView);
        } else if (defaultImage == 0) {
            Glide.with(FrameApp.getContext()).load(url).placeholder(place_image).crossFade().into(imageView);
        }
    }

    public void displayImage(Uri uri, ImageView imageView) {
        Glide.with(FrameApp.getContext()).load(uri).placeholder(0).error(
                0).crossFade().into(imageView);
    }
    public void displayImage(Uri uri, ImageView imageView,int place_image) {
        Glide.with(FrameApp.getContext()).load(uri).placeholder(place_image).error(
                0).crossFade().into(imageView);
    }

    public void displayImageWithError(Uri uri, ImageView imageView,int defaultImage) {
        Glide.with(FrameApp.getContext()).load(uri).placeholder(0).error(
                defaultImage).crossFade().into(imageView);
    }

    //加载drawable图片
    public void displayImageWithUri(Uri uri, ImageView imageView, int place_image, int defaultImage) {
        if (place_image == 0 && defaultImage == 0) {
            Glide.with(FrameApp.getContext()).load(uri).crossFade().into(imageView);
            return;
        }
        if (place_image == 0) {
            Glide.with(FrameApp.getContext()).load(uri).placeholder(place_image).error(defaultImage).crossFade().into(imageView);
        } else if (defaultImage == 0) {
            Glide.with(FrameApp.getContext()).load(uri).placeholder(place_image).crossFade().into(imageView);
        }
    }

    /**
     * 清除缓存
     *
     * @param context
     */
    public void clearCache(final Context context) {
        clearMemoryCache(context);
        new Thread(new Runnable() {
            @Override
            public void run() {
                clearDiskCache(context);
            }
        }).start();
    }

    /**
     * 清除内存缓存
     *
     * @param context
     */
    public void clearMemoryCache(Context context) {
        Glide.get(context).clearMemory();
    }

    /**
     * 清除磁盘缓存
     *
     * @param context
     */
    public void clearDiskCache(Context context) {
        Glide.get(context).clearDiskCache();
    }
}

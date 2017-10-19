package com.linhao.androidmodule.view;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;

import com.linhao.androidmodule.R;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.callback.ConfigButton;
import com.mylhyl.circledialog.callback.ConfigDialog;
import com.mylhyl.circledialog.callback.ConfigInput;
import com.mylhyl.circledialog.callback.ConfigProgress;
import com.mylhyl.circledialog.params.ButtonParams;
import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.params.InputParams;
import com.mylhyl.circledialog.params.ProgressParams;
import com.mylhyl.circledialog.view.listener.OnInputClickListener;

/**
 * Created by reeman on 2017/10/18.
 */

public class DialogView {

    /**
     * 确定框
     *
     * @param activity
     * @param title
     * @param info
     */
    public void ShowConfigDialog(FragmentActivity activity, String title, String info) {
        new CircleDialog.Builder(activity)
                .setTitle(title)
                .setText(info)
                .setPositive("确定", null)
                .show();
    }

    /**
     * String[] items = {"拍照", "从相册选择", "小视频"};
     * 选择对话框 选择对话框
     */
    public void SelectDialog(FragmentActivity activity, String[] items, String title, final int dialogWindowAnim) {
        new CircleDialog.Builder(activity)
                .configDialog(new ConfigDialog() {
                    @Override
                    public void onConfig(DialogParams params) {
                        //增加弹出动画
                        params.animStyle = dialogWindowAnim;
                    }
                })
                .setTitle(title)
                .setTitleColor(Color.BLUE)
                .setItems(items, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                })
                .setNegative("取消", null)
                .configNegative(new ConfigButton() {
                    @Override
                    public void onConfig(ButtonParams params) {
                        params.textColor = Color.RED;
                    }
                }).show();
    }

    /**
     * 输入框
     *
     * @param activity
     * @param title
     */
    public void DialogInput(final FragmentActivity activity, String title, final OnInputClickListener inputClickListener) {
        new CircleDialog.Builder(activity).configInput(new ConfigInput() {
            @Override
            public void onConfig(InputParams params) {

            }
        }).setTitle(title).setPositiveInput("确定", new OnInputClickListener() {
            @Override
            public void onClick(String text, View v) {
                inputClickListener.onClick(text, v);
            }
        }).setNegative("取消", null).show();
    }

    /**
     *显示下载的进度条
     * @param activity
     * @param title
     * @param max
     * @param progress
     * @param dialogCancelListener
     */
    public void DialogProgress(final FragmentActivity activity, String title, int max, int progress, final DialogCancelListener dialogCancelListener) {
        CircleDialog.Builder builder = null;
        if (builder == null) {
            builder = new CircleDialog.Builder(activity).configProgress(new ConfigProgress() {
                @Override
                public void onConfig(ProgressParams params) {

                }
            }).setTitle(title).setNegative("取消", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogCancelListener.clickCancel();
                }
            });
        }
        builder.setProgress(max, progress);
        builder.show();
    }
}

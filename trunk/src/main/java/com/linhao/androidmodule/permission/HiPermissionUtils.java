package com.linhao.androidmodule.permission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;

import com.linhao.androidmodule.FrameApp;
import com.linhao.androidmodule.R;
import com.linhao.androidmodule.bean.EventType;
import com.linhao.androidmodule.data.Config;

import org.greenrobot.eventbus.EventBus;
import java.util.List;

import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import me.weyye.hipermission.PermissionItem;

import static com.linhao.androidmodule.FrameApp.getResources;

/**
 * Created by reeman on 2017/10/18.
 */

public class HiPermissionUtils {


    /**
     * rmissionItem> permissionItems = new ArrayList<PermissionItem>();
     * permissionItems.add(new PermissionItem(Manifest.permission.CAMERA, "照相机", R.drawable.permission_ic_memory));
     * permissionItems.add(new PermissionItem(Manifest.permission.ACCESS_FINE_LOCATION, "定位", R.drawable.permission_ic_location));
     *
     * @param permissionItems
     */
    public static void checkPermission(List<PermissionItem> permissionItems) {
        HiPermission.create(FrameApp.getContext()).permissions(permissionItems).checkMutiPermission(new PermissionCallback() {
            @Override
            public void onClose() {
                EventBus.getDefault().post(new EventType.PermissionEventType(Config.PERMISSION_CLOSE, "onClose"));
            }

            @Override
            public void onFinish() {
                EventBus.getDefault().post(new EventType.PermissionEventType(Config.PERMISSION_FINISH, "onFinish"));
            }

            @Override
            public void onDeny(String permission, int position) {
                EventBus.getDefault().post(new EventType.PermissionEventType(Config.PERMISSION_DENY, "onDeny"));
            }

            @Override
            public void onGuarantee(String permission, int position) {
                EventBus.getDefault().post(new EventType.PermissionEventType(Config.PERMISSION_GUARANTEE, "onGuarantee"));
            }
        });
    }

    /**
     * HiPermission.create(MainActivity.this)
     * .title("亲爱的上帝")
     * .permissions(permissionItems)
     * .filterColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, getTheme()))//图标的颜色
     * .msg("为了保护世界的和平，开启这些权限吧！\n你我一起拯救世界！")
     * .style(R.style.PermissionBlueStyle)
     * .checkMutiPermission(...);
     *
     * @param permissionItems
     */
    public static void checkPermissionStyle(List<PermissionItem> permissionItems, String title, String msg, int style) {
        HiPermission.create(FrameApp.getContext())
                .title(title)
                .permissions(permissionItems)
                .filterColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, FrameApp.getContext().getTheme()))//图标的颜色
                .msg(msg)
                .style(style)
                .checkMutiPermission(new PermissionCallback() {
                    @Override
                    public void onClose() {
                        EventBus.getDefault().post(new EventType.PermissionEventType(Config.PERMISSION_CLOSE, "onClose"));
                    }

                    @Override
                    public void onFinish() {
                        EventBus.getDefault().post(new EventType.PermissionEventType(Config.PERMISSION_FINISH, "onFinish"));
                    }

                    @Override
                    public void onDeny(String permission, int position) {
                        EventBus.getDefault().post(new EventType.PermissionEventType(Config.PERMISSION_DENY, "onDeny"));
                    }

                    @Override
                    public void onGuarantee(String permission, int position) {
                        EventBus.getDefault().post(new EventType.PermissionEventType(Config.PERMISSION_GUARANTEE, "onGuarantee"));
                    }
                });
    }
}

package com.linhao.androidmodule.bean;

/**
 * Created by reeman on 2017/10/18.
 */

public class EventType {

    public static class PermissionEventType {
        public int permissionType;
        public String permissiomInfo;

        public PermissionEventType(int permissionType, String permissiomInfo) {
            this.permissionType = permissionType;
            this.permissiomInfo = permissiomInfo;
        }
    }

    public static class HttpResultEventType {
        public int httpEventType;
        public String httpResultInfo;

        public HttpResultEventType(int httpEventType, String httpResultInfo) {
            this.httpEventType = httpEventType;
            this.httpResultInfo = httpResultInfo;
        }
    }
}

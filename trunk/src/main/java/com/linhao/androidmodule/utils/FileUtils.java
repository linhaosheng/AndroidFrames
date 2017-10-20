package com.linhao.androidmodule.utils;

import java.io.File;

/**
 * Created by Administrator on 2017/8/15.
 */

public class FileUtils {

    //删除目录或者文件
    public static boolean deleteDirOrFile(String Path) {
        return deleteDirOrFile(new File(Path));
    }

    public static boolean deleteDirOrFile(File dir) {
        if (!dir.exists()) {
            return true;
        }
        if (dir.isDirectory()) {
            String[] children = dir.list();
            if (children == null || children.length == 0) {
                return true;
            }
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDirOrFile(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    public void handle(){

    }
}

package com.datayes.dyoa.utils;

import android.Manifest;

/**
 * Created by nianyi.yang on 2016/9/14.
 */
public class PermissionConstant {

    public static final int RUNTIME_PERMISSION = 1;

    // 拍照
    public static final String[] CAMERA_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA
    };

    // 录音
    public static final String[] AUDIO_PERMISSIONS = new String[]{
            Manifest.permission.RECORD_AUDIO
    };
}

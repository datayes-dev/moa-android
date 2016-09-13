package com.datayes.dyoa.module.code.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.datayes.dyoa.R;
import com.datayes.dyoa.common.base.BaseActivity;
import com.datayes.dyoa.common.networkstatus.NetworkState;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

/**
 * 二维码扫描界面
 * Created by hongfei.tao on 2016/9/13 10:44.
 */
public class ScanCodeActivity extends BaseActivity implements CodeUtils.AnalyzeCallback {

    private CaptureFragment mCaptureFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCaptureFragment = new CaptureFragment();
        CodeUtils.setFragmentArgs(mCaptureFragment, R.layout.layout_camera);
        mCaptureFragment.setAnalyzeCallback(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.fl_code_container, mCaptureFragment).commit();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scan_code;
    }

    @Override
    public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
        Intent intent = new Intent();
        intent.putExtra(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
        intent.putExtra(CodeUtils.RESULT_STRING, result);
        startActivity(intent);

        finish();
    }

    @Override
    public void onAnalyzeFailed() {
        Intent intent = new Intent();
        intent.putExtra(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
        startActivity(intent);

        finish();
    }

    @Override
    public void onNetworkStateChanged(NetworkState networkState) {

    }

    @Override
    public void onErrorResponse(String operationType, Throwable throwable, String tag) {

    }
}

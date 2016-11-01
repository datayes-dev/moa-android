package com.datayes.dinnerbusiness.module.code.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.datayes.baseapp.tools.DYToast;
import com.datayes.dinnerbusiness.R;
import com.datayes.dinnerbusiness.common.base.BaseActivity;
import com.datayes.dinnerbusiness.common.networkstatus.NetworkState;
import com.datayes.dinnerbusiness.common.view.CTitle;
import com.datayes.dinnerbusiness.module.swipecard.activity.SwipeCardActivity;
import com.datayes.dinnerbusiness.module.swipecard.activity.TradeHistoryActivity;
import com.datayes.dinnerbusiness.utils.PermissionConstant;
import com.datayes.dinnerbusiness.utils.PermissionManager;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.List;

import butterknife.BindView;

/**
 * 二维码扫描界面
 * Created by hongfei.tao on 2016/9/13 10:44.
 */
public class ScanCodeActivity extends BaseActivity implements CodeUtils.AnalyzeCallback {

    @BindView(R.id.ct_title)
    CTitle mTitle;


    private CaptureFragment mCaptureFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mTitle.setRightBtnText(getString(R.string.trade_history_title));
        mTitle.setLeftBtnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitle.setrightFenGeClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScanCodeActivity.this, TradeHistoryActivity.class);
                startActivity(intent);
            }
        });


        // 检查运行时权限，如果是Android M以下直接调用
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkCameraPermission();
        } else {
            initCode();
        }


    }

    private void initCode() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCaptureFragment == null) {
            mCaptureFragment = new CaptureFragment();
            CodeUtils.setFragmentArgs(mCaptureFragment, R.layout.layout_camera);
            mCaptureFragment.setAnalyzeCallback(this);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_code_container, mCaptureFragment).commit();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scan_code;
    }

    @Override
    public void onAnalyzeSuccess(Bitmap mBitmap, String result) {

        Intent intent = new Intent(this, SwipeCardActivity.class);
        intent.putExtra(SwipeCardActivity.RESTAURANT_ID_KEY, result);
        startActivity(intent);
        finish();

    }

    @Override
    public void onAnalyzeFailed() {
        Intent intent = new Intent(this, SwipeCardActivity.class);
        intent.putExtra(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
        startActivity(intent);

        finish();
    }

    @Override
    public void onNetworkStateChanged(NetworkState networkState) {

    }


    /**
     * 检查拍照运行时权限 暂未使用
     */
    private void checkCameraPermission() {
        if (PermissionManager.hasPermissions(this, PermissionConstant.CAMERA_PERMISSIONS)) {
            // 已有拍照权限
            initCode();
        } else {
            PermissionManager.requestPermissions(this, permissionListener,
                    getString(R.string.request_camera_permission_tip), PermissionConstant.CAMERA_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.onRequestPermissionsResult(this, permissionListener, requestCode, permissions, grantResults);
    }

    /**
     * Runtime Permission
     */
    private final PermissionManager.PermissionListener permissionListener = new PermissionManager.PermissionListener() {

        @Override
        public void onPermissionsGranted(List<String> perms) {
            if (perms.size() == PermissionConstant.CAMERA_PERMISSIONS.length) {
                // 拍照授权成功
                initCode();
            } else {
                // 授权失败
                DYToast.show(ScanCodeActivity.this, getString(R.string.grant_failed), Toast.LENGTH_SHORT);
            }
        }

        @Override
        public void onPermissionsDenied(List<String> perms) {
            // 授权失败
            DYToast.show(ScanCodeActivity.this, getString(R.string.permission_denied), Toast.LENGTH_SHORT);
        }

        @Override
        public void onPermissionRequestRejected() {
            // 授权失败
            DYToast.show(ScanCodeActivity.this, getString(R.string.permission_denied), Toast.LENGTH_SHORT);
        }

    };
}
package com.datayes.dinnerbusiness.module.code.activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.datayes.baseapp.tools.DYToast;
import com.datayes.dinnerbusiness.R;
import com.datayes.dinnerbusiness.common.base.BaseActivity;
import com.datayes.dinnerbusiness.common.network.BaseService;
import com.datayes.dinnerbusiness.common.networkstatus.NetworkState;
import com.datayes.dinnerbusiness.common.view.CTitle;
import com.datayes.dinnerbusiness.module.login.activity.LoginActivity;
import com.datayes.dinnerbusiness.module.moacapture.MoaCaptureFragment;
import com.datayes.dinnerbusiness.module.swipecard.activity.TradeHistoryActivity;
import com.datayes.dinnerbusiness.module.swipecard.manager.SwipeManager;
import com.datayes.dinnerbusiness.module.swipecard.service.SwipeService;
import com.datayes.dinnerbusiness.module.user.CurrentUser;
import com.datayes.dinnerbusiness.utils.PermissionConstant;
import com.datayes.dinnerbusiness.utils.PermissionManager;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;

/**
 * 二维码扫描界面
 * Created by hongfei.tao on 2016/9/13 10:44.
 */
public class ScanCodeActivity extends BaseActivity implements CodeUtils.AnalyzeCallback {

    @BindView(R.id.ct_title)
    CTitle mTitle;


    private MoaCaptureFragment mCaptureFragment;

    private SwipeManager mSwipeManager;
    private SwipeService mSwipeService;
    private static String lastScanCode = "";
    private Handler mHandler;
    private Runnable mRunnable;
    private boolean isNetWorking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSwipeManager = new SwipeManager();

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

        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                inintCodeManager();
            }
        };

    }

    private void initCode() {

    }

    @Override
    protected void onResume() {
        super.onResume();

        inintCodeManager();

    }

    @Override
    protected void onDestroy() {

        mHandler.removeCallbacks(mRunnable);

        super.onDestroy();

    }

    private void inintCodeManager() {

        mCaptureFragment = new MoaCaptureFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("layout_id", R.layout.layout_camera);
        mCaptureFragment.setArguments(bundle);
        mCaptureFragment.setAnalyzeCallback(ScanCodeActivity.this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_code_container, mCaptureFragment).commit();


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scan_code;
    }

    @Override
    public void onAnalyzeSuccess(Bitmap mBitmap, String result) {

        if (result != null) {
            if (!CurrentUser.getInstance().isLogin()) {

                Intent intent = new Intent(ScanCodeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            } else {

                if (lastScanCode != null && !lastScanCode.equals(result) && !isNetWorking) {
                    isNetWorking = true;
                    mSwipeManager.sendUserTradeMessage(this, this, result);
                } else {
                    jumpNextPage(false, "不能重复扫码");

                }
            }

        } else {

            jumpNextPage(false, "扫描失败");

        }

    }

    @Override
    public void onAnalyzeFailed() {

        jumpNextPage(false, "扫描失败");

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


    @Override
    public void onErrorResponse(String operationType, Throwable throwable, String tag) {

        hideLoading();

        isNetWorking = false;
        jumpNextPage(false, "交易失败");
    }

    @Override
    public void networkFinished(String operationType, BaseService service, int code, String tag) {

        hideLoading();

        isNetWorking = false;
        lastScanCode = operationType;

        if (mSwipeService != null) {

            if (mSwipeService.getTranResultBean() != null) {

                if (("Quota error").equals(mSwipeService.getTranResultBean().getInfo())) {

                    jumpNextPage(false, " 配额用完了");

                } else if (("Qrcode error").equals(mSwipeService.getTranResultBean().getInfo())) {

                    jumpNextPage(false, "二维码不正确");
                } else {
                    JSONObject object = mSwipeService.getTranResultBean().jsonObj;
                    try {
                        String resultStr = object.getString("result");
                        if ("success".equals(resultStr)) {
                            jumpNextPage(true, null);
                        }
                    } catch (Exception ex) {
                        jumpNextPage(false, "交易失败");
                    }
                }
            }
        }
    }

    @Override
    public BaseService initService() {

        mSwipeService = new SwipeService();
        return mSwipeService;
    }

    private void jumpNextPage(boolean success, String tag) {

        if (success) {

            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();

        } else {
            if (tag != null)
                DYToast.makeText(this, tag, Toast.LENGTH_SHORT).show();
        }

        mHandler.postDelayed(mRunnable, 500);

    }

}

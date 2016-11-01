package com.datayes.dinnercustom.swipecard.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.datayes.baseapp.tools.DYToast;
import com.datayes.dinnercustom.R;
import com.datayes.dyoa.common.base.BaseActivity;
import com.datayes.dyoa.common.config.Config;
import com.datayes.dyoa.common.config.RequestInfo;
import com.datayes.dyoa.common.imageloader.DYImageLoader;
import com.datayes.dyoa.common.networkstatus.NetworkState;
import com.datayes.dyoa.common.view.CTitle;
import com.datayes.dyoa.module.login.activity.LoginActivity;
import com.datayes.dyoa.module.user.CurrentUser;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shenen.gao on 2016/11/1.
 */

public class UserQRCodeActivity extends BaseActivity implements ImageLoadingListener {


    @BindView(R.id.ct_title)
    CTitle mCtTitle;
    @BindView(R.id.tv_loading)
    TextView mTvLoading;
    @BindView(R.id.iv_qr_code)
    ImageView mIvQrCode;
    @BindView(R.id.btn_refresh)
    Button mBtnRefresh;

    //TODO 由于imageloader 没有处理tocken的逻辑，所以当失败次数大于3次就要求用户重新登陆
    private final int ERROR_MAX_COUNT = 3;
    private int mCurCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

        init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_qr_code;
    }

    @Override
    protected void onResume() {

        super.onResume();
        refreshQRCode();
    }

    private void init() {

        mCtTitle.setLeftBtnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }

    private void refreshQRCode() {

        DYImageLoader.getInstance().displayFullImageAuthorization(new StringBuilder()
                .append(Config.getConfig().getUrl(Config.ConfigUrlType.ORDER))
                .append(RequestInfo.DINNER_USER_QR_CODE.getOperationType()).toString(), mIvQrCode, false, false, this);
    }

    @OnClick(R.id.btn_refresh)
    public void onClick() {

        refreshQRCode();
    }

    @Override
    public void onLoadingStarted(String s, View view) {
        mIvQrCode.setVisibility(View.GONE);
        mTvLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadingFailed(String s, View view, FailReason failReason) {
        mIvQrCode.setVisibility(View.VISIBLE);
        mTvLoading.setVisibility(View.GONE);
        DYToast.makeText(UserQRCodeActivity.this, "获取二维码失败", Toast.LENGTH_SHORT).show();
        onLoadError();
    }

    @Override
    public void onLoadingComplete(String s, View view, Bitmap bitmap) {
        mIvQrCode.setVisibility(View.VISIBLE);
        mTvLoading.setVisibility(View.GONE);
    }

    @Override
    public void onLoadingCancelled(String s, View view) {
        mIvQrCode.setVisibility(View.VISIBLE);
        mTvLoading.setVisibility(View.GONE);
        DYToast.makeText(UserQRCodeActivity.this, "获取二维码失败", Toast.LENGTH_SHORT).show();
        onLoadError();
    }

    private void onLoadError() {

        ++ mCurCount;

        if (mCurCount > ERROR_MAX_COUNT) {

            DYToast.makeText(UserQRCodeActivity.this, "重新登陆", Toast.LENGTH_SHORT).show();

            CurrentUser.sharedInstance().clearUserInfo();
            startActivity(new Intent(this, LoginActivity.class));

            finish();
        }
    }

    @Override
    public void onNetworkStateChanged(NetworkState networkState) {

    }
}

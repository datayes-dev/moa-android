package com.datayes.dinnercustom.swipecard.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.datayes.baseapp.tools.DYToast;
import com.datayes.dinnercustom.R;
import com.datayes.dyoa.bean.user.AccountBean;
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

public class UserQRCodeActivity extends BaseActivity implements ImageLoadingListener, CurrentUser.onRefreshAccountInfo {


    @BindView(R.id.ct_title)
    CTitle mCtTitle;
    @BindView(R.id.tv_loading)
    TextView mTvLoading;
    @BindView(R.id.iv_qr_code)
    ImageView mIvQrCode;
    @BindView(R.id.btn_refresh)
    Button mBtnRefresh;

    //TODO 是否处于刷新二维码的状态
    private boolean mIsOnRrefresh = false;

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

        if (CurrentUser.getInstance().isLogin()) {

            if (!mIsOnRrefresh) {

                mIsOnRrefresh = true;

                DYImageLoader.getInstance().displayFullImageAuthorization(new StringBuilder()
                        .append(Config.getConfig().getUrl(Config.ConfigUrlType.ORDER))
                        .append(RequestInfo.DINNER_USER_QR_CODE.getOperationType()).toString(), mIvQrCode, false, false, this);
            }

        } else {

            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
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

        if (failReason != null && failReason.getType() == FailReason.FailType.DECODING_ERROR) {

            //TODO 由于imageloader没有刷新tocken的机制，所以这里刷新一下用户数据来刷新tocken
            CurrentUser.getInstance().refreshAccountInfo(this);

        } else {

            mIvQrCode.setVisibility(View.VISIBLE);
            mTvLoading.setVisibility(View.GONE);

            DYToast.makeText(UserQRCodeActivity.this, "获取二维码失败,请重试!", Toast.LENGTH_SHORT).show();
            mIsOnRrefresh = false;
        }
    }

    @Override
    public void onRefreshed(AccountBean accountInfo) {

        mIvQrCode.setVisibility(View.VISIBLE);
        mTvLoading.setVisibility(View.GONE);

        DYToast.makeText(UserQRCodeActivity.this, "获取二维码失败,请重试!", Toast.LENGTH_SHORT).show();

        mIsOnRrefresh = false;
    }

    @Override
    public void onLoadingComplete(String s, View view, Bitmap bitmap) {
        mIvQrCode.setVisibility(View.VISIBLE);
        mTvLoading.setVisibility(View.GONE);

        mIsOnRrefresh = false;
    }

    @Override
    public void onLoadingCancelled(String s, View view) {
        mIvQrCode.setVisibility(View.VISIBLE);
        mTvLoading.setVisibility(View.GONE);
        DYToast.makeText(UserQRCodeActivity.this, "获取二维码失败,请重试!", Toast.LENGTH_SHORT).show();

        mIsOnRrefresh = false;
    }

    @Override
    public void onNetworkStateChanged(NetworkState networkState) {

    }


    @Override
    public void onError() {

    }
}

package com.datayes.dyoa.module.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.datayes.baseapp.tools.DYToast;
import com.datayes.dyoa.R;
import com.datayes.dyoa.bean.user.AccountBean;
import com.datayes.dyoa.common.base.BaseActivity;
import com.datayes.dyoa.common.config.Config;
import com.datayes.dyoa.common.imageloader.DYImageLoader;
import com.datayes.dyoa.common.networkstatus.NetworkState;
import com.datayes.dyoa.common.view.CTitle;
import com.datayes.dyoa.common.view.CircleImageView;
import com.datayes.dyoa.common.view.MineItemView;
import com.datayes.dyoa.module.code.activity.ScanCodeActivity;
import com.datayes.dyoa.module.login.activity.LoginActivity;
import com.datayes.dyoa.module.user.CurrentUser;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hongfei.tao on 2016/9/14 11:00.
 */
public class MineActivity extends BaseActivity {

    @BindView(R.id.miv_scan_code)
    MineItemView mScanCode;
    @BindView(R.id.miv_logout)
    MineItemView mLogout;
    @BindView(R.id.ct_title)
    CTitle mCtTitle;
    @BindView(R.id.tv_username)
    TextView mUsername;
    @BindView(R.id.iv_user_icon)
    CircleImageView mUserIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCtTitle.getLeftBtn().setVisibility(View.INVISIBLE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (CurrentUser.getInstance().isLogin()) {
            AccountBean accountInfo = CurrentUser.getInstance().getAccountInfo();
            if (accountInfo != null) {
                mUsername.setText(accountInfo.getUserName());

                String imageId = String.valueOf(accountInfo.getUser().getImageId());

                if (!TextUtils.isEmpty(imageId)) {

                    DYImageLoader.getInstance().displayAvatar(Config.getConfig().getUserloginUrl() + imageId, mUserIcon);
                }
            } else {
                CurrentUser.getInstance().refreshAccountInfo(new CurrentUser.onRefreshAccountInfo() {
                    @Override
                    public void onRefreshed(AccountBean accountInfo) {
                        if (accountInfo != null && accountInfo.getUser() != null) {

                            mUsername.setText(accountInfo.getUserName());

                            String imageId = String.valueOf(accountInfo.getUser().getImageId());

                            if (!TextUtils.isEmpty(imageId)) {

                                DYImageLoader.getInstance().displayAvatar(Config.getConfig().getUserloginUrl() + imageId, mUserIcon);
                            }
                        } else {
                            DYToast.showShort(MineActivity.this, R.string.user_send_login_response_9);
                            startActivity(new Intent(MineActivity.this, LoginActivity.class));
                            finish();
                        }
                    }

                    @Override
                    public void onError() {
                        DYToast.showShort(MineActivity.this, R.string.user_send_login_response_9);
                        startActivity(new Intent(MineActivity.this, LoginActivity.class));
                        finish();
                    }
                });
            }
        } else {

        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine;
    }


    @Override
    public void onNetworkStateChanged(NetworkState networkState) {

    }

    @Override
    public void onErrorResponse(String operationType, Throwable throwable, String tag) {

    }

    @OnClick({R.id.miv_scan_code, R.id.miv_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.miv_scan_code://扫描二维码
                startActivity(new Intent(this, ScanCodeActivity.class));
                break;
            case R.id.miv_logout://退出登录
                CurrentUser.getInstance().clearUserInfo();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }
    }
}

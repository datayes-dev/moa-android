package com.datayes.dyoa.module.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.datayes.dyoa.R;
import com.datayes.dyoa.common.base.BaseActivity;
import com.datayes.dyoa.common.networkstatus.NetworkState;
import com.datayes.dyoa.module.user.CurrentUser;
import com.datayes.dyoa.module.user.activity.MineActivity;

public class SplashActivity extends BaseActivity {

    private static final int TIME_DELAY = 2000;

    private Handler mHandler = new Handler();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onStart() {
        super.onStart();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (CurrentUser.getInstance().isLogin()) {
                    startActivity(new Intent(SplashActivity.this, MineActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
                finish();
            }
        }, TIME_DELAY);

    }

    @Override
    public void onNetworkStateChanged(NetworkState networkState) {

    }
}
package com.datayes.dinnerbusiness.module.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.datayes.dinnerbusiness.R;
import com.datayes.dinnerbusiness.common.base.BaseActivity;
import com.datayes.dinnerbusiness.common.networkstatus.NetworkState;
import com.datayes.dinnerbusiness.module.user.CurrentUser;
import com.datayes.dinnerbusiness.module.user.activity.MineActivity;

public class SplashActivity extends BaseActivity {

    private static final int TIME_DELAY = 200;

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

        /*mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (CurrentUser.getInstance().isLogin()) {
                    startActivity(new Intent(SplashActivity.this, MineActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
                finish();
            }
        }, TIME_DELAY);*/
        if (CurrentUser.getInstance().isLogin()) {
            startActivity(new Intent(SplashActivity.this, MineActivity.class));
        } else {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        }
        finish();

    }

    @Override
    public void onNetworkStateChanged(NetworkState networkState) {

    }
}
package com.datayes.dyoa.module.login.activity;

import android.content.Intent;
import android.os.Bundle;

import com.datayes.dyoa.R;
import com.datayes.dyoa.common.base.BaseActivity;
import com.datayes.dyoa.common.networkstatus.NetworkState;
import com.datayes.dyoa.module.user.CurrentUser;
import com.datayes.dyoa.module.user.activity.MineActivity;

public class SplashActivity extends BaseActivity {

    private static final int sleepTime = 2000;

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

        new Thread(new Runnable() {
            public void run() {
                if (!CurrentUser.getInstance().isLogin()) {
                    //不存在token，登录
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                } else {
                    //存在token，免登录
                    startActivity(new Intent(SplashActivity.this, MineActivity.class));
                    finish();
                }
            }
        }).start();
    }

    @Override
    public void onNetworkStateChanged(NetworkState networkState) {

    }
}
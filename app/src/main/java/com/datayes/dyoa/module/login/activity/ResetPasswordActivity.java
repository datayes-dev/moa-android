package com.datayes.dyoa.module.login.activity;

import com.datayes.dyoa.R;
import com.datayes.dyoa.common.base.BaseActivity;
import com.datayes.dyoa.common.networkstatus.NetworkState;

/**
 * 重置用户密码
 * Created by hongfei.tao on 2016/9/14 10:42.
 */
public class ResetPasswordActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_reset_pwd;
    }

    @Override
    public void onNetworkStateChanged(NetworkState networkState) {

    }

    @Override
    public void onErrorResponse(String operationType, Throwable throwable, String tag) {

    }
}

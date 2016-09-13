package com.datayes.dyoa.module.activity;

import com.datayes.dyoa.common.base.BaseActivity;
import com.datayes.dyoa.common.networkstatus.NetworkState;

/**
 * Created by datayes on 16/9/13.
 */
public class TradeHistoryActivity  extends BaseActivity{

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    public void onNetworkStateChanged(NetworkState networkState) {

    }

    @Override
    public void onErrorResponse(String operationType, Throwable throwable, String tag) {

    }
}

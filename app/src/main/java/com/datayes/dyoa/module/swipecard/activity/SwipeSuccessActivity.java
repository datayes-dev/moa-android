package com.datayes.dyoa.module.swipecard.activity;

import com.datayes.dyoa.R;
import com.datayes.dyoa.common.base.BaseActivity;
import com.datayes.dyoa.common.networkstatus.NetworkState;

/**
 * Created by datayes on 16/9/13.
 */
public class SwipeSuccessActivity extends BaseActivity {

    public static final String SHOP_NAME_KEY = "shop_name_key";
    public static final String MONEY_VALUE_KEY = "money_value_key";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_trade_finish;
    }

    @Override
    public void onNetworkStateChanged(NetworkState networkState) {

    }

    @Override
    public void onErrorResponse(String operationType, Throwable throwable, String tag) {

    }
}

package com.datayes.dyoa.module.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.datayes.dyoa.R;
import com.datayes.dyoa.common.base.BaseActivity;
import com.datayes.dyoa.common.networkstatus.NetworkState;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by datayes on 16/9/13.
 */
public class tradeFinishActivity extends BaseActivity {

    @BindView(R.id.tv_shop_name)
    TextView mTvShopName;
    @BindView(R.id.tv_money)
    TextView mTvMoney;
    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.btn_pay)
    TextView mBtnPay;
    @BindView(R.id.tv_trade_history)
    TextView mTvTradeHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_finish);
        ButterKnife.bind(this);
    }

    @Override
    public void onNetworkStateChanged(NetworkState networkState) {

    }

    @Override
    public void onErrorResponse(String operationType, Throwable throwable, String tag) {

    }

    @OnClick({R.id.btn_pay, R.id.tv_trade_history})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_pay:
                break;
            case R.id.tv_trade_history:
                break;
        }
    }
}

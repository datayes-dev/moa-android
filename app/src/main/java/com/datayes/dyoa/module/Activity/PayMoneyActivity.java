package com.datayes.dyoa.module.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.datayes.dyoa.R;
import com.datayes.dyoa.common.base.BaseActivity;
import com.datayes.dyoa.common.networkstatus.NetworkState;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by datayes on 16/9/12.
 */
public class PayMoneyActivity extends BaseActivity {

    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.tv_shop_name)
    TextView mTvShopName;
    @BindView(R.id.ev_money)
    EditText mEvMoney;
    @BindView(R.id.btn_pay)
    TextView mBtnPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_money);
        ButterKnife.bind(this);
    }

    @Override
    public void onNetworkStateChanged(NetworkState networkState) {

    }

    @Override
    public void onErrorResponse(String operationType, Throwable throwable, String tag) {

    }

    @OnClick(R.id.btn_pay)
    public void onClick() {
    }
}

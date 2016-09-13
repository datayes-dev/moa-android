package com.datayes.dyoa.module.swipecard.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.datayes.dyoa.R;
import com.datayes.dyoa.common.base.BaseActivity;
import com.datayes.dyoa.common.networkstatus.NetworkState;
import com.datayes.dyoa.common.view.CTitle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by datayes on 16/9/13.
 */
public class SwipeSuccessActivity extends BaseActivity {

    public static final String SHOP_NAME_KEY = "shop_name_key";
    public static final String MONEY_VALUE_KEY = "money_value_key";
    public static final String TRADE_DATE_KEY = "trade_data_key";

    @BindView(R.id.ct_title)
    CTitle mCtTitle;
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
    protected int getLayoutId() {
        return R.layout.activity_trade_finish;
    }

    @Override
    public void onNetworkStateChanged(NetworkState networkState) {

    }

    @Override
    public void onErrorResponse(String operationType, Throwable throwable, String tag) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        initUI();

    }


    private void initUI() {

        String shopName = getIntent().getStringExtra(SHOP_NAME_KEY);
        String money = getIntent().getStringExtra(MONEY_VALUE_KEY);
        String date = getIntent().getStringExtra(TRADE_DATE_KEY);

        mTvMoney.setText(money);
        mTvShopName.setText(shopName);
        mTvDate.setText(date);

        mCtTitle.setLeftBtnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @OnClick({R.id.btn_pay, R.id.tv_trade_history})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_pay:
                finish();
                break;
            case R.id.tv_trade_history:
                Intent intent = new Intent(this, TradeHistoryActivity.class);
                startActivity(intent);
                break;
        }
    }
}

package com.datayes.dinnerbusiness.module.swipecard.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.datayes.dinnerbusiness.R;
import com.datayes.dinnerbusiness.common.base.BaseActivity;
import com.datayes.dinnerbusiness.common.networkstatus.NetworkState;
import com.datayes.dinnerbusiness.common.view.CTitle;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by datayes on 16/9/13.
 */
public class SwipeSuccessActivity extends BaseActivity {

    public static final String SHOP_NAME_KEY = "shop_name_key";
    public static final String MONEY_VALUE_KEY = "money_value_key";
    public static final String INFO_KEY = "info_key";
    public static final String ERROR_MESSAG = "error_message";


    boolean isSuccess;

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
    @BindView(R.id.tv_message)
    TextView mTvMessage;
    @BindView(R.id.img_info)
    ImageView mImgInfo;

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

        initUI();

    }


    private void initUI() {


        isSuccess = getIntent().getBooleanExtra(INFO_KEY, false);

        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowDate = sDateFormat.format(new Date());

        String date = nowDate;
        mTvDate.setText(date);

        if (isSuccess == true) {

            mTvMessage.setText("刷卡成功");
            mImgInfo.setImageResource(R.mipmap.ok_2);
            mTvTradeHistory.setVisibility(View.VISIBLE);
            mTvDate.setVisibility(View.VISIBLE);

        } else {

            String errorString = getIntent().getStringExtra(ERROR_MESSAG);
            if (errorString == null) {
                errorString = "刷卡失败";
            }
            mTvMessage.setText(errorString);

            mImgInfo.setImageResource(R.mipmap.error);
            mTvTradeHistory.setVisibility(View.GONE);
            mTvDate.setVisibility(View.INVISIBLE);
        }

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
                finish();
                break;
        }
    }
}

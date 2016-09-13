package com.datayes.dyoa.module.swipecard.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.datayes.dyoa.R;
import com.datayes.dyoa.common.base.BaseActivity;
import com.datayes.dyoa.common.network.BaseService;
import com.datayes.dyoa.common.networkstatus.NetworkState;
import com.datayes.dyoa.common.view.CTitle;
import com.datayes.dyoa.module.swipecard.activity.manager.SwipeManager;
import com.datayes.dyoa.module.swipecard.activity.manager.SwipeService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by datayes on 16/9/13.
 */
public class SwipeCardActivity extends BaseActivity {
    @BindView(R.id.ct_title)
    CTitle mCtTitle;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.tv_shop_name)
    TextView mTvShopName;
    @BindView(R.id.ev_money)
    EditText mEvMoney;
    @BindView(R.id.btn_pay)
    TextView mBtnPay;


    private SwipeManager mSwipeManager;
    private SwipeService mSwipeService;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_money;
    }

    @Override
    public void onNetworkStateChanged(NetworkState networkState) {

    }

    @Override
    public void onErrorResponse(String operationType, Throwable throwable, String tag) {

    }

    @Override
    public void networkFinished(String operationType, BaseService service, int code, String tag) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSwipeManager = new SwipeManager();
    }


    @Override
    public BaseService initService() {
        if (mSwipeService == null)
            mSwipeService = new SwipeService();
        return mSwipeService;
    }

    @OnClick(R.id.btn_pay)
    public void onClick() {
    }
}

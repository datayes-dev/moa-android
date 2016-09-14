package com.datayes.dyoa.module.swipecard.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.datayes.dyoa.R;
import com.datayes.dyoa.common.base.BaseActivity;
import com.datayes.dyoa.common.network.BaseService;
import com.datayes.dyoa.common.networkstatus.NetworkState;
import com.datayes.dyoa.common.view.CTitle;
import com.datayes.dyoa.module.swipecard.activity.adapter.TradeHistoryAdapter;
import com.datayes.dyoa.module.swipecard.activity.manager.SwipeManager;
import com.datayes.dyoa.module.swipecard.activity.manager.SwipeService;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by datayes on 16/9/13.
 */
public class TradeHistoryActivity extends BaseActivity {
    @BindView(R.id.ct_title)
    CTitle mCtTitle;
    @BindView(R.id.lv_history)
    ListView mLvHistory;


    private TradeHistoryAdapter mHistoryAdapter;
    private SwipeManager mSwipeManager;
    private SwipeService mSwipeService;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_history;
    }

    @Override
    public void onNetworkStateChanged(NetworkState networkState) {

    }

    @Override
    public void onErrorResponse(String operationType, Throwable throwable, String tag) {

    }

    @Override
    public void networkFinished(String operationType, BaseService service, int code, String tag) {
        if (mSwipeService.getTransactionListBean()!=null){
            mHistoryAdapter.setList(mSwipeService.getTransactionListBean().getTransactionBeanList());
        }
    }

    @Override
    public BaseService initService() {
        if (mSwipeService == null)
            mSwipeService = new SwipeService();
        return mSwipeService;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mSwipeManager = new SwipeManager();
        mSwipeManager.getTransactionHistoryList(this, this);
        initUI();
    }

    private void initUI() {

        mHistoryAdapter = new TradeHistoryAdapter(this);

        mCtTitle.setLeftBtnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mLvHistory.setAdapter(mHistoryAdapter);
    }

}

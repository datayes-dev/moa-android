package com.datayes.dinnerbusiness.module.swipecard.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.datayes.baseapp.tools.DYToast;
import com.datayes.dinnerbusiness.App;
import com.datayes.dinnerbusiness.R;
import com.datayes.dinnerbusiness.common.base.BaseActivity;
import com.datayes.dinnerbusiness.common.network.BaseService;
import com.datayes.dinnerbusiness.common.network.bean.TransactionListBean;
import com.datayes.dinnerbusiness.common.networkstatus.NetworkState;
import com.datayes.dinnerbusiness.common.view.CTitle;
import com.datayes.dinnerbusiness.module.login.activity.LoginActivity;
import com.datayes.dinnerbusiness.module.swipecard.adapter.TradeHistoryAdapter;
import com.datayes.dinnerbusiness.module.swipecard.manager.SwipeManager;
import com.datayes.dinnerbusiness.module.swipecard.service.SwipeService;
import com.datayes.dinnerbusiness.module.user.CurrentUser;
import com.datayes.dinnerbusiness.utils.AppUtil;

import java.util.ArrayList;

import butterknife.BindView;


/**
 * Created by datayes on 16/9/13.
 */
public class TradeHistoryActivity extends BaseActivity {

    private static final String firstHour = " 00:00:00";
    private static final String lastHour = " 23:59:59";

    @BindView(R.id.ct_title)
    CTitle mCtTitle;
    @BindView(R.id.lv_history)
    ListView mLvHistory;

    @BindView(R.id.tv_nodata)
    TextView mTvNodata;
    @BindView(R.id.tv_count)
    TextView mTvCount;


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
        hideLoading();
        DYToast.makeText(this, App.getInstance().getString(R.string.NoConnectionError), Toast.LENGTH_LONG).show();
        mTvNodata.setVisibility(View.VISIBLE);
        mHistoryAdapter.setList(new ArrayList<TransactionListBean.TransactionBean>());

    }

    @Override
    public void networkFinished(String operationType, BaseService service, int code, String tag) {
        hideLoading();
        if (mSwipeService.getTradeHistoryListBean() != null) {

            mHistoryAdapter.setList(mSwipeService.getTradeHistoryListBean().getHistoryBeanList());

            if (mHistoryAdapter.getCount() == 0) {

                mTvNodata.setVisibility(View.VISIBLE);
                mTvCount.setVisibility(View.GONE);
            } else {
                mTvCount.setVisibility(View.VISIBLE);
                mTvNodata.setVisibility(View.GONE);
                if (mSwipeService.getTradeHistoryListBean().getCount() !=null){
                    mTvCount.setText("今天一共有"+ mSwipeService.getTradeHistoryListBean().getCount() + "笔交易,以下为最新10条");
                }else {
                    mTvCount.setText("");
                }

            }
        } else {

            mTvNodata.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public BaseService initService() {

        mSwipeService = new SwipeService();
        return mSwipeService;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSwipeManager = new SwipeManager();

        initUI();
    }

    @Override
    protected void onResume() {
        String today = AppUtil.timestampToYearMonthDate(System.currentTimeMillis(), "yyyy-MM-dd");

        fetchNetWorkByDay(today);
        super.onResume();
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

    private void fetchNetWorkByDay(String today) {

        String begin = today + firstHour;
        String end = today + lastHour;

        if (!CurrentUser.getInstance().isLogin()) {

            Intent intent = new Intent(TradeHistoryActivity.this, LoginActivity.class);
            startActivity(intent);


        } else {

            showLoading();
            mTvNodata.setVisibility(View.GONE);
            mSwipeManager.getTradeHistoryList(this, this, begin, end);

        }


    }


}

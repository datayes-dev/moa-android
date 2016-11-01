package com.datayes.dinnerbusiness.module.swipecard.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.datayes.baseapp.tools.DYToast;
import com.datayes.dinnerbusiness.App;
import com.datayes.dinnerbusiness.R;
import com.datayes.dinnerbusiness.common.base.BaseActivity;
import com.datayes.dinnerbusiness.common.network.BaseService;
import com.datayes.dinnerbusiness.common.network.bean.TransactionListBean;
import com.datayes.dinnerbusiness.common.networkstatus.NetworkState;
import com.datayes.dinnerbusiness.common.view.CTitle;
import com.datayes.dinnerbusiness.module.swipecard.adapter.TradeHistoryAdapter;
import com.datayes.dinnerbusiness.module.swipecard.manager.SwipeManager;
import com.datayes.dinnerbusiness.module.swipecard.service.SwipeService;
import com.datayes.dinnerbusiness.utils.AppUtil;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by datayes on 16/9/13.
 */
public class TradeHistoryActivity extends BaseActivity implements TimePickerView.OnTimeSelectListener {

    private static final String firstHour = " 00:00:00";
    private static final String lastHour = " 23:59:59";

    @BindView(R.id.ct_title)
    CTitle mCtTitle;
    @BindView(R.id.lv_history)
    ListView mLvHistory;
    @BindView(R.id.tv_picker)
    TextView mTvPicker;

    TimePickerView pvTime;
    @BindView(R.id.tv_nodata)
    TextView mTvNodata;


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
        if (mSwipeService.getTransactionListBean() != null) {
            mHistoryAdapter.setList(mSwipeService.getTransactionListBean().getTransactionBeanList());

            if (mHistoryAdapter.getCount() == 0) {

                mTvNodata.setVisibility(View.VISIBLE);
            } else {
                mTvNodata.setVisibility(View.GONE);
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

    private void initUI() {

        mHistoryAdapter = new TradeHistoryAdapter(this);

        mCtTitle.setLeftBtnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mLvHistory.setAdapter(mHistoryAdapter);


        if (pvTime == null) {

            long time = System.currentTimeMillis();

            pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
            pvTime.setTime(new Date(time));
            pvTime.setCyclic(false);
            pvTime.setCancelable(true);

            pvTime.setOnTimeSelectListener(this);

        }
        String today = AppUtil.timestampToYearMonthDate(System.currentTimeMillis(), "yyyy-MM-dd");

        mTvPicker.setText("当前日期:" + today);

        fetchNetWorkByDay(today);

    }

    private void fetchNetWorkByDay(String today) {

        String begin = today + firstHour;
        String end = today + lastHour;

        showLoading();
        mTvNodata.setVisibility(View.GONE);
        mSwipeManager.getTransactionHistoryList(this, this, begin, end);
    }


    @OnClick(R.id.tv_picker)
    public void onClick() {

        if (loading.getShowing()) return;

        if (pvTime.isShowing()) return;
        pvTime.show();
    }

    @Override
    public void onTimeSelect(java.util.Date date) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String today = format.format(date);

        mTvPicker.setText("当前日期:" + today);
        fetchNetWorkByDay(today);

    }
}

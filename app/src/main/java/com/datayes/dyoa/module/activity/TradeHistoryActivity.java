package com.datayes.dyoa.module.activity;
import android.widget.ListView;

import com.datayes.dyoa.R;
import com.datayes.dyoa.common.base.BaseActivity;
import com.datayes.dyoa.common.networkstatus.NetworkState;
import com.datayes.dyoa.common.view.CTitle;

import butterknife.BindView;


/**
 * Created by datayes on 16/9/13.
 */
public class TradeHistoryActivity extends BaseActivity {

    @BindView(R.id.ct_title)
    CTitle mCtTitle;
    @BindView(R.id.lv_history)
    ListView mLvHistory;

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

}

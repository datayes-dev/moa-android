package com.datayes.dyoa.module.swipecard.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.datayes.dyoa.R;
import com.datayes.dyoa.common.base.BaseActivity;
import com.datayes.dyoa.common.networkstatus.NetworkState;
import com.datayes.dyoa.common.view.CTitle;
import com.datayes.dyoa.module.swipecard.activity.adapter.TradeHistoryAdapter;

import java.util.ArrayList;
import java.util.List;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         ButterKnife.bind(this);
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

        List<String> stringList = new ArrayList<>();
        stringList.add("哈哈哈");
        stringList.add("哟哟哟");
        stringList.add("周末快乐");
        stringList.add("东方明珠");
        stringList.add("天天向上");
        stringList.add("天天向上");
        stringList.add("天天向上");
        stringList.add("好好学习");
        mHistoryAdapter.setList(stringList);
        mLvHistory.setAdapter(mHistoryAdapter);
    }

}

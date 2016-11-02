package com.datayes.dinnerbusiness.module.swipecard.service;

import com.datayes.dinnerbusiness.common.network.BaseService;
import com.datayes.dinnerbusiness.common.network.bean.RestaurantListBean;
import com.datayes.dinnerbusiness.common.network.bean.TradeHistoryListBean;
import com.datayes.dinnerbusiness.common.network.bean.TranResultBean;
import com.datayes.dinnerbusiness.common.network.bean.TransactionListBean;

/**
 * Created by datayes on 16/9/13.
 */
public class SwipeService extends BaseService {


    public TradeHistoryListBean getTradeHistoryListBean() {
        return mTradeHistoryListBean;
    }

    private TradeHistoryListBean mTradeHistoryListBean; //交易记录(新)

    public TranResultBean getTranResultBean() {
        return mTranResultBean;
    }

    private TranResultBean mTranResultBean; //交易接口(新)



    private RestaurantListBean mRestaurantListBean;

    private TransactionListBean mTransactionListBean;

    public RestaurantListBean getRestaurantListBean() {
        return mRestaurantListBean;
    }

    public TransactionListBean getTransactionListBean() {
        return mTransactionListBean;
    }

}

package com.datayes.dinnerbusiness.module.swipecard.service;

import com.datayes.dinnerbusiness.common.network.BaseService;
import com.datayes.dinnerbusiness.common.network.bean.RestaurantListBean;
import com.datayes.dinnerbusiness.common.network.bean.TransactionListBean;

/**
 * Created by datayes on 16/9/13.
 */
public class SwipeService extends BaseService {

    private RestaurantListBean mRestaurantListBean;

    private TransactionListBean mTransactionListBean;

    public RestaurantListBean getRestaurantListBean() {
        return mRestaurantListBean;
    }

    public TransactionListBean getTransactionListBean() {
        return mTransactionListBean;
    }

}

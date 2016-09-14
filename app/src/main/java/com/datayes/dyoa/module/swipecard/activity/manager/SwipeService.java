package com.datayes.dyoa.module.swipecard.activity.manager;

import com.datayes.dyoa.common.network.BaseService;
import com.datayes.dyoa.common.network.bean.RestaurantListBean;
import com.datayes.dyoa.common.network.bean.TransactionListBean;

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

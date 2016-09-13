package com.datayes.dyoa.module.swipecard.activity.manager;

import com.datayes.dyoa.common.network.BaseService;
import com.datayes.dyoa.common.network.bean.RestaurantListBean;

/**
 * Created by datayes on 16/9/13.
 */
public class SwipeService extends BaseService {

    private RestaurantListBean mRestaurantListBean;

    public RestaurantListBean getRestaurantListBean() {
        return mRestaurantListBean;
    }
}

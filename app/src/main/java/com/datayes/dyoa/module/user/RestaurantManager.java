package com.datayes.dyoa.module.user;

import com.datayes.dyoa.common.network.bean.RestaurantListBean;

/**
 * Created by datayes on 16/9/14.
 */
public class RestaurantManager {

    private RestaurantListBean mRestaurantListBean;

    public void setRestaurantListBean(RestaurantListBean restaurantListBean) {
        mRestaurantListBean = restaurantListBean;
    }

    public RestaurantListBean getRestaurantListBean() {
        return mRestaurantListBean;
    }


    private RestaurantManager(){

    }

    public static RestaurantManager getInstance(){
        return CeateRestaurantManager.mRestaurantManager;
    }


    private static class  CeateRestaurantManager{
        private final static RestaurantManager mRestaurantManager = new RestaurantManager();
    }

}

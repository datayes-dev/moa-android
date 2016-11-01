package com.datayes.dinnerbusiness.module.user;

import com.datayes.dinnerbusiness.common.network.BaseService;
import com.datayes.dinnerbusiness.common.network.NetCallBack;
import com.datayes.dinnerbusiness.common.network.bean.RestaurantListBean;
import com.datayes.dinnerbusiness.module.swipecard.manager.SwipeManager;
import com.datayes.dinnerbusiness.module.swipecard.service.SwipeService;

/**
 * Created by datayes on 16/9/14.
 */
public class RestaurantManager implements NetCallBack, NetCallBack.InitService {

    private RestaurantListBean mRestaurantListBean;

    private SwipeManager mSwipeManager;
    private SwipeService mSwipeService;

    public void setRestaurantListBean(RestaurantListBean restaurantListBean) {
        mRestaurantListBean = restaurantListBean;
    }

    public RestaurantListBean getRestaurantListBean() {
        return mRestaurantListBean;
    }


    /**
     * 获取网络餐厅列表
     *
     * @author datayes
     * @time create at 16/9/19
     */
//    public void sendFetchRestaurantRequest() {
//        mSwipeManager.getRestaurantList(this, this);
//    }


    private RestaurantManager() {
        mSwipeManager = new SwipeManager();
        mSwipeService = new SwipeService();
    }

    public static RestaurantManager getInstance() {
        return CeateRestaurantManager.mRestaurantManager;
    }

    @Override
    public BaseService initService() {
        return mSwipeService;
    }

    @Override
    public void networkFinished(String operationType, BaseService service, int code, String tag) {
        if (operationType.equals("/restaurant") && mSwipeService.getRestaurantListBean() != null)
            this.setRestaurantListBean(mSwipeService.getRestaurantListBean());
    }

    @Override
    public void onErrorResponse(String operationType, Throwable throwable, String tag) {

    }


    private static class CeateRestaurantManager {
        private final static RestaurantManager mRestaurantManager = new RestaurantManager();
    }

}

package com.datayes.dinnercustom.swipecard.manager;

import com.datayes.dyoa.common.config.Config;
import com.datayes.dyoa.common.network.NetCallBack;
import com.datayes.dyoa.common.network.bean.RestaurantListBean;
import com.datayes.dyoa.common.network.bean.TransactionListBean;
import com.datayes.dyoa.common.network.manager.base.JsonRequestManager;
import com.google.gson.JsonObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by datayes on 16/9/13.
 */
public class SwipeManager extends JsonRequestManager {


    /**
     * 获取饭店列表
     *
     * @author datayes
     * @time create at 16/9/13
     */
    public void getRestaurantList(NetCallBack netCallBack,
                                  NetCallBack.InitService baseService) {
        start(netCallBack,
                baseService,
                getInstance().getRestaurantList(Config.ConfigUrlType.ORDER.getUrl()),
                Config.ConfigUrlType.ORDER,
                RestaurantListBean.class);
    }


    /**
     * 执行交易
     *
     * @author datayes
     * @time create at 16/9/13
     */
    public void sendUserTradeMessage(NetCallBack netCallBack,
                                     NetCallBack.InitService baseService,
                                     String price,
                                     String restaurant,
                                     String memo) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("price", price);
        jsonObject.addProperty("restaurant", restaurant);
        jsonObject.addProperty("memo", memo);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=UTF-8"), jsonObject.toString());

        start(netCallBack,
                baseService,
                getInstance().handleTransaction(Config.ConfigUrlType.ORDER.getUrl(), body),
                Config.ConfigUrlType.ORDER,
                RestaurantListBean.class);
    }


    /**
     * 获取交易记录
     *
     * @author datayes
     * @time create at 16/9/13
     */
    public void getTransactionHistoryList(NetCallBack netCallBack,
                                          NetCallBack.InitService baseService) {
        start(netCallBack,
                baseService,
                getInstance().getPersonalDeals(Config.ConfigUrlType.ORDER.getUrl()),
                Config.ConfigUrlType.ORDER,
                TransactionListBean.class);
    }

}

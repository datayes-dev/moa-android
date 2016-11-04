package com.datayes.dinnerbusiness.module.swipecard.manager;

import com.datayes.dinnerbusiness.bean.user.AccountBean;
import com.datayes.dinnerbusiness.common.config.Config;
import com.datayes.dinnerbusiness.common.network.NetCallBack;
import com.datayes.dinnerbusiness.common.network.bean.RestaurantListBean;
import com.datayes.dinnerbusiness.common.network.bean.TradeHistoryListBean;
import com.datayes.dinnerbusiness.common.network.bean.TranResultBean;
import com.datayes.dinnerbusiness.common.network.bean.TransactionListBean;
import com.datayes.dinnerbusiness.common.network.manager.base.JsonRequestManager;
import com.datayes.dinnerbusiness.module.login.manager.UserManager;
import com.datayes.dinnerbusiness.module.user.CurrentUser;
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
                                     String qrstring) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("qrstring", qrstring);
        jsonObject.addProperty("restaurant", "888cf414-a270-11e6-b99a-02730bb67404RES_");
        jsonObject.addProperty("memo", "");

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=UTF-8"), jsonObject.toString());

        start(netCallBack,
                baseService,
                getInstance().handleTransaction(Config.ConfigUrlType.ORDER.getUrl(), body),
                Config.ConfigUrlType.ORDER,
                TranResultBean.class);
    }


    /**
     * 获取交易记录(old)
     *
     * @author datayes
     * @time create at 16/9/13
     */
    public void getTransactionHistoryList(NetCallBack netCallBack,
                                          NetCallBack.InitService baseService,
                                          String begin,
                                          String end) {

        if (CurrentUser.getInstance().getAccountInfo() != null) {

            AccountBean bean = CurrentUser.getInstance().getAccountInfo();

            if (bean.getActivieAccount() != null) {

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("begin", begin);
                jsonObject.addProperty("end", end);

                jsonObject.addProperty("admin", bean.getActivieAccount().getPrincipalName());

                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=UTF-8"), jsonObject.toString());

                start(netCallBack,
                        baseService,
                        getInstance().getPersonalDeals(Config.ConfigUrlType.ORDER.getUrl(), body),
                        Config.ConfigUrlType.ORDER,
                        TransactionListBean.class);
            }
        }
    }

    /**
     * 获取交易记录(新)
     *
     * @author datayes
     * @time create at 16/9/13
     */
    public void getTradeHistoryList(NetCallBack netCallBack,
                                          NetCallBack.InitService baseService,
                                          String begin,
                                          String end) {

        if (CurrentUser.getInstance().getAccountInfo() != null) {

            AccountBean bean = CurrentUser.getInstance().getAccountInfo();

            if (bean.getActivieAccount() != null) {

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("begin", begin);
                jsonObject.addProperty("end", end);

//                jsonObject.addProperty("admin", bean.getActivieAccount().getPrincipalName());

                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=UTF-8"), jsonObject.toString());

                start(netCallBack,
                        baseService,
                        getInstance().getTradeHistory(Config.ConfigUrlType.ORDER.getUrl(), body),
                        Config.ConfigUrlType.ORDER,
                        TradeHistoryListBean.class);
            }
        }
    }


}

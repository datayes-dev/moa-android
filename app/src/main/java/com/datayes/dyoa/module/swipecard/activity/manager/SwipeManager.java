package com.datayes.dyoa.module.swipecard.activity.manager;

import com.datayes.dyoa.common.config.Config;
import com.datayes.dyoa.common.network.NetCallBack;
import com.datayes.dyoa.common.network.manager.base.JsonRequestManager;

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
        start("",netCallBack,baseService,getInstance().getRestaurantList(), Config.ConfigUrlType.NOMRAL);
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
        start("",netCallBack,baseService,getInstance().handleTransaction(), Config.ConfigUrlType.NOMRAL);
    }

}

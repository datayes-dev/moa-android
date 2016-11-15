package com.datayes.dinnerbusiness.module.code.activity.manager;

import com.datayes.dinnerbusiness.common.network.BaseService;
import com.datayes.dinnerbusiness.common.network.NetCallBack;
import com.datayes.dinnerbusiness.common.network.bean.WeiXinTokenBean;
import com.datayes.dinnerbusiness.module.swipecard.manager.SwipeManager;
import com.datayes.dinnerbusiness.module.swipecard.service.SwipeService;

import java.util.Calendar;

/**
 * Created by datayes on 16/11/15.
 */

public enum WeiXinManager {

    INSTANCE;


    public String getAccessToken() {

        if (mTokenBean != null) {
            return mTokenBean.getAcceessToken();
        } else {
            return "";
        }
    }

    private WeiXinTokenBean mTokenBean;

    private SwipeManager mSwipeManager;

    private SwipeService mSwipeService;

    private long lastTimeMilles; //最新请求token的时间戳

    private long expiresIn = 3600 * 1000; //默认过期为1小时


    public void freshWeixinToken() {

        if (mSwipeManager == null) {
            mSwipeManager = new SwipeManager();
        }

        if (needRefreshToken()) {

            mSwipeManager.getAccessTokenMessage(new NetCallBack() {
                @Override
                public void networkFinished(String operationType, BaseService service, int code, String tag) {

                    if (mSwipeService != null) {

                        lastTimeMilles = Calendar.getInstance().getTimeInMillis();

                        mTokenBean = mSwipeService.getWeiXinTokenBean();

                    }
                }

                @Override
                public void onErrorResponse(String operationType, Throwable throwable, String tag) {
                    if (operationType != null) {

                    }
                }
            }, new NetCallBack.InitService() {
                @Override
                public BaseService initService() {
                    if (mSwipeService == null)
                        mSwipeService = new SwipeService();
                    return mSwipeService;
                }
            });
        }

    }


    /**
     * 是否需要重新刷新token true:需要  false:不需要
     * Create by zhizhong.zhou at 16/11/15.
     */
    private boolean needRefreshToken() {

        long currentTimeMillis = Calendar.getInstance().getTimeInMillis();

        if ((currentTimeMillis - lastTimeMilles) > expiresIn) {
            return true;
        } else {
            return false;
        }

    }


}

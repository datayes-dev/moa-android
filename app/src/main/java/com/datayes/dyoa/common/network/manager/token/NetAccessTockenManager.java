/*
 * *
 *  * 通联数据机密
 *  * --------------------------------------------------------------------
 *  * 通联数据股份公司版权所有 © 2013-2016
 *  *
 *  * 注意：本文所载所有信息均属于通联数据股份公司资产。本文所包含的知识和技术概念均属于
 *  * 通联数据产权，并可能由中国、美国和其他国家专利或申请中的专利所覆盖，并受商业秘密或
 *  * 版权法保护。
 *  * 除非事先获得通联数据股份公司书面许可，严禁传播文中信息或复制本材料。
 *  *
 *  * DataYes CONFIDENTIAL
 *  * --------------------------------------------------------------------
 *  * Copyright © 2013-2016 DataYes, All Rights Reserved.
 *  *
 *  * NOTICE: All information contained herein is the property of DataYes
 *  * Incorporated. The intellectual and technical concepts contained herein are
 *  * proprietary to DataYes Incorporated, and may be covered by China, U.S. and
 *  * Other Countries Patents, patents in process, and are protected by trade
 *  * secret or copyright law.
 *  * Dissemination of this information or reproduction of this material is
 *  * strictly forbidden unless prior written permission is obtained from DataYes.
 *
 */

package com.datayes.dyoa.common.network.manager.token;

import com.datayes.baseapp.tools.DYLog;
import com.datayes.baseapp.utils.TimeUtil;
import com.datayes.dyoa.bean.user.UserLoginBean;
import com.datayes.dyoa.common.config.Config;
import com.datayes.dyoa.module.user.CurrentUser;
import com.datayes.dyoa.common.network.manager.base.BaseRequestManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 网络层，accessTocken管理器
 * Created by shenen.gao on 2016/9/5.
 */
public enum NetAccessTockenManager {
    INSTANCE,;

    final String Tag = "NetAccessTockenManager";

    //是否处于刷新tocken的状态
    private boolean mOnRefreshTocken = false;
    private List<RequestCache> mCaches = new ArrayList<>();

    public boolean isOnRefreshTocken() {
        return mOnRefreshTocken;
    }

    public void setOnRefreshTocken(boolean mOnRefreshTocken) {

        if (this.mOnRefreshTocken != mOnRefreshTocken) {

            this.mOnRefreshTocken = mOnRefreshTocken;

            if (mOnRefreshTocken)
                refreshAccessTocken();
        }
    }

    /**
     * 把请求放入请求队列（也就是开始请求）
     *
     * @param call
     * @param callback
     */
    public void enqueue(Call call, Callback callback) {

        if (call != null && callback != null) {

            if (mOnRefreshTocken) {

                addCache(call, callback);

            } else {

                call.enqueue(callback);
            }
        }
    }

    /**
     * 添加请求缓存
     *
     * @param call
     * @param callback
     */
    public synchronized void addCache(Call call, Callback callback) {

        if (call != null && callback != null) {

            mCaches.add(new RequestCache(call.clone(), callback));

            DYLog.d(Tag, "拦截请求，加入缓存：" + call.request().url().url().getPath());
        }
    }

    /**
     * 添加因为需要登陆的请求缓存
     * @param call
     * @param callback
     */
    public synchronized void addNeedLoginCache(Call call, Callback callback) {

        if (call != null && callback != null) {

            if (mCaches.isEmpty()) {

                DYLog.d(Tag, "needLogin加入缓存（自己）：" + call.request().url().url().getPath());

            } else {

                DYLog.d(Tag, "needLogin加入缓存（其他）：" + call.request().url().url().getPath());
            }

            mCaches.add(new RequestCache(call.clone(), callback));
        }
    }

    /**
     * 把请求缓存中的请求发出去
     */
    public void enqueueCaches() {

        mOnRefreshTocken = false;

        if (!mCaches.isEmpty()) {

            for (RequestCache cache : mCaches) {

                if (cache.mCall != null && cache.mCallBack != null && !cache.mCall.isExecuted()) {

                    cache.mCall.enqueue(cache.mCallBack);
                    DYLog.d(Tag, "发起缓存请求：" + cache.mCall.request().url().url().getPath());
                }
            }

            mCaches.clear();
        }
    }

    /**
     * 清空请求队列
     */
    protected void clearCaches() {

        mCaches.clear();
        mOnRefreshTocken = false;

        DYLog.d(Tag, "清空缓存");
    }

    //TODO 为了避免死循环，在这里添加时间限制
    private static long mTimestamp = 0;

    /**
     * 刷新refreshTocken
     */
    protected void refreshAccessTocken() {

        if (CurrentUser.getInstance().isLogin() && (System.currentTimeMillis() - mTimestamp) > TimeUtil.ONE_MILLION_SECOND * 60) {

            DYLog.d(Tag, "开始刷新token");

            mTimestamp = System.currentTimeMillis();

            Callback callback = new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    String result = response.body();

                    if (result != null) {

                        try {

                            JSONObject jsonObject = new JSONObject(result);
                            UserLoginBean bean = new UserLoginBean();
                            bean.parseJsonObject(jsonObject);

                            if ((bean.getCode() == -120)) {

                                CurrentUser.getInstance().clearUserInfo();
                                clearCaches();
                                DYLog.d(Tag, "刷新token失败，APP登出");

                            } else if (bean.getCode() > 0) {

                                enqueueCaches();
                                DYLog.d(Tag, "刷新token成功");

                            } else {

                                clearCaches();
                            }

                        } catch (JSONException e) {

                            clearCaches();

                            DYLog.d(Tag, "刷新token失败，" + e.getMessage());
                        }

                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                    clearCaches();
                }
            };

            Call<String> call = BaseRequestManager.getInstance().sendRefreshTocken(
                    Config.ConfigUrlType.USER_MASTER.getUrl(),
                    "refresh_token", CurrentUser.sharedInstance().getRefresh_token());

            call.enqueue(callback);

        } else {

            clearCaches();
        }
    }


    private class RequestCache {

        Call mCall;

        Callback mCallBack;

        private RequestCache(Call call, Callback callback) {

            mCall = call;
            mCallBack = callback;
        }
    }

}

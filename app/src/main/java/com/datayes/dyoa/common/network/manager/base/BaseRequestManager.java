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

package com.datayes.dyoa.common.network.manager.base;

import android.text.TextUtils;

import com.datayes.dyoa.bean.user.UserLoginBean;
import com.datayes.dyoa.common.config.Config;
import com.datayes.dyoa.common.network.OkHttpClientSingleton;
import com.datayes.dyoa.common.network.converter.proto.IrrProtoConverterFactory;
import com.datayes.dyoa.common.network.error.NetWorkException;
import com.datayes.dyoa.common.network.error.ServerException;
import com.datayes.dyoa.common.network.manager.token.NetAccessTockenManager;
import com.datayes.dyoa.common.network.service.AppService;
import com.datayes.dyoa.module.user.CurrentUser;
import com.datayes.dyoa.utils.AppUtil;
import com.datayes.dyoa.utils.DYCookieManager;

import java.util.List;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 请求管理器基类(ProtoBuf,Json等数据类型请求)
 * Created by hongfei.tao on 2016/9/1 16:51.
 */
public class BaseRequestManager {

    private static Retrofit mRetrofit;
    private static AppService mInstance;

    protected static String getBaseUrl() {
        return Config.getConfig().getBaseUrl();
    }

    public static AppService getInstance() {
        if (mRetrofit == null) {
            synchronized (ProtoRequestManager.class) {
                mRetrofit = new Retrofit.Builder()
                        .client(OkHttpClientSingleton.getInstance())
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(IrrProtoConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(getBaseUrl())
                        .build();

                mInstance = mRetrofit.create(AppService.class);
            }
        }

        return mInstance;
    }

    /**
     * @param response
     * @return
     */
    public Throwable checkResponseCode(Response response) {

        if (response != null) {

            int code = response.code();

            if (code > 200 && code <= 499) {

                return new NetWorkException(code + "");

            } else if (code >= 500) {

                return new ServerException(code + "");
            }
        }

        return null;
    }

    /**
     * 当tocken失效的操作
     *
     * @param code
     * @return
     */
    public synchronized boolean checkTockenNeedLogin(Call call, Callback callback, int code) {

        if ((code == -403 || isOuttime()) && CurrentUser.getInstance().isLogin()) {

            NetAccessTockenManager manager = NetAccessTockenManager.INSTANCE;

            if (call != null && callback != null) {

                if (!manager.isOnRefreshTocken()) {

                    manager.addNeedLoginCache(call, callback);
                    NetAccessTockenManager.INSTANCE.setOnRefreshTocken(true);

                } else {

                    manager.addNeedLoginCache(call, callback);
                }
            }

            return false;
        }

        return true;
    }

    /**
     * 是否token超时
     *
     * @return
     */
    protected synchronized boolean isOuttime() {

        //TODO 刷新失败之后有可能导致死循环，本地不做超时

        UserLoginBean userLoginBean = CurrentUser.getInstance().getUserInfo();

        if (userLoginBean != null
                && !AppUtil.checkS(userLoginBean.getRefresh_token()).equals("")) {

            return System.currentTimeMillis() > userLoginBean.getCurrentTime();
        }

        return false;
    }

    /**
     * 保存Cookie值
     *
     * @author hongfei.tao
     * @time create at 2016/9/8 15:34
     */
    protected void saveCookie(Headers headers) {
        List<String> cookieList = headers.values("Set-Cookie");

        for (String tempCookie : cookieList) {
            if (!TextUtils.isEmpty(tempCookie) && !tempCookie.equals(DYCookieManager.getInstance().getCookie()) && tempCookie.startsWith(DYCookieManager.PREFIX_COOKIE)) {
                DYCookieManager.getInstance().setCookie(tempCookie);
                break;
            }
        }
    }

}

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

package com.datayes.dyoa.common.network.interceptor;


import com.datayes.dyoa.module.user.CurrentUser;
import com.datayes.dyoa.utils.DYCookieManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 完成添加Token的逻辑
 * Created by hongfei.tao on 2016/9/5 11:25.
 */
public class TokenInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request.Builder builder = chain.request().newBuilder();

        //该处是为所有的Retrofit请求添加了token值
        if (CurrentUser.getInstance().isLogin()) {

            builder.addHeader("Authorization", "Bearer " + CurrentUser.sharedInstance().getAccess_token());
        }

        //该处是为所有的Retrofit请求添加了cookie值
        if (DYCookieManager.getInstance().hasCookie()) {

            builder.addHeader("cookie", DYCookieManager.getInstance().getCookie());
        }

        return chain.proceed(builder.build());
    }
}

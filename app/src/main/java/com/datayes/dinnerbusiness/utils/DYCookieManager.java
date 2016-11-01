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
package com.datayes.dinnerbusiness.utils;

import android.text.TextUtils;

import java.util.Map;

public class DYCookieManager {
    @SuppressWarnings("unused")
    private static final String TAG = "DYCookieManager";

    public static final String PREFIX_COOKIE = "cloud-anonymous-token";

    private static DYCookieManager sInstance = new DYCookieManager();

    private String cookie;

    private DYCookieManager() {
    }

    public static DYCookieManager getInstance() {
        return sInstance;
    }

    public void setCookie(Map<String, String> headers) {
        String cookieFromResponse = headers.get("Set-Cookie");
        if (cookieFromResponse != null) {
            cookie = cookieFromResponse;
        }
//         

//         String cookieFromResponse = headers.get("Set-Cookie");
//         if(cookieFromResponse != null) {
//             String[] tokens = cookieFromResponse.split(";");
//             for(int i = 0; i < tokens.length; i++) {
//            	 String token = tokens[0].trim();
//            	 if(token.startsWith("cloud-sso-token=")) {
//            		 cookie = token;
//            		 break;
//            	 }
//             }
//             DYLog.i(TAG,"save cookie: "+ cookieFromResponse);
//         }

    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getCookie() {
        return cookie;
    }

    public boolean hasCookie() {
        return !TextUtils.isEmpty(cookie);
    }

    public void clear() {
        cookie = null;
    }

}

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

package com.datayes.dyoa.common.sharedpreferences;

import android.content.SharedPreferences;

import com.datayes.dinnercustom.App;
import com.datayes.dyoa.bean.user.UserLoginBean;
import com.google.gson.Gson;

import java.util.List;

/**
 * sharedPreferences统一管理
 * 
 * @author nianyi.yang
 * @date create at 2016/9/12 15:00
 */
public class MySharedPreferences {

    private static final String SHARED = "shared";
    private static final String SAVE_USER_INFO = "saveUserInfo";// 存储用户信息

    private static MySharedPreferences sharedPreferences;

    private MySharedPreferences() { }

    public static final MySharedPreferences getSharedPreferences() {
        if (sharedPreferences == null) {
            sharedPreferences = new MySharedPreferences();
        }
        return sharedPreferences;

    }

    private void saveLocalListData(String name, List<? extends Object> list) {

        Gson gson = new Gson();
        String str = gson.toJson(list);
        SharedPreferences.Editor sharedata = App.getInstance()
                .getSharedPreferences(SHARED, 0).edit();
        sharedata.putString(name, str);
        sharedata.commit();
    }

    private void clearLocalListData(String name) {

        SharedPreferences.Editor sharedata = App.getInstance()
                .getSharedPreferences(SHARED, 0).edit();
        sharedata.putString(name, "");
        sharedata.commit();
    }

    /**
     * 用户信息保存
     *
     * @author nianyi.yang
     * @date create at 2016/9/12 14:57
     */
    public void saveUserInfo(UserLoginBean userInfo) {
        Gson gson = new Gson();
        String userInfoJson = gson.toJson(userInfo);
        SharedPreferences.Editor sharedata = App.getInstance()
                .getSharedPreferences(SHARED, 0).edit();
        sharedata.putString(SAVE_USER_INFO, userInfoJson);
        sharedata.commit();
    }

    /**
     * 获取当前用户信息
     *
     * @author nianyi.yang
     * @date create at 2016/9/12 14:57
     */
    public UserLoginBean getUserInfo() {
        SharedPreferences sharedata = App.getInstance().getSharedPreferences(
                SHARED, 0);
        String data = sharedata.getString(SAVE_USER_INFO, "");
        Gson gson = new Gson();
        UserLoginBean userLoginBean = gson.fromJson(data, UserLoginBean.class);
        return userLoginBean;
    }

    /**
     * 清空用户信息
     *
     * @author nianyi.yang
     * @date create at 2016/9/12 14:57
     */
    public void clearUserInfo() {
        clearLocalListData(SAVE_USER_INFO);
    }
}

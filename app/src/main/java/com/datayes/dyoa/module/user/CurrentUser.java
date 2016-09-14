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

package com.datayes.dyoa.module.user;


import com.datayes.baseapp.utils.StringUtil;
import com.datayes.dyoa.bean.user.AccountBean;
import com.datayes.dyoa.bean.user.UserLoginBean;
import com.datayes.dyoa.common.network.BaseService;
import com.datayes.dyoa.common.network.NetCallBack;
import com.datayes.dyoa.common.sharedpreferences.MySharedPreferences;
import com.datayes.dyoa.module.login.manager.UserManager;
import com.datayes.dyoa.module.login.service.UserService;


/**
 * @author rong.mo
 * @version 1.0
 * @className CurrentUser
 * @brief 类描述  用户信息管理
 * @date 2015-12-23 下午8:09:09
 */
public class CurrentUser {

    public CurrentUser() {
        mManager = new UserManager();
    }

    private static CurrentUser instance;

    public static CurrentUser sharedInstance() {
        if (instance == null) {
            synchronized (CurrentUser.class) {
                if (instance == null) {
                    instance = new CurrentUser();
                }
            }
        }
        return instance;
    }

    private UserLoginBean userLoginBean;
    private AccountBean accountInfoBean;
    private UserManager mManager;


    public static CurrentUser getInstance() {
        sharedInstance();
        return instance;
    }

    public static void setInstance(CurrentUser instance) {
        CurrentUser.instance = instance;
    }

    public UserLoginBean getUserLoginBean() {
        return userLoginBean;
    }

    /**
     * @author rong.mo
     *  @brief 方法描述  用户信息
     *  @param userLoginBean void      
     *  @throws  
     * @date 2015-12-23 下午8:09:39
     *     
     */
    public synchronized void saveUserInfo(UserLoginBean userLoginBean) {
        if (userLoginBean == null)
            return;
        this.userLoginBean = userLoginBean;
        MySharedPreferences.getSharedPreferences().saveUserInfo(userLoginBean);
    }

    /**
     * @author rong.mo
     *  @brief 方法描述  读取当前用户
     *  @back_return UserLoginBean      
     *  @throws  
     * @date 2015-12-23 下午8:32:04
     *     
     */
    public synchronized UserLoginBean getUserInfo() {
        if (userLoginBean == null)
            userLoginBean = MySharedPreferences.getSharedPreferences().getUserInfo();

        return userLoginBean;

    }

    /**
     * @author rong.mo
     *  @brief 方法描述  获取Access_token
     *  @back_return String      
     *  @throws  
     * @date 2015-12-25 下午4:37:07
     *     
     */
    public synchronized String getAccess_token() {

        UserLoginBean userLoginBean = getUserInfo();
        if (userLoginBean != null) {

            return StringUtil.checkString(userLoginBean.getAccess_token());
        }

        return "";
    }

    /**
     * @author rong.mo
     *  @brief 方法描述  获取Refresh_token
     *  @back_return String      
     *  @throws  
     * @date 2015-12-25 下午4:36:47
     *     
     */
    public synchronized String getRefresh_token() {
        UserLoginBean userLoginBean = getUserInfo();
        if (userLoginBean != null) {
            return StringUtil.checkString(userLoginBean.getRefresh_token());
        }
        return "";
    }

    /**
     * 是否已登陆
     *
     * @return
     */
    public synchronized boolean isLogin() {
        return !instance.getAccess_token().isEmpty();
    }

    /**
     * @author rong.mo
     *  @brief 方法描述  清空用户信息
     *  @param userInfo void      
     *  @throws  
     * @date 2015-12-25 下午7:58:16
     *     
     **/
    public void clearUserInfo() {
        userLoginBean = null;
        accountInfoBean = null;
        MySharedPreferences.getSharedPreferences().clearUserInfo();
    }

    /**
     *      
     *
     * @author fei.guo
     *  @brief 方法描述
     *  @param accountInfo void      
     *  @throws  
     * @date 2015-12-28 下午2:30:46
     *     
     */
    public synchronized void setAccountInfo(AccountBean accountInfo) {
        this.accountInfoBean = accountInfo;
    }

    public AccountBean getAccountInfo() {
        return accountInfoBean;
    }

    /**
     * 用户信息
     * 
     * @author nianyi.yang
     * @date create at 2016/9/14 14:45
     */
    public void refreshAccountInfo(final onRefreshAccountInfo onRefresh) {

        mManager.getAccountInfo(new NetCallBack() {
            @Override
            public void networkFinished(String operationType, BaseService service, int code, String tag) {
                AccountBean bean = ((UserService) service).getAccountBean();

                if (bean != null && bean.getUser() != null) {
                    setAccountInfo(bean);
                } else {
                    bean = null;
                }

                if (onRefresh != null)
                    onRefresh.onRefreshed(bean);
            }

            @Override
            public void onErrorResponse(String operationType, Throwable throwable, String tag) {
                if (onRefresh != null)
                    onRefresh.onError();
            }
        }, new NetCallBack.InitService() {

            @Override
            public BaseService initService() {
                return new UserService();
            }
        });
    }

    public interface onRefreshAccountInfo {
        void onRefreshed(AccountBean accountInfo);

        void onError();
    }
}
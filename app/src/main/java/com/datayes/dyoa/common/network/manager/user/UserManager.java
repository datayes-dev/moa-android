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

package com.datayes.dyoa.common.network.manager.user;

import com.datayes.dyoa.common.network.NetCallBack;
import com.datayes.dyoa.common.network.manager.base.JsonRequestManager;

/**
 * @author shenen.gao
 * @version 1.0
 * @brief 用户相关的请求管理类
 * @date 2015-12-11 下午7:50:43
 */
public class UserManager extends JsonRequestManager {

    /**
     * @author shenen.gao
     *  @brief 发送登陆请求
     *  @param netCallBack
     *  @param baseService
     *  @param username
     *  @param password void      
     *  @throws  
     * @date 2015-12-15 下午7:04:45
     *     
     */
    public void sendLogin(NetCallBack netCallBack,
                          NetCallBack.InitService baseService, String username, String password, String captcha, String tenant) {

        /*start(netCallBack,
                baseService,
                getInstance().sendLogin(
                        Config.ConfigUrlType.USER_MASTER.getUrl(),
                        username,
                        password,
                        "password",
                        tenant,
                        captcha,
                        ((captcha.length() <= 0) ? "false" : "true")
                ),
                Config.ConfigUrlType.USER_MASTER, UserLoginBean.class);*/
    }
}

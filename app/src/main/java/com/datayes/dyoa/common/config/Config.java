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

package com.datayes.dyoa.common.config;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import com.datayes.dyoa.App;


public class Config {

    private static Config confing;

    public static final Config getConfig() {

        if (confing == null)
            confing = new Config();

        return confing;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //初始化

    private Config() {

        App app = App.getInstance();

        try {
            ApplicationInfo appInfo = app.getPackageManager().getApplicationInfo(app.getPackageName(), PackageManager.GET_META_DATA);
            String environment = appInfo.metaData.getString("ENVIRONMENT");

            if (environment.equalsIgnoreCase("stg")) {
                this.type = "2";
            } else if (environment.equalsIgnoreCase("prd")) {
                this.type = "3";
            } else {
                this.type = "1";
            }

        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //基本配置变量

    private String type = "2"; // 1:QA; 2:STG, 3:PROD

    public String getUrl(int dd) {
        return qaBaseUrl;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //APP服务器URL

    public enum ConfigUrlType {
        NOMRAL(""),
        RRP("/rrp/mobile"),
        MOBILE("/mobile"),
        MOBILE_MASTER("/mobilemaster"),
        CLOUD("/cloud"),
        USER_MASTER("/usermaster"),
        WEB_VIEW_PAGE_INFO("information/news/"),
        WEB_VIEW_PAGE_RESEARCH("information/research/"),
        WEB_VIEW_PAGE_ANNOUNCEMENT("information/announcement/"),
        WEB_VIEW_PAGE_EVENT(""),
        WEB_MIAL("/webmail");

        private String url_;

        ConfigUrlType(String url) {
            url_ = url;
        }

        public String getUrl() {

            //处理测试QA的特殊情况
            if (this == MOBILE) {

                if (confing.type.equals("1")) {//QA

                    return "/rrpqa" + url_;

                } else {

                    return "/ira" + url_;
                }
            }

            return url_;
        }
    }

    private static final String qaBaseUrl = "http://gw.cp.wmcloud-qa.com";
    private static final String devBaseUrl = "https://gw.wmcloud-stg.com";
    private static final String stageBaseUrl = "https://gw.wmcloud-stg.com";
    private static final String productBaseUrl = "https://gw.wmcloud.com";

    /**
     * @author shenen.gao  @brief 获取请求的URL  @param type  @back_return String      
     *  @throws  
     * @date 2015-12-14 下午8:03:31     
     */
    public String getUrl(ConfigUrlType type) {
        return new StringBuffer()
                .append(getBaseUrl())
                .append(type.getUrl()).toString();
    }

    public String getBaseUrl() {

        if (type.equals("1"))
            return devBaseUrl;
        else if (type.equals("2"))
            return stageBaseUrl;
        else if (type.equals("3"))
            return productBaseUrl;
        return qaBaseUrl;
    }
}

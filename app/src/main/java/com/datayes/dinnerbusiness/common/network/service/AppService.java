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

package com.datayes.dinnerbusiness.common.network.service;

import com.datayes.dinnerbusiness.bean.user.AccountBean;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Yang on 2016/5/1.
 */
public interface AppService {

    String ACCEPT_JSON = "Accept:application/json";
    String ACCEPT_PROTOBUF = "Accept:application/x-protobuf";

    String CONTENT_TYPE = "Content-Type:application/json";
    String CONTENT_TYPE_RAW = "Content-Type:multipart/raw";
    String CONTENT_TYPE_FORM_DATA = "Content-Type:multipart/form-data";
    String MULTIPART_FORM_DATA = "Content-Type: multipart/form-data; boundary=----WebKitFormBoundaryJKxbzue2e43U4Bc4";

    /**
     * 登陆接口
     *
     * @param subServer
     * @param username
     * @param password
     * @param grant_type
     * @param tenant
     * @param captcha
     * @param setContext
     * @return
     */
    @Headers({ACCEPT_JSON})
    @FormUrlEncoded
    @POST("{subServer}/oauth2/token.json")
    Call<String> sendLogin(
            @Path(value = "subServer", encoded = true) String subServer,
            @Field("username") String username,
            @Field("password") String password,
            @Field("grant_type") String grant_type,
            @Field("tenant") String tenant,
            @Field("captcha") String captcha,
            @Field("setContext") String setContext
    );

    /**
     * 获取用户详细信息
     *
     * @author nianyi.yang
     * @date create at 2016/9/14 14:25
     */
    @Headers({ACCEPT_JSON})
    @GET("{subServer}/identity.json")
    Call<AccountBean> getAccountInfo(
            @Path(value = "subServer", encoded = true) String subServer
    );

    /**
     * 刷新refreshTocken请求
     *
     * @param subServer
     * @return
     */
    @Headers({ACCEPT_JSON})
    @FormUrlEncoded
    @POST("{subServer}/oauth2/token.json")
    Call<String> sendRefreshTocken(
            @Path(value = "subServer", encoded = true) String subServer,
            @Field("grant_type") String grant_type,
            @Field("refresh_token") String refresh_token
    );

    /**
     * 获取饭店详情
     *
     * @author hongfei.tao
     * @time create at 2016/9/13 13:56
     */
    @GET("/meal_ticket/restaurant/{resId}")
    Call getRestaurantDetail(
            @Path("resId") String resId
    );

    /**
     * 添加饭店
     *
     * @author hongfei.tao
     * @time create at 2016/9/13 13:56
     */
    @POST("/meal_ticket/restaurant")
    Call addRestaurant(
            @Body RequestBody body
    );


    /**
     * 获取商家当天交易
     *
     * @author hongfei.tao
     * @time create at 2016/9/13 14:07
     */
    @GET("/meal_ticket/transaction-restaurant")
    Call getRestaurantDeals();

    /**
     * 饭店列表
     *
     * @author hongfei.tao
     * @time create at 2016/9/13 14:08
     */
    @GET("{subServer}/restaurant")
    Call<String> getRestaurantList(
            @Path(value = "subServer", encoded = true) String subServer
    );

    /**
     * 执行交易
     *
     * @author hongfei.tao
     * @time create at 2016/9/13 14:03
     */
    @POST("{subServer}/transaction")
    Call<String> handleTransaction(
            @Path(value = "subServer", encoded = true) String subServer,
            @Body RequestBody body
    );


    /**
     * 获取个人当天交易
     *
     * @author hongfei.tao
     * @time create at 2016/9/13 14:04
     */
    @GET("{subServer}/transaction-user")
    Call<String> getPersonalDeals(
            @Path(value = "subServer", encoded = true) String subServer
    );

}

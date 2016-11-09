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

package com.datayes.dinnerbusiness.common.network.manager.base;

import android.text.TextUtils;

import com.datayes.dinnerbusiness.bean.base.BaseBean;
import com.datayes.dinnerbusiness.common.config.Config;
import com.datayes.dinnerbusiness.common.network.BaseService;
import com.datayes.dinnerbusiness.common.network.NetCallBack;
import com.datayes.dinnerbusiness.common.network.bean.BaseResponseBean;
import com.datayes.dinnerbusiness.common.network.error.SafeGrardException;
import com.datayes.dinnerbusiness.common.network.manager.token.NetAccessTockenManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Json数据请求管理器基类
 * Created by hongfei.tao on 2016/9/1 16:52.
 */
public class JsonRequestManager extends BaseRequestManager {

    /**
     * json请求，返回字符串(适应之前的网络请求)
     *
     * @param callBack
     * @param service
     * @param call
     * @param beanClass
     */
    protected void start(final NetCallBack callBack, final NetCallBack.InitService service, Call<String> call, final Config.ConfigUrlType type, final Class<? extends BaseBean> beanClass) {

        Callback<String> callback = new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                String resultJson = response.body();

                Throwable errorThrowable = checkResponseCode(response);

                if (errorThrowable == null) {//网络正常

                    if (!TextUtils.isEmpty(resultJson)) {

                        BaseBean bean;

                        try {

                            if (beanClass == null) {

                                bean = BaseBean.class.newInstance();

                            } else {

                                bean = beanClass.newInstance();
                            }

                            if (resultJson.startsWith("{")) {
                                JSONObject json = new JSONObject(resultJson);
                                bean.parseJsonObject(json);
                            }else if (resultJson.startsWith("["))  {
                                JSONArray jsonArray = new JSONArray(resultJson);
                                bean.parseJsonArray(jsonArray);
                            }

                            String subUrl = call.request().url().url().getPath();
                            String operateType = subUrl.replace(type.getUrl(), "");

                            //检查运维模式
                            if (bean.getCode() == -9990) {

                                if (callBack != null) {
                                    callBack.onErrorResponse(
                                            operateType,
                                            new SafeGrardException(),
                                            ""
                                    );
                                }

                            } else {

                                if (checkTockenNeedLogin(call, this, bean.getCode())) {

                                    saveCookie(response.headers());

                                    if (callBack != null) {

                                        BaseService initService = service.initService();

                                        //数据注入
                                        infuse(initService, bean);

                                        callBack.networkFinished(operateType, initService, bean.getCode(), bean.getInfo());
                                    }
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } else {

                    this.onFailure(call, errorThrowable);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (callBack != null && t != null) {

                    String subUrl = call.request().url().url().getPath();
                    String operateType = subUrl.replace(type.getUrl(), "");

                    callBack.onErrorResponse(
                            operateType,
                            t,
                            ""
                    );
                }
            }
        };

        NetAccessTockenManager.INSTANCE.enqueue(call, callback);
    }

    /**
     * 携带operateType参数回来(可能一个请求有两个operateType)
     *
     * @author hongfei.tao
     * @time create at 2016/9/2 18:11
     */
    protected <T extends BaseResponseBean> void start(final String operateType, final NetCallBack callBack, final NetCallBack.InitService service, Call<T> call) {

        Callback callback = new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {

                Throwable errorThrowable = checkResponseCode(response);

                if (errorThrowable == null) {//网络正常

                    T t = response.body();

                    if (t != null && checkTockenNeedLogin(call, this, t.getCode())) {

                        saveCookie(response.headers());

                        if (callBack != null) {
                            BaseService initService = service.initService();

                            //数据注入
                            infuse(initService, t);

                            callBack.networkFinished(operateType, initService, t.getCode(), t.getMessage());
                        }
                    }

                } else {

                    onFailure(call, errorThrowable);
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {

                if (callBack != null && t != null) {
                    callBack.onErrorResponse(
                            operateType,
                            t,
                            ""
                    );
                }
            }
        };

        NetAccessTockenManager.INSTANCE.enqueue(call, callback);
    }

    /**
     * json转换成bean后，将bean注入到initService中
     *
     * @param initService
     * @param bean
     */
    private <T> void infuse(BaseService initService, T bean) {
        Class<? extends BaseService> serviceClass = initService.getClass();
        Field[] fields = serviceClass.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            String clazzName = field.getType().getSimpleName();

            if (bean.getClass().getSimpleName().equals(clazzName)) {
                try {
                    field.set(initService, bean);
                    break;

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    protected void start(final String operateType, final NetCallBack callBack, final NetCallBack.InitService service, Call<String> call, final Config.ConfigUrlType type, final Class<? extends BaseBean> beanClass) {

        Callback<String> callback = new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                String resultJson = response.body();

                Throwable errorThrowable = checkResponseCode(response);

                if (errorThrowable == null) {//网络正常

                    if (!TextUtils.isEmpty(resultJson)) {

                        BaseBean bean;

                        try {

                            if (beanClass == null) {

                                bean = BaseBean.class.newInstance();

                            } else {

                                bean = beanClass.newInstance();
                            }

                            if (resultJson.startsWith("{")) {
                                JSONObject json = new JSONObject(resultJson);
                                bean.parseJsonObject(json);
                            }else if (resultJson.startsWith("["))  {
                                JSONArray jsonArray = new JSONArray(resultJson);
                                bean.parseJsonArray(jsonArray);
                            }


                            //检查运维模式
                            if (bean.getCode() == -9990) {

                                if (callBack != null) {
                                    callBack.onErrorResponse(
                                            operateType,
                                            new SafeGrardException(),
                                            ""
                                    );
                                }

                            } else {

                                if (checkTockenNeedLogin(call, this, bean.getCode())) {

                                    saveCookie(response.headers());

                                    if (callBack != null) {

                                        BaseService initService = service.initService();

                                        //数据注入
                                        infuse(initService, bean);

                                        callBack.networkFinished(operateType, initService, bean.getCode(), bean.getInfo());
                                    }
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } else {

                    this.onFailure(call, errorThrowable);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (callBack != null && t != null) {


                    callBack.onErrorResponse(
                            operateType,
                            t,
                            ""
                    );
                }
            }
        };

        NetAccessTockenManager.INSTANCE.enqueue(call, callback);
    }



}

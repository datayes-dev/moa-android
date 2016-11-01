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


import com.datayes.dinnerbusiness.common.config.Config;
import com.datayes.dinnerbusiness.common.network.BaseService;
import com.datayes.dinnerbusiness.common.network.NetCallBack;
import com.datayes.dinnerbusiness.common.network.manager.token.NetAccessTockenManager;
import com.google.protobuf.GeneratedMessage;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ProtoBuf数据格式请求管理器基类
 * Created by hongfei.tao on 2016/8/30 14:10.
 */
public class ProtoRequestManager extends BaseRequestManager {

    /**
     * 携带operateType参数回来(可能一个请求有两个operateType)
     *
     * @author hongfei.tao
     * @time create at 2016/9/2 10:10
     */
    protected void start(final String operateType, final NetCallBack callBack, final NetCallBack.InitService service, Call<? extends GeneratedMessage> call, final Config.ConfigUrlType type) {

        final Callback<GeneratedMessage> callback = new Callback<GeneratedMessage>() {
            @Override
            public void onResponse(Call<GeneratedMessage> call, Response<GeneratedMessage> response) {

                GeneratedMessage result = response.body();

                Method methodCode = null;
                Method methodMessage = null;
                int code = -1;
                String message = "";

                try {

                    methodCode = result.getClass().getMethod("getCode");
                    methodMessage = result.getClass().getMethod("getMessage");

                    if (methodCode != null)
                        code = (int) methodCode.invoke(result);

                    if (methodMessage != null)
                        message = (String) methodMessage.invoke(result);

                } catch (NoSuchMethodException e) {

                    e.printStackTrace();
                    return;

                } catch (InvocationTargetException e) {

                    e.printStackTrace();
                    return;

                } catch (IllegalAccessException e) {

                    e.printStackTrace();
                    return;
                }

                if (result != null && checkTockenNeedLogin(call, this, code)) {

                    saveCookie(response.headers());

                    BaseService initService = service.initService();

                    //这里做业务层数据的注入
                    if (initService != null) {

                        /*if (result instanceof ResultProto.Result) {

                            infuseReault(initService, (ResultProto.Result) result);

                        } else {

                            infusePb(initService, result);
                        }*/
                    }
                    if (callBack != null) {

                        callBack.networkFinished(
                                operateType,
                                initService,
                                code,
                                message
                        );
                    }
                }
            }

            @Override
            public void onFailure(Call<GeneratedMessage> call, Throwable t) {

                if (t.getMessage().equals("needLogin")) {

                    checkTockenNeedLogin(call, this, -403);

                } else {

                    String subUrl = call.request().url().url().getPath();
                    String operateType = subUrl.replace(type.getUrl(), "");

                    if (callBack != null) {
                        callBack.onErrorResponse(operateType, t, "");
                    }
                }
            }
        };

        NetAccessTockenManager.INSTANCE.enqueue(call, callback);
    }

    private void infusePb(BaseService b, GeneratedMessage message) {

        if (b != null && message != null) {

            @SuppressWarnings("unchecked")
            Class<? extends BaseService> userCla = b.getClass();
            Class messageClass = message.getClass();
            String messageClassName = messageClass.getSimpleName();

            try {

                Field[] fs = userCla.getDeclaredFields();

                for (Field f : fs) {

                    f.setAccessible(true);

                    Class fClass = f.getType().getSuperclass();

                    if (fClass != null && fClass.isAssignableFrom(GeneratedMessage.class)) {

                        String simpleName = f.getType().getSimpleName();

                        if (simpleName.equals(messageClassName)) {

                            f.set(b, message);
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //反射result数据到service
    /*private void infuseReault(BaseService b, ResultProto.Result r) {

        if (b != null && r != null) {

            @SuppressWarnings("unchecked")
            Class<? extends BaseService> userCla = b.getClass();
            Class rCla = r.getClass();

            try {

                Field[] fs = userCla.getDeclaredFields();

                for (Field f : fs) {

                    f.setAccessible(true);

                    Class fClass = f.getType().getSuperclass();

                    if (r instanceof ResultProto.Result && fClass != null && fClass.getSimpleName().equals("GeneratedMessage")) {

                        String simpleName = f.getType().getSimpleName();
                        String name = simpleName + "_";
                        name = name.substring(0, 1).toLowerCase() + name.substring(1, name.length());

                        Field field = rCla.getDeclaredField(name);
                        Method method = rCla.getDeclaredMethod("has" + simpleName);

                        if (field != null && method != null) {

                            field.setAccessible(true);

                            boolean hasData = (boolean) method.invoke(r);

                            if (hasData) {

                                GeneratedMessage generatedMessage = (GeneratedMessage) field.get(r);
                                f.set(b, generatedMessage);

                                break;
                            }
                        }

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }*/
}

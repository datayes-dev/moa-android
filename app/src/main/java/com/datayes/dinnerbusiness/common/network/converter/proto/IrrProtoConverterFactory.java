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

package com.datayes.dinnerbusiness.common.network.converter.proto;

import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.MessageLite;
import com.google.protobuf.Parser;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * 通联数据，pb转换器
 * Created by shenen.gao on 2016/9/7.
 */
public class IrrProtoConverterFactory extends Converter.Factory {

    public static IrrProtoConverterFactory create() {
        return new IrrProtoConverterFactory(null);
    }

    /**
     * Create an instance which uses {@code registry} when deserializing.
     */
    public static IrrProtoConverterFactory createWithRegistry(ExtensionRegistryLite registry) {
        return new IrrProtoConverterFactory(registry);
    }

    private final ExtensionRegistryLite registry;

    private IrrProtoConverterFactory(ExtensionRegistryLite registry) {
        this.registry = registry;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        if (!(type instanceof Class<?>)) {
            return null;
        }
        Class<?> c = (Class<?>) type;
        if (!MessageLite.class.isAssignableFrom(c)) {
            return null;
        }

        Parser<MessageLite> parser;
        try {
            Field field = c.getDeclaredField("PARSER");
            //noinspection unchecked
            parser = (Parser<MessageLite>) field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalArgumentException(
                    "Found a protobuf message but " + c.getName() + " had no PARSER field.");
        }
        return new IrrProtoResponseBodyConverter<>(parser, registry);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        if (!(type instanceof Class<?>)) {
            return null;
        }
        if (!MessageLite.class.isAssignableFrom((Class<?>) type)) {
            return null;
        }
        return new IrrProtoRequestBodyConverter<>();
    }
}

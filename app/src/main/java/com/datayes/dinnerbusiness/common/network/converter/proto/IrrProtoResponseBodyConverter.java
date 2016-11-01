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
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;
import com.google.protobuf.Parser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by shenen.gao on 2016/9/7.
 */
public class IrrProtoResponseBodyConverter<T extends MessageLite>
        implements Converter<ResponseBody, T> {
    private final Parser<T> parser;
    private final ExtensionRegistryLite registry;

    IrrProtoResponseBodyConverter(Parser<T> parser, ExtensionRegistryLite registry) {
        this.parser = parser;
        this.registry = registry;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {

        byte[] bytes = value.bytes();

        try {

            return parser.parseFrom(bytes);

        } catch (InvalidProtocolBufferException e) {

            //处理accessToken过期的问题
            String errStr = new String(bytes, "utf-8");

            try {

                JSONObject json = new JSONObject(errStr);
                int code = json.optInt("code", -1);

                if (code == -403) {

                    throw new IOException("needLogin");
                }

            } catch (JSONException e1) {

                throw new RuntimeException(e);
            }

            throw new RuntimeException(e); // Despite extending IOException, this is data mismatch.

        } finally {

            value.close();
        }
    }
}

/**
 * 通联数据机密
 * --------------------------------------------------------------------
 * 通联数据股份公司版权所有 © 2013-2016
 *
 * 注意：本文所载所有信息均属于通联数据股份公司资产。本文所包含的知识和技术概念均属于
 * 通联数据产权，并可能由中国、美国和其他国家专利或申请中的专利所覆盖，并受商业秘密或
 * 版权法保护。
 * 除非事先获得通联数据股份公司书面许可，严禁传播文中信息或复制本材料。
 *
 * DataYes CONFIDENTIAL
 * --------------------------------------------------------------------
 * Copyright © 2013-2016 DataYes, All Rights Reserved.
 *
 * NOTICE: All information contained herein is the property of DataYes
 * Incorporated. The intellectual and technical concepts contained herein are
 * proprietary to DataYes Incorporated, and may be covered by China, U.S. and
 * Other Countries Patents, patents in process, and are protected by trade
 * secret or copyright law.
 * Dissemination of this information or reproduction of this material is
 * strictly forbidden unless prior written permission is obtained from DataYes.
 */
package com.datayes.dyoa.common.networkstatus;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.datayes.dyoa.App;


/**
 * 网络状态类
 */
public class NetworkState {

    public static final int TYPE_UNKNOWN = 0;
    public static final int TYPE_WIFI = 1;
    public static final int TYPE_2G = 2;
    public static final int TYPE_3G = 3;
    public static final int TYPE_4G = 4;
    /**
     * 是否有网
     */
    private boolean mConnected;

    /**
     * 网络类型
     */
    private int mType;

    public NetworkState(NetworkInfo info) {
        parseNetworkInfo(info);
    }

    public boolean isConnected() {
        return mConnected;
    }

    public int getType() {
        return mType;
    }

    public String getTypeName() {
        switch (mType) {
            case TYPE_UNKNOWN:
                return "unknown";
            case TYPE_WIFI:
                return "wifi";
            case TYPE_2G:
                return "2G";
            case TYPE_3G:
                return "3G";
            case TYPE_4G:
                return "4G";
            default:
                return "unknown";
        }
    }

    private void parseNetworkInfo(NetworkInfo info) {
        if (info != null && info.isAvailable() && info.isConnected()) {
            mConnected = true;
            int type = info.getType();
            if (type == ConnectivityManager.TYPE_WIFI) {
                mType = TYPE_WIFI;
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                mType = parseType();
            } else {
                mType = TYPE_UNKNOWN;
            }
        } else {
            mConnected = false;
            mType = TYPE_UNKNOWN;
        }
    }

    private int parseType() {
        TelephonyManager tm = (TelephonyManager) App.getInstance().
                getSystemService(Context.TELEPHONY_SERVICE);
        int networkType = tm.getNetworkType();
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case 16://TelephonyManager.NETWORK_TYPE_GSM
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return TYPE_2G;
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
            case 17://小米4移动的3G网络
                return TYPE_3G;
            case TelephonyManager.NETWORK_TYPE_LTE:
                return TYPE_4G;
            default:
                return TYPE_UNKNOWN;
        }
    }

    @Override
    public String toString() {
        return "NetworkState: isConnected = " + mConnected + ", type = " + getTypeName();
    }

    public static NetworkState create() {
        ConnectivityManager cm = (ConnectivityManager) App.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return new NetworkState(networkInfo);
    }
}

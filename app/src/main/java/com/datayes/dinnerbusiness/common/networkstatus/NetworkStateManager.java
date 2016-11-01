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
package com.datayes.dinnerbusiness.common.networkstatus;

import com.datayes.baseapp.tools.DYLog;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 */
public class NetworkStateManager {

    public interface OnNetworkStateChangedListener {
        void onNetworkStateChanged(NetworkState networkState);
    }

    private static NetworkStateManager ourInstance = new NetworkStateManager();

    private NetworkState mNetworkState;

    private CopyOnWriteArrayList<OnNetworkStateChangedListener> mListeners;

    public static NetworkStateManager getInstance() {
        return ourInstance;
    }

    private NetworkStateManager() {
        mNetworkState = NetworkState.create();
        mListeners = new CopyOnWriteArrayList<OnNetworkStateChangedListener>();
    }

    public void updateNetworkState(NetworkState newState) {
        if (newState.isConnected() == mNetworkState.isConnected() &&
                newState.getType() == mNetworkState.getType()) {
            DYLog.w("updateNetworkState : " + newState.toString() + ", not changed");
            return;
        }
        DYLog.w("updateNetworkState : " + newState.toString() + ", has changed");
        mNetworkState = newState;
        for (OnNetworkStateChangedListener listener : mListeners) {
            listener.onNetworkStateChanged(newState);
        }
    }

    public void addNetworkStateChangedListener(OnNetworkStateChangedListener listener) {
        if (listener == null) return;
        if (mListeners.contains(listener)) return;
        mListeners.add(listener);
        listener.onNetworkStateChanged(mNetworkState);
    }

    public void removeNetworkStateChangedListener(OnNetworkStateChangedListener listener) {
        mListeners.remove(listener);
    }

}

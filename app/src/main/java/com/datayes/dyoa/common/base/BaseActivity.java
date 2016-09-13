package com.datayes.dyoa.common.base;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.datayes.baseapp.base.BaseFragmentActivity;
import com.datayes.dyoa.common.network.BaseService;
import com.datayes.dyoa.common.network.NetCallBack;
import com.datayes.dyoa.common.networkstatus.NetworkState;
import com.datayes.dyoa.common.networkstatus.NetworkStateManager;

import butterknife.ButterKnife;

/**
 * Created by nianyi.yang on 2016/9/12.
 */
public abstract class BaseActivity extends BaseFragmentActivity implements NetCallBack, NetCallBack.InitService {

    private boolean isResuming = false;

    /**
     * 是否需要重置
     *
     * @param mNeedReset
     */
    public void setNeedReset(boolean mNeedReset) {

    }

    /**
     * 当前Activity处在用户可交互状态
     *
     * @return
     */
    public boolean isResuming() {
        return isResuming;
    }


    public View getRootView() {
        return ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
    }

    public ViewGroup getRootFrameLayout() {
        return ((ViewGroup) findViewById(android.R.id.content));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NetworkStateManager.getInstance().addNetworkStateChangedListener(
                mNetworkStateChangedListener);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
    }

    /**
     * 获取当前Activity的布局文件Id
     *
     * @return
     */
    protected abstract int getLayoutId();

    @Override
    protected void onResume() {

        super.onResume();
        isResuming = true;
    }

    /**
     * App退出进入后台要做的操作
     */
    @Override
    protected void onApplicationToBackground() {

    }

    /**
     * App返回前台的操作
     */
    @Override
    protected void onApplicationBackFromBackGround() {

    }

    @Override
    protected void onPause() {

        super.onPause();
        isResuming = false;

    }

    protected void onDestroy() {

        NetworkStateManager.getInstance().removeNetworkStateChangedListener(
                mNetworkStateChangedListener);
        super.onDestroy();
    }

    public abstract void onNetworkStateChanged(NetworkState networkState);

    private NetworkStateManager.OnNetworkStateChangedListener mNetworkStateChangedListener = new NetworkStateManager.OnNetworkStateChangedListener() {
        @Override
        public void onNetworkStateChanged(NetworkState networkState) {

            BaseActivity.this.onNetworkStateChanged(networkState);
        }
    };

    @Override
    public void networkFinished(String operationType, BaseService service, int code, String tag) {

    }

    @Override
    public BaseService initService() {
        return null;
    }
}

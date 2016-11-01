package com.datayes.dyoa.common.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.datayes.baseapp.base.BaseFragmentActivity;
import com.datayes.baseapp.tools.DYToast;
import com.datayes.dinnercustom.R;
import com.datayes.dyoa.common.network.BaseService;
import com.datayes.dyoa.common.network.NetCallBack;
import com.datayes.dyoa.common.network.error.SafeGrardException;
import com.datayes.dyoa.common.networkstatus.NetworkState;
import com.datayes.dyoa.common.networkstatus.NetworkStateManager;
import com.datayes.dyoa.common.view.CLoading;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import butterknife.ButterKnife;

/**
 * Created by nianyi.yang on 2016/9/12.
 */
public abstract class BaseActivity extends BaseFragmentActivity implements NetCallBack, NetCallBack.InitService {

    protected CLoading loading;

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
        //setStateBar();
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
    public void onErrorResponse(String operationType, Throwable throwable, String tag) {
        hideLoading();
        networkResponseError(this, throwable);
    }

    /**
     * @author shenen.gao
     *  @brief 网络接口错误统一返回接口
     *  @param context
     *  @param volleyError void      
     *  @throws  
     * @date 2016-3-29 下午9:15:15
     *     
     */
    public static void networkResponseError(Context context, Throwable throwable) {

        if (context != null && throwable != null) {

            if (throwable instanceof UnknownHostException) {

                DYToast.makeText(context, R.string.NoConnectionError, Toast.LENGTH_SHORT).show();

            } else if (throwable instanceof SocketTimeoutException) {

                DYToast.makeText(context, R.string.TimeoutError, Toast.LENGTH_SHORT).show();

            } else if (throwable instanceof SafeGrardException) {

            } else {

                DYToast.makeText(context, R.string.ServerError, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public BaseService initService() {
        return null;
    }

    /**
     * 加载进度框
     * 重载
     */
    public void showLoading() {
        if (!BaseActivity.this.isFinishing()) {
            if (loading == null) {
                loading = new CLoading.Builder()
                        .setContext(this)
                        .setBackground(R.drawable.loading_bg)
                        .build();
            }
            loading.show();
        }
    }

    /**
     * 加载进度框
     *
     * @param msg
     */
    public void showLoading(String msg) {
        if (!BaseActivity.this.isFinishing()) {
            if (loading == null) {
                loading = new CLoading.Builder()
                        .setContext(this)
                        .setBackground(R.drawable.loading_bg)
                        .setMessage(msg)
                        .build();
            }
            loading.show();
        }
    }

    public void hideLoading() {
        if (loading != null) {
            loading.hide();
        }
    }

    private void setStateBar() {
        //透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}

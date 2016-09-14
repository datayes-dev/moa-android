package com.datayes.dyoa.module.code.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.datayes.baseapp.tools.DYToast;
import com.datayes.dyoa.R;
import com.datayes.dyoa.common.base.BaseActivity;
import com.datayes.dyoa.common.network.BaseService;
import com.datayes.dyoa.common.network.bean.RestaurantListBean;
import com.datayes.dyoa.common.networkstatus.NetworkState;
import com.datayes.dyoa.common.view.CTitle;
import com.datayes.dyoa.module.swipecard.activity.SwipeCardActivity;
import com.datayes.dyoa.module.swipecard.activity.TradeHistoryActivity;
import com.datayes.dyoa.module.swipecard.activity.manager.SwipeManager;
import com.datayes.dyoa.module.swipecard.activity.manager.SwipeService;
import com.datayes.dyoa.module.user.RestaurantManager;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.List;

import butterknife.BindView;

/**
 * 二维码扫描界面
 * Created by hongfei.tao on 2016/9/13 10:44.
 */
public class ScanCodeActivity extends BaseActivity implements CodeUtils.AnalyzeCallback {

    @BindView(R.id.ct_title)
    CTitle mTitle;

    private SwipeManager mSwipeManager;
    private SwipeService mSwipeService;

    private CaptureFragment mCaptureFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCaptureFragment = new CaptureFragment();
        CodeUtils.setFragmentArgs(mCaptureFragment, R.layout.layout_camera);
        mCaptureFragment.setAnalyzeCallback(this);
        mSwipeManager = new SwipeManager();
        mSwipeManager.getRestaurantList(this, this);

        mTitle.setRightBtnText("交易记录");
        mTitle.setrightFenGeClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScanCodeActivity.this, TradeHistoryActivity.class);
                startActivity(intent);
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fl_code_container, mCaptureFragment).commit();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scan_code;
    }

    @Override
    public void onAnalyzeSuccess(Bitmap mBitmap, String result) {

        boolean isHeZuo = false;
        List<RestaurantListBean.RestaurantBean> beanList = RestaurantManager.getInstance().getRestaurantListBean().getRestaurantBeanList();
        for (RestaurantListBean.RestaurantBean bean : beanList) {

            if (bean.getId().equals(result)) {
                isHeZuo = true;
                break;
            }
        }
        if (isHeZuo){
            Intent intent = new Intent(this, SwipeCardActivity.class);
            intent.putExtra(SwipeCardActivity.RESTAURANT_ID_KEY, result);
            startActivity(intent);
        }else {
            DYToast.makeText(this, "商家未存在公司合作列表中", Toast.LENGTH_LONG).show();
            finish();
        }



    }

    @Override
    public void onAnalyzeFailed() {

//        Intent intent = new Intent(this, SwipeCardActivity.class);
//        intent.putExtra(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
//        startActivity(intent);
        finish();
    }

    @Override
    public void onNetworkStateChanged(NetworkState networkState) {

    }

    @Override
    public void onErrorResponse(String operationType, Throwable throwable, String tag) {

    }

    @Override
    public void networkFinished(String operationType, BaseService service, int code, String tag) {

        if (operationType.equals("/restaurant") && mSwipeService.getRestaurantListBean() != null) {
            RestaurantManager.getInstance().setRestaurantListBean(mSwipeService.getRestaurantListBean());
        }
    }

    @Override
    public BaseService initService() {
        if (mSwipeService == null)
            mSwipeService = new SwipeService();
        return mSwipeService;
    }
}

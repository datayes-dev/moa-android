package com.datayes.dinnercustom;

import android.app.Activity;
import android.content.Intent;

import com.datayes.baseapp.base.BaseApp;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

/**
 * Created by nianyi.yang on 2016/9/12.
 */
public class App extends BaseApp {

    private static App instance_;

    public static App getInstance() {
        return instance_;
    }

    @Override
    public void onCreate() {
        instance_ = this;

        super.onCreate();

        ZXingLibrary.initDisplayOpinion(this);
    }

    /**
     * 打开登陆界面
     */
    public void gotoLoginActivity() {

        Activity activity = this.getLastActivity();

        if (activity != null) {

            activity.startActivity(new Intent(activity, LoginActivity.class));
        }
    }

    @Override
    protected void initSqlite() {

    }

    @Override
    protected void onActivityAdded(Activity activity) {

    }

    @Override
    protected void onActivityRemoved(Activity activity) {

    }
}

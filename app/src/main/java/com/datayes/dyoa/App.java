package com.datayes.dyoa;

import android.app.Activity;

import com.datayes.baseapp.base.BaseApp;

/**
 * Created by nianyi.yang on 2016/9/12.
 */
public class App extends BaseApp{

    private static App instance_;

    public static App getInstance() {
        return instance_;
    }

    @Override
    public void onCreate() {
        instance_ = this;

        super.onCreate();
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

package com.datayes.dyoa.bean.user;

import com.datayes.baseapp.tools.DYLog;
import com.datayes.dyoa.bean.base.BaseBean;
import com.datayes.dyoa.module.user.CurrentUser;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by nianyi.yang on 2016/9/12.
 */
public class UserLoginBean extends BaseBean {

    private String openid;
    private String token_type;
    private Long expires_in;
    private String access_token;
    private String refresh_token;
    private long currentTime;

    public Long getExpires_in() {
        return expires_in;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    @Override
    protected void parseJson(JSONObject json) {
        try {
            openid = json.getString("openid");
            token_type = json.getString("token_type");
            expires_in = json.getLong("expires_in");
            access_token = json.getString("access_token");
            refresh_token = json.getString("refresh_token");

            if (openid != null && !openid.equals("")) {

                setCurrentTime(expires_in * 1000 + System.currentTimeMillis() - 500);
                CurrentUser.getInstance().saveUserInfo(this);
            }

        } catch (NullPointerException e) {
            DYLog.e(e.toString());
        } catch (JSONException e) {
            DYLog.e(e.toString());
        }
    }
}

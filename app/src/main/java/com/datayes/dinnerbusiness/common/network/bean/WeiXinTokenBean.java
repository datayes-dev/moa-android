package com.datayes.dinnerbusiness.common.network.bean;

import com.datayes.dinnerbusiness.bean.base.BaseBean;
import org.json.JSONObject;


/**
 * Created by datayes on 16/11/15.
 */

public class WeiXinTokenBean extends BaseBean {



    public String getAcceessToken() {
        return acceessToken;
    }

    public int getExpires() {
        return expires;
    }

    public int getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }



    private String acceessToken;


    private int expires;


    private int errCode;


    private String errMsg;



    @Override
    protected void parseJson(JSONObject json) {
        if (json == null) return;

        try {
            if (json.has("access_token")){

                acceessToken = json.getString("access_token");
                expires = json.getInt("expires_in");

            }else {

                errCode = json.getInt("errcode");
                errMsg = json.getString("errmsg");
            }



        } catch (Exception ex) {

        }

    }

}

package com.datayes.dinnerbusiness.common.network.bean;

import com.datayes.dinnerbusiness.bean.base.BaseBean;

import org.json.JSONObject;

/**
 * Created by datayes on 16/11/15.
 */

public class WeiXinConsumeBean extends BaseBean {


    private int errCode = -1;

    private String errMsg;

    private String cardId;

    private String openId;


    public int getErrCode() {
        return errCode;
    }

    public String getErrMsg() {

        return errMsg;
    }

    public String getCardId() {
        return cardId;
    }

    public String getOpenId() {
        return openId;
    }


    @Override
    protected void parseJson(JSONObject json) {

        if (json == null) return;

        try {

            errCode = json.getInt("errcode");
            errMsg = json.getString("errmsg");
            JSONObject object = json.getJSONObject("card_id");
            cardId = object.getString("card_id");
            openId = json.getString("openid");


        } catch (Exception ex) {

        }

    }


}

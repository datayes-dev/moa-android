package com.datayes.dyoa.bean;

import com.datayes.baseapp.utils.StringUtil;
import com.datayes.dyoa.common.networkstatus.NetworkCode;

import org.json.JSONObject;

/**
 * Created by nianyi.yang on 2016/9/12.
 */
public class BaseBean {

    private static final int requestCode = 0;

    /**
     * 错误信息
     */
    protected String info;

    /**
     * 正常返回
     */
    protected String data;

    protected int code = -1;

    public JSONObject jsonObj;

    /**
     * @author rong.mo
     *  @brief 方法描述  网络数据json解析
     *  @param json void      
     *  @throws  
     * @date 2015-12-25 下午2:24:05
     *     
     */
    public void parseJsonObject(JSONObject json) {

        jsonObj = json;

        code = json.optInt(NetworkCode.code.getName(), -2);
        info = json.optString(NetworkCode.message.getName(), "-2");
        data = json.optString("data");

        if (code == -2 && info.equals("-2")) {
            code = StringUtil.sToI(json.optString(NetworkCode.errcode.getName(), "-2"));
            info = json.optString(NetworkCode.errmsg.getName(), "-2");
        }
        if (code == -2 && info.equals("-2")) {
            parseJson(json);
            code = 1;
        } else if (code >= requestCode)
            parseJson(json);
    }

    protected void parseJson(JSONObject json) {
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String v) {
        info = v;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int v) {
        code = v;
    }

    public String getData() {
        return data;
    }
}

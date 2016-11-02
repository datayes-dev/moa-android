package com.datayes.dinnerbusiness.common.network.bean;

import com.datayes.dinnerbusiness.bean.base.BaseBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by datayes on 16/11/2.
 */

public class TradeHistoryListBean extends BaseBean {

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    private String count;
    public List<TransactionListBean.TransactionBean> getHistoryBeanList() {
        return mHistoryBeanList;
    }

    public void setHistoryBeanList(List<TransactionListBean.TransactionBean> historyBeanList) {
        mHistoryBeanList = historyBeanList;
    }

    private List<TransactionListBean.TransactionBean> mHistoryBeanList;

    @Override
    protected void parseJson(JSONObject json) {
        if (json == null)return;
        try {

            String count = json.getString("count");
            setCount(count);

            JSONArray jsonArray = json.getJSONArray("data");

            if (jsonArray != null && jsonArray.length() > 0) {
                mHistoryBeanList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    TransactionListBean.TransactionBean bean = new TransactionListBean.TransactionBean();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    bean.setUser(jsonObject.getString("user"));
                    bean.setTime_stamp(jsonObject.getString("time_stamp"));
                    bean.setId(jsonObject.getString("id"));
                    mHistoryBeanList.add(i, bean);
                }
            }
        }catch (Exception ex){

        }

    }




//    public class TradeHistoryBean {
//        public String getUser() {
//
//            return user;
//        }
//
//        public void setUser(String user) {
//            this.user = user;
//        }
//
//        public String getTime_stamp() {
//            return time_stamp;
//        }
//
//        public void setTime_stamp(String time_stamp) {
//            this.time_stamp = time_stamp;
//        }
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//        private String user;
//        private String time_stamp;
//        private String id;
//
//    }

}

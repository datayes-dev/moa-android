package com.datayes.dyoa.common.network.bean;

import com.datayes.dyoa.bean.BaseBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by datayes on 16/9/14.
 */
public class TransactionListBean extends BaseBean {

    private List<TransactionBean> mTransactionBeanList;

    public List<TransactionBean> getTransactionBeanList() {
        return mTransactionBeanList;
    }

    public void setTransactionBeanList(List<TransactionBean> transactionBeanList) {
        mTransactionBeanList = transactionBeanList;
    }

    public static class TransactionBean {

        /**
         * restaurant : d598f5d4-7730-11e6-bd14-021a12af69c9RES_
         * price : 17
         * memo :
         * user : yong.gong@datayes.com
         * restaurant_name : 麦当劳
         * time_stamp : 2016-09-10 08:31:40
         * id : ff6af9e8-7730-11e6-bd14-021a12af69c9TRS_
         */

        private String restaurant;
        private double price;
        private String memo;
        private String user;
        private String restaurant_name;
        private String time_stamp;
        private String id;

        public String getRestaurant() {
            return restaurant;
        }

        public void setRestaurant(String restaurant) {
            this.restaurant = restaurant;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getRestaurant_name() {
            return restaurant_name;
        }

        public void setRestaurant_name(String restaurant_name) {
            this.restaurant_name = restaurant_name;
        }

        public String getTime_stamp() {
            return time_stamp;
        }

        public void setTime_stamp(String time_stamp) {
            this.time_stamp = time_stamp;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    @Override
    public void parseJsonArray(JSONArray jsonArray) throws JSONException {
        if (jsonArray != null && jsonArray.length() > 0) {
            mTransactionBeanList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                TransactionBean bean = new TransactionBean();
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                bean.setRestaurant(jsonObject.getString("restaurant"));
                bean.setPrice(jsonObject.getDouble("price"));
                bean.setMemo(jsonObject.getString("memo"));
                bean.setUser(jsonObject.getString("user"));
                bean.setRestaurant_name(jsonObject.getString("restaurant_name"));
                bean.setTime_stamp(jsonObject.getString("time_stamp"));
                bean.setId(jsonObject.getString("id"));
                mTransactionBeanList.add(i, bean);
            }
        }
    }
}


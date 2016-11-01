package com.datayes.dinnerbusiness.common.network.bean;

import com.datayes.dinnerbusiness.bean.base.BaseBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongfei.tao on 2016/9/13 14:19.
 */
public class RestaurantListBean extends BaseBean {

    private List<RestaurantBean> mRestaurantBeanList;

    public List<RestaurantBean> getRestaurantBeanList() {
        return mRestaurantBeanList;
    }

    public void setRestaurantBeanList(List<RestaurantBean> restaurantBeanList) {
        mRestaurantBeanList = restaurantBeanList;
    }

    /**
     * status : 0
     * name : COSTA
     * admin : boss4@datayes.com
     * memo :
     * phone : 1234567890
     * address : 陆家嘴西路99号万向大厦4楼
     * id : c1e1f2bc-7892-11e6-bd14-021a12af69c9RES_
     */

    public static class RestaurantBean {

        private String status;
        private String name;
        private String admin;
        private String memo;
        private String phone;
        private String address;
        private String id;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAdmin() {
            return admin;
        }

        public void setAdmin(String admin) {
            this.admin = admin;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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
            mRestaurantBeanList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                RestaurantBean bean = new RestaurantBean();
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                bean.setStatus(jsonObject.getString("status"));
                bean.setName(jsonObject.getString("name"));
                bean.setAdmin(jsonObject.getString("admin"));
                bean.setMemo(jsonObject.getString("memo"));
                bean.setPhone(jsonObject.getString("phone"));
                bean.setAddress(jsonObject.getString("address"));
                bean.setId(jsonObject.getString("id"));
                mRestaurantBeanList.add(i, bean);
            }
        }
    }
}

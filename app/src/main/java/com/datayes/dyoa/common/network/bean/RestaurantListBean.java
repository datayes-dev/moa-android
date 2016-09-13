package com.datayes.dyoa.common.network.bean;

import com.datayes.dyoa.bean.BaseBean;

/**
 * Created by hongfei.tao on 2016/9/13 14:19.
 */
public class RestaurantListBean extends BaseResponseBean {

    /**
     * status : 0
     * name : COSTA
     * admin : boss4@datayes.com
     * memo :
     * phone : 1234567890
     * address : 陆家嘴西路99号万向大厦4楼
     * id : c1e1f2bc-7892-11e6-bd14-021a12af69c9RES_
     */

    private int status;
    private String name;
    private String admin;
    private String memo;
    private String phone;
    private String address;
    private String id;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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

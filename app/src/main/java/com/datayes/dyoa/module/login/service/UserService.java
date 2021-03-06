package com.datayes.dyoa.module.login.service;

import com.datayes.dyoa.bean.BaseBean;
import com.datayes.dyoa.bean.UserLoginBean;
import com.datayes.dyoa.common.network.BaseService;

/**
 * Created by nianyi.yang on 2016/9/13.
 */
public class UserService extends BaseService {

    //登录数据
    UserLoginBean loginBean_;

    //重置密码获取重置TOKEN的数据
    BaseBean resetPwdTokenBean_;

    public UserLoginBean getLoginBean() {
        return loginBean_;
    }

    public BaseBean getResetPwdTokenBean() {
        return resetPwdTokenBean_;
    }
}

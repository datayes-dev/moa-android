package com.datayes.dinnerbusiness.module.login.service;

import com.datayes.dinnerbusiness.bean.base.BaseBean;
import com.datayes.dinnerbusiness.bean.user.AccountBean;
import com.datayes.dinnerbusiness.bean.user.UserLoginBean;
import com.datayes.dinnerbusiness.common.network.BaseService;

/**
 * Created by nianyi.yang on 2016/9/13.
 */
public class UserService extends BaseService {

    //登录数据
    UserLoginBean loginBean_;

    //用户信息
    AccountBean accountBean_;

    //重置密码获取重置TOKEN的数据
    BaseBean resetPwdTokenBean_;

    public UserLoginBean getLoginBean() {
        return loginBean_;
    }

    public AccountBean getAccountBean() {
        return accountBean_;
    }

    public BaseBean getResetPwdTokenBean() {
        return resetPwdTokenBean_;
    }
}

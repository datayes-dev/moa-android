package com.datayes.dyoa.module.login;

/**
 * Created by nianyi.yang on 2016/9/13.
 */
public class Constant {
    /**
     * 登录成功
     */
    public static final String SUCCESS = "-2";
    /**
     * 登录失败
     */
    public static final String FAIL = "FAIL";
    /**
     * 参数错误
     */
    public static final String INVALID = "INVALID";
    /**
     * 用户被锁定
     */
    public static final String LOCKED = "LOCKED";
    /**
     * 用户不存在
     */
    public static final String NOTEXIST = "NOTEXIST";
    /**
     * 用户不存在
     */
    public static final String NOTEXIST2 = "notExisting account/refresh token";

    /**
     * 需要输入验证
     */
    public static final String CODE_REQUIRED = "CODE_REQUIRED";
    /**
     * 验证码失效
     */
    public static final String INVALID_CODE = "INVALID_CODE";
    /**
     * 验证码错误
     */
    public static final String ERROR_CODE = "ERROR_CODE";
    /**
     * 验证码错误  invalid credential
     */
    public static final String INVALID_CREDENTIAL = "invalid credential";
    /**
     * 服务端错误
     */
    public static final String BAD_REQUEST = "bad request";
}

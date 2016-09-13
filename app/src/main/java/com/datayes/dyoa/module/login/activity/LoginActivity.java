package com.datayes.dyoa.module.login.activity;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.datayes.baseapp.tools.DYToast;
import com.datayes.dyoa.R;
import com.datayes.dyoa.bean.UserLoginBean;
import com.datayes.dyoa.common.base.BaseActivity;
import com.datayes.dyoa.common.config.RequestInfo;
import com.datayes.dyoa.common.network.BaseService;
import com.datayes.dyoa.common.network.manager.user.UserManager;
import com.datayes.dyoa.common.networkstatus.NetworkState;
import com.datayes.dyoa.common.view.CTitle;
import com.datayes.dyoa.module.login.Constant;
import com.datayes.dyoa.module.login.service.UserService;

import butterknife.BindView;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.ct_title)
    CTitle cTitle;
    @BindView(R.id.userTxt)
    EditText mUser;
    @BindView(R.id.pwdText)
    EditText mPwd;
    @BindView(R.id.codeTxt)
    EditText mCode;
    @BindView(R.id.loginBtn)
    Button btnLogin;
    @BindView(R.id.forgetPwdTxt)
    TextView btnForget;
    @BindView(R.id.v_bottom_line)
    View mVTextCodeBottomLine;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void onNetworkStateChanged(NetworkState networkState) {

    }

    private UserManager request_;
    private UserService service_;

    private boolean mIsOnLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.init();
        this.initEvent();
    }

    @Override
    public BaseService initService() {
        return service_;
    }

    private void init() {
        request_ = new UserManager();
        mVTextCodeBottomLine = findViewById(R.id.v_bottom_line);
    }

    private void initEvent() {

        btnLogin.setOnClickListener(this);
        btnForget.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.forgetPwdTxt:
                // 打开设置密码界面
                break;

            case R.id.loginBtn:
                // 登陆操作
                if (request_ != null && mUser != null && mPwd != null && mCode != null && !mIsOnLogin) {

                    mIsOnLogin = true;

                    String username = mUser.getText().toString();

                    int tagIndex = username.indexOf("@");
                    String tenant = null;

                    int userNameLen = username.length();

                    if (tagIndex >= 0) {

                        if ((tagIndex + 1) < userNameLen) {

                            tenant = username.substring(tagIndex + 1, userNameLen);
                            username = username.substring(0, tagIndex);
                        }
                    }

                    request_.sendLogin(this,
                            this,
                            username,
                            mUser.getText().toString(),
                            mCode.getVisibility() == View.VISIBLE ? mCode.getText().toString() : "",
                            tenant);
                }
                break;

            default:
                break;
        }
    }

    // 显示提示密码错误过得的提示
    private void showPasswordToMatchErrorDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(this.getResources().getString(R.string.user_login_pwd_tomatch_error));
        builder.setTitle("提示");
        builder.create().show();
    }

    private void onLoginCompleted() {

        finish();


        DYToast.makeText(this, R.string.user_send_login_response_0, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void networkFinished(String operationType,
                                BaseService response, int code, String tag) {

        mIsOnLogin = false;

        if (service_ != null) {
            // 登录
            if (RequestInfo.LOGIN_ACCOUNT.getOperationType().equals(operationType)) {

                UserLoginBean bean = service_.getLoginBean();
                String resultInfo = bean.getInfo();

                if (!resultInfo.equals(Constant.SUCCESS)) {
                    // 刷新验证码
                    return;
                }

                if (resultInfo.equals(Constant.SUCCESS)) {
                    // 登录成功
                    onLoginCompleted();

                } else if (resultInfo.equals(Constant.FAIL)) {
                    // 登录失败
                    DYToast.makeText(this, R.string.user_send_login_response_1, Toast.LENGTH_SHORT).show();

                } else if (resultInfo.equals(Constant.INVALID)) {
                    // 参数错误
                    DYToast.makeText(this, R.string.user_send_login_response_2, Toast.LENGTH_SHORT).show();

                } else if (resultInfo.equals(Constant.LOCKED)) {
                    // 用户被锁定
                    showPasswordToMatchErrorDialog();

                    DYToast.makeText(this, R.string.user_send_login_response_3, Toast.LENGTH_SHORT).show();

                } else if (resultInfo.equals(Constant.NOTEXIST)
                        || resultInfo.equals(Constant.NOTEXIST2)) {
                    // 用户不存在
                    DYToast.makeText(this, R.string.user_send_login_response_4, Toast.LENGTH_SHORT).show();

                } else if (resultInfo.equals(Constant.CODE_REQUIRED)) {
                    // 需要输入验证
                    DYToast.makeText(this, R.string.user_send_login_response_7, Toast.LENGTH_SHORT).show();

                } else if (resultInfo.equals(Constant.INVALID_CODE)) {
                    // 验证码失效
                    DYToast.makeText(this, R.string.user_send_login_response_7, Toast.LENGTH_SHORT).show();

                } else if (resultInfo.equals(Constant.ERROR_CODE)) {
                    // 验证码错误
                    DYToast.makeText(this, R.string.user_send_login_response_7, Toast.LENGTH_SHORT).show();

                } else if (resultInfo.equals(Constant.INVALID_CREDENTIAL)) {
                    // 验证码错误
                    DYToast.makeText(this, R.string.user_send_login_response_8, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onErrorResponse(String operationType, Throwable throwable, String tag) {
        mIsOnLogin = false;
    }
}
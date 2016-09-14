package com.datayes.dyoa.module.login.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.datayes.baseapp.tools.DYToast;
import com.datayes.baseapp.utils.KeyBoardUtils;
import com.datayes.dyoa.R;
import com.datayes.dyoa.bean.user.AccountBean;
import com.datayes.dyoa.bean.user.UserLoginBean;
import com.datayes.dyoa.common.base.BaseActivity;
import com.datayes.dyoa.common.config.Config;
import com.datayes.dyoa.common.config.RequestInfo;
import com.datayes.dyoa.common.imageloader.DYImageLoader;
import com.datayes.dyoa.common.network.BaseService;
import com.datayes.dyoa.common.networkstatus.NetworkState;
import com.datayes.dyoa.common.view.CEditText;
import com.datayes.dyoa.common.view.CTitle;
import com.datayes.dyoa.module.login.Constant;
import com.datayes.dyoa.module.login.manager.UserManager;
import com.datayes.dyoa.module.login.service.UserService;
import com.datayes.dyoa.module.user.CurrentUser;
import com.datayes.dyoa.module.user.activity.MineActivity;
import com.datayes.dyoa.utils.AppUtil;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    public static final int REQUEST_CODE_RESET_PWD = 100;

    @BindView(R.id.ct_title)
    CTitle cTitle;
    @BindView(R.id.userTxt)
    CEditText mUser;
    @BindView(R.id.pwdText)
    CEditText mPwd;
    @BindView(R.id.codeTxt)
    CEditText mCode;
    @BindView(R.id.loginBtn)
    Button btnLogin;
    @BindView(R.id.forgetPwdTxt)
    TextView btnForget;
    @BindView(R.id.v_bottom_line)
    View mVTextCodeBottomLine;

    // 验证码图片加载器
    private DYImageLoader imageLoader_;

    private UserManager request_;
    private UserService service_;

    private boolean mIsOnLogin = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void onNetworkStateChanged(NetworkState networkState) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //重置密码功能暂时不用
        btnForget.setVisibility(View.GONE);
        cTitle.getLeftBtn().setVisibility(View.INVISIBLE);

        this.init();
        this.initEvent();
    }

    @Override
    public BaseService initService() {

        if (service_ == null) {
            service_ = new UserService();
        }

        return service_;
    }

    private void init() {
        request_ = new UserManager();
        imageLoader_ = DYImageLoader.getInstance();

        mCode.rightIamge.setContentDescription(getString(R.string.my_login_code));
        mCode.rightText.setText(getString(R.string.loading_state));

        AppUtil.setViewEnableView(btnLogin, false);

        mUser.setCurRegEx(AppUtil.REG_EX_INPUT_STRING);
        mPwd.setCurRegEx(AppUtil.REG_EX_INPUT_STRING);
    }

    private void initEvent() {
        mUser.setOnEditListener(new CEditText.OnEditListener() {

            @Override
            public void onEdit(boolean comList) {
                refreshAllBtns();
            }
        });

        mPwd.setOnEditListener(new CEditText.OnEditListener() {

            @Override
            public void onEdit(boolean comList) {
                refreshAllBtns();
            }
        });

        mPwd.setRightBtnClick(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (mPwd != null) {

                    Button rightBtn = mPwd.getRightBtn();

                    if (rightBtn != null) {

                        if (mPwd.getEditText().getInputType() == CEditText.MyInputType.visible_password.getType()) {

                            mPwd.setInputType(CEditText.MyInputType.textPassword.getTypeName());
                            rightBtn.setBackgroundResource(R.mipmap.eyeclose2x);

                        } else {

                            mPwd.setInputType(CEditText.MyInputType.visible_password.getTypeName());
                            rightBtn.setBackgroundResource(R.mipmap.eyeopen2x);
                        }
                    }
                }
            }
        });

        mCode.setOnEditListener(new CEditText.OnEditListener() {

            @Override
            public void onEdit(boolean comList) {
                refreshAllBtns();
            }
        });

        mCode.setRightIamgeClick(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                refreshCaptchaImage();
            }
        });

    }

    // 刷新按钮的状态
    private void refreshAllBtns() {

        if (mPwd != null && mPwd != null && mCode != null && btnLogin != null) {

            String pwd = mPwd.getText();
            String phone = mPwd.getText();

            if (mCode.getVisibility() == View.GONE) {

                AppUtil.setViewEnableView(
                        btnLogin, pwd.length() >= 6 && phone.length() >= 2);

            } else {

                String code = mCode.getText();

                AppUtil.setViewEnableView(
                        btnLogin, pwd.length() >= 6 && phone.length() >= 2 && code.length() >= 4);

            }
        }
    }

    // 刷新图片验证码
    private void refreshCaptchaImage() {

        if (mCode != null && imageLoader_ != null && mCode.rightIamge != null) {

            mCode.rightIamge.setVisibility(View.VISIBLE);
            mCode.setVisibility(View.VISIBLE);
            mVTextCodeBottomLine.setVisibility(View.VISIBLE);

            imageLoader_.displayFullImage(
                    new StringBuilder()
                            .append(Config.getConfig().getUrl(Config.ConfigUrlType.USER_MASTER))
                            .append(RequestInfo.CAPTCHA_IMAGE.getOperationType()).toString(),
                    mCode.rightIamge, false, false, new CaptchaImageLoadingListener());
        }
    }

    private class CaptchaImageLoadingListener implements ImageLoadingListener {

        @Override
        public void onLoadingStarted(String s, View view) {
            mCode.rightIamge.setVisibility(View.GONE);
            mCode.rightText.setVisibility(View.VISIBLE);
        }

        @Override
        public void onLoadingFailed(String s, View view, FailReason failReason) {
            mCode.rightIamge.setVisibility(View.VISIBLE);
            mCode.rightText.setVisibility(View.GONE);
            DYToast.makeText(LoginActivity.this, R.string.user_send_login_response_10, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onLoadingComplete(String s, View view, Bitmap bitmap) {
            mCode.rightIamge.setVisibility(View.VISIBLE);
            mCode.rightText.setVisibility(View.GONE);
        }

        @Override
        public void onLoadingCancelled(String s, View view) {
            mCode.rightIamge.setVisibility(View.VISIBLE);
            mCode.rightText.setVisibility(View.GONE);
            DYToast.makeText(LoginActivity.this, R.string.user_send_login_response_10, Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick({R.id.loginBtn, R.id.forgetPwdTxt})
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.forgetPwdTxt://忘记密码
                Intent intent = new Intent(this, ResetPasswordActivity.class);
                startActivityForResult(intent, REQUEST_CODE_RESET_PWD);
                break;

            case R.id.loginBtn:
                // 登陆操作
                if (request_ != null && mUser != null && mPwd != null && mCode != null && !mIsOnLogin) {

                    mIsOnLogin = true;

                    String username = mUser.getText();

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
                            mPwd.getText(),
                            mCode.getVisibility() == View.VISIBLE ? mCode.getText() : "",
                            tenant);

                    showLoading();

                    //软键盘关闭
                    mPwd.getEditText().clearFocus();
                    KeyBoardUtils.closeKeybord(mPwd.getEditText(), this);
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
        Intent intent = new Intent(this, MineActivity.class);
        startActivity(intent);

        DYToast.makeText(this, R.string.user_send_login_response_0, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void networkFinished(String operationType,
                                BaseService response, int code, String tag) {
        hideLoading();
        mIsOnLogin = false;

        if (service_ != null) {
            // 登录
            if (RequestInfo.LOGIN_ACCOUNT.getOperationType().equals(operationType)) {

                UserLoginBean bean = service_.getLoginBean();
                String resultInfo = bean.getInfo();

                if (!resultInfo.equals(Constant.SUCCESS)) {
                    // 刷新验证码
                    refreshCaptchaImage();
                    return;
                }

                if (resultInfo.equals(Constant.SUCCESS)) {
                    // 登录成功
                    showLoading();
                    CurrentUser.getInstance().setAccountInfo(null);
                    CurrentUser.getInstance().refreshAccountInfo(new CurrentUser.onRefreshAccountInfo() {

                        @Override
                        public void onRefreshed(AccountBean accountInfo) {
                            hideLoading();
                            onLoginCompleted();
                        }

                        @Override
                        public void onError() {
                            hideLoading();
                            DYToast.show(LoginActivity.this, R.string.user_send_login_response_9, Toast.LENGTH_SHORT);
                        }
                    });

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
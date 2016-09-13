package com.datayes.dyoa.module.swipecard.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.datayes.dyoa.R;
import com.datayes.dyoa.common.base.BaseActivity;
import com.datayes.dyoa.common.network.BaseService;
import com.datayes.dyoa.common.networkstatus.NetworkState;
import com.datayes.dyoa.common.view.CTitle;
import com.datayes.dyoa.module.swipecard.activity.manager.SwipeManager;
import com.datayes.dyoa.module.swipecard.activity.manager.SwipeService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by datayes on 16/9/13.
 */
public class SwipeCardActivity extends BaseActivity {
    @BindView(R.id.ct_title)
    CTitle mCtTitle;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.tv_shop_name)
    TextView mTvShopName;
    @BindView(R.id.ev_money)
    EditText mEvMoney;
    @BindView(R.id.btn_pay)
    TextView mBtnPay;


    /**
     * 最大数字，我们取int型最大值
     */
    public static final int MAX_VALUE = 10000;

    public static final int PONTINT_LENGTH = 2;

    Pattern p = Pattern.compile("[0-9]*");   //除数字外的其他的


    private SwipeManager mSwipeManager;
    private SwipeService mSwipeService;
    String shopName;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_money;
    }

    @Override
    public void onNetworkStateChanged(NetworkState networkState) {

    }

    @Override
    public void onErrorResponse(String operationType, Throwable throwable, String tag) {

    }

    @Override
    public void networkFinished(String operationType, BaseService service, int code, String tag) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSwipeManager = new SwipeManager();
        mSwipeManager.getRestaurantList(this, this);
        initUI();
    }


    @Override
    public BaseService initService() {
        if (mSwipeService == null)
            mSwipeService = new SwipeService();
        return mSwipeService;
    }


    private void initUI() {

        String userName = "哈哈";
        shopName = "大家乐餐厅";

        mTvUserName.setText(userName);
        mTvShopName.setText(shopName);

        mCtTitle.setLeftBtnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mEvMoney.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                        if (dest.length() <= 0 && source.equals("0"))
                            return "";

                        String oldtext = dest.toString();

                        //验证删除等按键
                        if ("".equals(source.toString())) {
                            return null;
                        }

                        //验证非数字或者小数点的情况
                        Matcher m = p.matcher(source);
                        if(oldtext.contains(".")){
                        //已经存在小数点的情况下，只能输入数字
                            if(!m.matches()){
                                return null;
                            }
                        }else{
                        //未输入小数点的情况下，可以输入小数点和数字
                            if(!m.matches() && !source.equals(".") ){
                                return null;
                            }
                        }

                        if (!source.toString().equals("")) {
                            double dold = Double.parseDouble(oldtext + source.toString());
                            if (dold > MAX_VALUE) {
                                return dest.subSequence(dstart, dend);
                            } else if (dold == MAX_VALUE) {
                                if (source.toString().equals(".")) {
                                    return dest.subSequence(dstart, dend);
                                }

                            }

                        }

                        if (oldtext.contains(".")) {
                            int index = oldtext.indexOf(".");
                            int len = dend - index;

                            if (len > PONTINT_LENGTH) {
                                CharSequence newText = dest.subSequence(dstart, dend);
                                return newText;
                            }
                        }
                        return dest.subSequence(dstart, dend) + source.toString();
                    }
                }
        });

    }


    @OnClick(R.id.btn_pay)
    public void onClick() {
        String moneyStr = mEvMoney.getText().toString();
//        mSwipeManager.sendUserTradeMessage(this,this,moneyStr,shopName,"");
        jumpNextPage();
    }


    private void jumpNextPage(){
        Intent intent = new Intent(this,SwipeSuccessActivity.class);
        intent.putExtra(SwipeSuccessActivity.SHOP_NAME_KEY,shopName);
        intent.putExtra(SwipeSuccessActivity.MONEY_VALUE_KEY,mEvMoney.getText().toString());
        startActivity(intent);
    }


}

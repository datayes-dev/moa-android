/*
 * *
 *  * 通联数据机密
 *  * --------------------------------------------------------------------
 *  * 通联数据股份公司版权所有 © 2013-2016
 *  *
 *  * 注意：本文所载所有信息均属于通联数据股份公司资产。本文所包含的知识和技术概念均属于
 *  * 通联数据产权，并可能由中国、美国和其他国家专利或申请中的专利所覆盖，并受商业秘密或
 *  * 版权法保护。
 *  * 除非事先获得通联数据股份公司书面许可，严禁传播文中信息或复制本材料。
 *  *
 *  * DataYes CONFIDENTIAL
 *  * --------------------------------------------------------------------
 *  * Copyright © 2013-2016 DataYes, All Rights Reserved.
 *  *
 *  * NOTICE: All information contained herein is the property of DataYes
 *  * Incorporated. The intellectual and technical concepts contained herein are
 *  * proprietary to DataYes Incorporated, and may be covered by China, U.S. and
 *  * Other Countries Patents, patents in process, and are protected by trade
 *  * secret or copyright law.
 *  * Dissemination of this information or reproduction of this material is
 *  * strictly forbidden unless prior written permission is obtained from DataYes.
 *
 */

package com.datayes.dyoa.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.datayes.dyoa.R;

import java.io.IOException;


/**
 * @brief 公用title
 * @date 2014-11-13 上午9:56:08
 */
public class CTitle extends RelativeLayout implements OnClickListener {

    /**
     * @author shenen.gao
     *  @brief 标题层大于3层有返回按钮
     *  @param context
     *  @param btn void      
     *  @throws  
     * @date 2016-2-26 上午11:13:30
     *     
     */


    private Button fRrightLeftBtn;// 右侧左数第一个按钮
    private Button fRightBtn, fLeftBtn, fLeftBtn1;// 最右侧按钮,左侧按钮
    private TextView fTitleTxt, fTitleTxt1, fRightText;// 标题，右侧按钮显示文案
    private RelativeLayout titleBar;

    public Button getfRrightLeftBtn() {
        return fRrightLeftBtn;
    }

    public CTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        setAttribute(context, attrs);
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.title_bar, this, true);
        titleBar = (RelativeLayout) findViewById(R.id.rl_title_bar);
        fTitleTxt = (TextView) findViewById(R.id.titleTxt);
        fTitleTxt1 = (TextView) findViewById(R.id.titleTxt1);
        fRrightLeftBtn = (Button) findViewById(R.id.rightLeftBtn);
        fRightBtn = (Button) findViewById(R.id.rightBtn);
        fLeftBtn = (Button) findViewById(R.id.leftBtn);
        fLeftBtn1 = (Button) findViewById(R.id.leftBtn1);
        fRightText = (TextView) findViewById(R.id.rightText);
    }

    private void setAttribute(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.CTitle);

        String txt = typedArray.getString(R.styleable.CTitle_titleValue);
        if (txt != null && !"".equals(txt)) {
            fTitleTxt.setText(txt);
        }

        Drawable right_bg = typedArray
                .getDrawable(R.styleable.CTitle_title_right_btn_bg);
        if (right_bg != null) {
            right_bg.setBounds(0, 0,
                    right_bg.getMinimumWidth(),
                    right_bg.getMinimumHeight());

            fRightBtn.setCompoundDrawables(right_bg, null, null, null);
            fRightBtn.setVisibility(View.VISIBLE);
        }

        if (typedArray.getBoolean(R.styleable.CTitle_title_back, false)) {// title_back
            // 属性设置为true时
            // title返回键模拟发送硬件返回键
            fLeftBtn.setOnClickListener(this);
        }
        if (typedArray.getBoolean(R.styleable.CTitle_display_left_back_drawable,
                false)) {// title_back
            // 属性设置为true时
            // title返回键有图标
            fLeftBtn.setCompoundDrawables(null, null, null, null);
        }

        Drawable left_btn_drawable = typedArray.getDrawable(R.styleable.CTitle_left_btn_drawable);
        if (left_btn_drawable != null) {
            left_btn_drawable.setBounds(0, 0,
                    left_btn_drawable.getMinimumWidth(),
                    left_btn_drawable.getMinimumHeight());

            fLeftBtn.setCompoundDrawables(left_btn_drawable, null, null, null);
        }

        if (typedArray.getBoolean(R.styleable.CTitle_leftBtn_visibility, false)) {
            fLeftBtn.setVisibility(View.GONE);
        }

        String rightInfo = typedArray.getString(R.styleable.CTitle_rightNote);
        if (rightInfo != null && !"".equals(rightInfo)) {
            fRightText.setText(rightInfo);

        }
        Drawable title_rightNote_drawable = typedArray
                .getDrawable(R.styleable.CTitle_rightNote_drawable);
        if (title_rightNote_drawable != null) {
            title_rightNote_drawable.setBounds(0, 0,
                    title_rightNote_drawable.getMinimumWidth(),
                    title_rightNote_drawable.getMinimumHeight());

            fRightText.setCompoundDrawables(null, null,
                    title_rightNote_drawable, null);
        }

        int right_text_margin = (int) typedArray.getDimension(
                R.styleable.CTitle_right_text_margin_right, 0);

        if (right_text_margin > 0) {

            LayoutParams layoutParams = (LayoutParams) fRightText
                    .getLayoutParams();

            if (layoutParams != null) {

                layoutParams.setMargins(0, 0, right_text_margin, 0);

                fRightText.setLayoutParams(layoutParams);
            }
        }

        int right_text_appearance = typedArray.getResourceId(
                R.styleable.CTitle_right_text_text_appearance, 0);

        if (right_text_appearance > 0) {
            fRightText.setTextAppearance(getContext(), right_text_appearance);
        }

        Drawable right_left_bg = typedArray
                .getDrawable(R.styleable.CTitle_title_right_left_btn_bg);
        if (right_left_bg != null) {

            right_left_bg.setBounds(0, 0,
                    right_left_bg.getMinimumWidth(),
                    right_left_bg.getMinimumHeight());

            fRrightLeftBtn.setCompoundDrawables(right_left_bg, null, null, null);

        }

        int right_btn_margin_right = (int) typedArray.getDimension(
                R.styleable.CTitle_right_btn_margin_right, 0);

        if (right_btn_margin_right > 0) {

            fRightBtn.setPadding(0, 0, right_btn_margin_right, 0);
        }

        typedArray.recycle();
    }

    public void setRightBtnVisibility(int vis) {
        if (fRightBtn != null) {
            fRightBtn.setVisibility(vis);
        }
    }

    public void setLeftValue(String leftValue) {
        if (leftValue != null && !"".equals(leftValue) && fLeftBtn != null) {
            fLeftBtn.setText(leftValue);
        }

    }

    public Button getRightBtn() {
        return fRightBtn;
    }

    public Button getLeftBtn() {
        return fLeftBtn;
    }

    public TextView getRightText() {
        return fRightText;
    }

    public void setRightBtnValue(String leftValue) {
        if (fRightBtn != null && !"".equals(fRightBtn) && fRightBtn != null) {
            fRightBtn.setText(leftValue);
        }

    }

    public void setRightTextClick(View.OnClickListener l) {
        if (fRightText != null) {
            fRightText.setOnClickListener(l);
        }
    }

    public void setRightTextStr(String str) {
        if (fRightText != null) {
            fRightText.setText(str);
        }
    }

    public void setRightLeftBtnClick(View.OnClickListener l) {
        if (fRrightLeftBtn != null) {
            fRrightLeftBtn.setOnClickListener(l);
        }
    }

    public void setRightBtnText(String rightbtn) {
        findViewById(R.id.rightFenGeLayout).setVisibility(View.VISIBLE);
        fRightBtn.setVisibility(View.GONE);
        Button rightFenGe = (Button) findViewById(R.id.rightFenGe);

        rightFenGe.setPadding(15, 0, 15, 0);
        rightFenGe.setText(rightbtn);


    }

    public void setRightBtnBg(int resid) {
        if (fRightBtn != null) {
            fRightBtn.setBackgroundResource(resid);

        }
    }

    public void setrightFenGeClick(View.OnClickListener l) {
        findViewById(R.id.rightFenGe).setOnClickListener(l);

    }

    public void setRightBtnClick(View.OnClickListener l) {
        if (fRightBtn != null) {
            fRightBtn.setOnClickListener(l);
        }
    }

    public void setLeftBtnClick(View.OnClickListener l) {
        if (fLeftBtn != null) {
            fLeftBtn.setOnClickListener(l);
        }
    }

    public void setTitleText(String value) {
        if (fTitleTxt != null) {
            fTitleTxt.setText(value);
        }
    }

    public void setTitleBarColor(int color) {
        if (titleBar != null) {
            titleBar.setBackgroundColor(titleBar.getResources().getColor(color));
        }
    }

    public TextView getTextView() {
        return fTitleTxt;
    }

    public TextView getTextView1() {
        return fTitleTxt1;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.leftBtn:
                try {
                    // 模拟发送返回键
                    Runtime runtime = Runtime.getRuntime();
                    runtime.exec("input keyevent " + KeyEvent.KEYCODE_BACK);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }
}

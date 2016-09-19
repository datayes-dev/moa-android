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
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.datayes.dyoa.App;
import com.datayes.dyoa.R;

/**
 * "我的"页面--单个cell封装
 * <p>
 * Created by hongfei.tao on 2016/7/19 17:37.
 */
public class MineItemView extends RelativeLayout {

    public static int DEFAULT_RIGHT_IMG = R.drawable.goin2;

    private ImageView mItemIcon;
    private TextView mItemText;
    private TextView mItemOtherText;
    private ImageView mItemRightImg;
    private View mDotView;

    public MineItemView(Context context) {
        this(context, null, 0);
    }

    public MineItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MineItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MineItemView);

        Drawable itemIcon = typedArray.getDrawable(R.styleable.MineItemView_itemIcon);
        String itemText = typedArray.getString(R.styleable.MineItemView_itemText);
        String itemOtherText = typedArray.getString(R.styleable.MineItemView_itemOtherText);
        Drawable itemRightImage = typedArray.getDrawable(R.styleable.MineItemView_itemRightImg);
        boolean imgShow = typedArray.getBoolean(R.styleable.MineItemView_imgVisable, true);
        boolean otherTextShow = typedArray.getBoolean(R.styleable.MineItemView_otherTextVisable, false);
        float textSize = typedArray.getDimensionPixelSize(R.styleable.MineItemView_itemTextSize, 16);

        typedArray.recycle();

        if (itemIcon != null) {
            mItemIcon.setImageDrawable(itemIcon);
        }

        if (itemText != null) {

            mItemText.setText(itemText);
        }

        if (itemOtherText != null) {

            mItemOtherText.setText(itemOtherText);
        }

        if (itemRightImage == null) {
            mItemRightImg.setImageDrawable(getResources().getDrawable(DEFAULT_RIGHT_IMG));
        } else {
            mItemRightImg.setImageDrawable(itemRightImage);
        }

        mItemRightImg.setVisibility(imgShow ? VISIBLE : GONE);
        mItemOtherText.setVisibility(otherTextShow ? VISIBLE : GONE);
        mItemText.setTextSize(textSize);
    }

    private void initView() {
        View.inflate(getContext(), R.layout.view_mine_item, this);

        mItemIcon = (ImageView) findViewById(R.id.iv_item_icon);
        mItemText = (TextView) findViewById(R.id.tv_item_text);
        mItemOtherText = (TextView) findViewById(R.id.tv_item_other_text);
        mItemRightImg = (ImageView) findViewById(R.id.iv_item_right_img);
        mDotView = findViewById(R.id.view_dot);
    }

    public void setItemOtherText(String otherText) {
        this.mItemOtherText.setText(otherText);
    }

    public void showRedDot() {
        mDotView.setVisibility(VISIBLE);
    }

    public void hideRedDot() {
        mDotView.setVisibility(GONE);
    }

    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, App.getInstance().getSreenMetrics());
    }
}

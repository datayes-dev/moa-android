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

package com.datayes.dinnerbusiness.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.datayes.baseapp.utils.StringUtil;
import com.datayes.dinnerbusiness.R;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author MoRong
 * @version Who When What -------- ----------
 *          ----------------------------------- MoRong 2014-3-12
 *          TODO
 * @brief 目前仅支持在布局中使用该控件，
 * @date 2014-3-12 上午9:51:10
 */
public class CEditText extends RelativeLayout {
    private EditText editText;
    private TextView errNote;
    private TextView label;
    public Button rightBtn;
    public ImageView clearbtn;
    private RelativeLayout layout;
    private RelativeLayout inputLayout;
    private LinearLayout rightBtnLayout;

    private Drawable nomalBg;
    private Drawable selectBg;
    private boolean mClear_;
    private boolean isOnSetText = false;
    private boolean clearImage;
    private FocusChange focusChange;
    private TextView leftText;
    public TextView rightText;
    public ImageView rightIamge;
    public OnEditListener mEditListener;
    private int maxlength;

    private String curRegEx_ = "";//过滤的正则表达式

    public void setCurRegEx(String v) {

        curRegEx_ = v;
    }

    public CEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        attributeSet(context, attrs);
    }

    /**
     * @param context 上下文环境
     * @param attrs   属性集合
     * @return void
     * @brief 设置控件属性
     * @date 2014-3-12 上午10:07:53
     * @version : v1.00
     * @author MoRong
     * Update Date :
     * Update Author : MoRong
     */

    @SuppressWarnings("deprecation")
    private void attributeSet(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.custom);
        if (typedArray.getBoolean(R.styleable.custom_clear, false)) {
            setClear(true);
        }
        String txt = typedArray.getString(R.styleable.custom_lable);
        if (txt != null && !"".equals(txt)) {
            label.setText(txt);
        }

        String err = typedArray.getString(R.styleable.custom_errNote);
        if (err != null && !"".equals(err)) {
            errNote.setText(err);
        }

        String hit = typedArray.getString(R.styleable.custom_hit);
        if (hit != null && !"".equals(hit)) {
            getEditText().setHint(hit);
        }

        getEditText().setTextColor(typedArray.getColor(R.styleable.custom_text_color, 0x000000));

        nomalBg = typedArray.getDrawable(R.styleable.custom_nomal_bg);
        selectBg = typedArray.getDrawable(R.styleable.custom_select_bg);

        //TODO 这里注释掉了，因为默认什么都不显示比较合适，高神恩
//		if (nomalBg == null) {
//			nomalBg = getResources().getDrawable(R.drawable.c_edit_n);
//		}
//		if (selectBg == null) {
//			selectBg = getResources().getDrawable(R.drawable.c_edit_s);
//		}
        inputLayout.setBackgroundDrawable(nomalBg);
        BitmapDrawable leftDrawable = (BitmapDrawable) typedArray
                .getDrawable(R.styleable.custom_left_btn_bg);
        if (leftDrawable != null) {
            leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
            leftText.setCompoundDrawables(null, null, leftDrawable, null);
            leftText.setVisibility(View.VISIBLE);
        }

        Drawable rightDrawable = typedArray
                .getDrawable(R.styleable.custom_right_btn_bg);
        if (rightDrawable != null) {
            rightIamge.setBackgroundDrawable(rightDrawable);
            rightIamge.setVisibility(View.VISIBLE);
        }
        String type = typedArray.getString(R.styleable.custom_input_typed);

        if (type != null && !"".equals(type)) {
            getEditText().setInputType(MyInputType.valueOf(type).getType());

        }

        int length = typedArray.getInteger(R.styleable.custom_length, -1);
        maxlength = length;
        if (length > 0) {
            getEditText().setFilters(
                    new InputFilter[]{new InputFilter.LengthFilter(length)});
        }

        String right_btn_text = typedArray
                .getString(R.styleable.custom_right_btn_text);

        if (right_btn_text != null && !"".equals(right_btn_text)) {
//			getEditText().setInputType(MyInputType.valueOf(type).getType());
            rightBtnLayout.setVisibility(VISIBLE);
            rightBtn.setText(right_btn_text);
        }

        Drawable rightBtnDrawable = typedArray
                .getDrawable(R.styleable.custom_right_btn);
        if (rightBtnDrawable != null) {
            rightBtnLayout.setVisibility(VISIBLE);
            rightBtn.setBackgroundDrawable(rightBtnDrawable);
        }

        float title_padding_left = typedArray.getDimension(R.styleable.custom_text_padding_left, 0);

        if (title_padding_left > 0) {
            editText.setPadding((int) title_padding_left, 0, 0, 0);
        }

        float right_btn_width = typedArray.getDimension(R.styleable.custom_right_btn_width, 0);

        if (right_btn_width > 0) {
            rightBtn.getLayoutParams().width = (int) right_btn_width;
            rightBtn.setLayoutParams(rightBtn.getLayoutParams());
        }

        float right_btn_height = typedArray.getDimension(R.styleable.custom_right_btn_height, 0);

        if (right_btn_height > 0) {
            rightBtn.getLayoutParams().height = (int) right_btn_height;
            rightBtn.setLayoutParams(rightBtn.getLayoutParams());
        }

        float right_btn_padding_left = typedArray.getDimension(R.styleable.custom_right_btn_padding_left, 0);
        float right_btn_padding_right = typedArray.getDimension(R.styleable.custom_right_btn_padding_right, 0);

        if (right_btn_padding_left > 0 || right_btn_padding_right > 0) {
            rightBtnLayout.setPadding((int) right_btn_padding_left, 0, (int) right_btn_padding_right, 0);
            rightBtn.setPadding(0, 0, 0, 0);
        }

        int right_btn_text_appearance = typedArray.getResourceId(R.styleable.custom_right_btn_text_appearance, 0);

        if (right_btn_text_appearance > 0) {
            rightBtn.setTextAppearance(getContext(), right_btn_text_appearance);
        }

        android.widget.RelativeLayout.LayoutParams right_iamge_parmas =
                (android.widget.RelativeLayout.LayoutParams) rightIamge.getLayoutParams();

        int right_iamge_margin_right = (int) typedArray.getDimension(R.styleable.custom_right_iamge_margin_right, 0);

        if (right_iamge_margin_right > 0 && right_iamge_parmas != null) {

            right_iamge_parmas.setMargins(0, 0, right_iamge_margin_right, 0);
            rightIamge.setLayoutParams(right_iamge_parmas);
        }

        int right_iamge_width = (int) typedArray.getDimension(R.styleable.custom_right_iamge_width, 0);

        if (right_iamge_width > 0 && right_iamge_parmas != null) {

            right_iamge_parmas.width = right_iamge_width;
            rightIamge.setLayoutParams(right_iamge_parmas);

            rightText.getLayoutParams().width = right_iamge_width;
            rightText.setLayoutParams(rightText.getLayoutParams());
        }

        int right_iamge_height = (int) typedArray.getDimension(R.styleable.custom_right_iamge_height, 0);

        if (right_iamge_height > 0 && right_iamge_parmas != null) {

            right_iamge_parmas.height = right_iamge_height;
            rightIamge.setLayoutParams(right_iamge_parmas);

            rightText.getLayoutParams().height = right_iamge_height;
            rightText.setLayoutParams(rightText.getLayoutParams());
        }

        typedArray.recycle();
    }

    /**
     * @return void
     * @brief 初始化控件，设置初始监听事件
     * @date 2014-3-12 上午10:09:25
     * @version : v1.00
     * @author MoRong
     * Update Date :
     * Update Author : MoRong
     */
    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.com_edit_text, this, true);
        setEditText((EditText) findViewById(R.id.editText));
        label = (TextView) findViewById(R.id.label);
        rightBtn = (Button) findViewById(R.id.rightBtn);
        clearbtn = (ImageView) findViewById(R.id.clearbtn);

        layout = (RelativeLayout) findViewById(R.id.layout);
        inputLayout = (RelativeLayout) findViewById(R.id.inputLayout);
        rightBtnLayout = (LinearLayout) findViewById(R.id.rightBtnLayout);
        errNote = (TextView) findViewById(R.id.errNote);
        leftText = (TextView) findViewById(R.id.leftTxt);
        rightText = (TextView) findViewById(R.id.rightText);
        rightIamge = (ImageView) findViewById(R.id.rightIamge);
        getEditText().setLongClickable(false);
        initClear();
        initOnFcousChange();
        ininWatch();
    }

    public void initClear() {
        clearbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View paramView) {
                getEditText().setText("");
                getEditText().requestFocus();
                if (clearClick != null) {
                    clearClick.clear();
                }
            }
        });
    }

    public void clearText() {
        getEditText().setText("");
    }

    private void initOnFcousChange() {
        getEditText().setOnFocusChangeListener(new OnFocusChangeListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (getEditText().isEnabled()) {
                    if (hasFocus) {
                        inputLayout.setBackgroundDrawable(selectBg);
                    } else {
                        inputLayout.setBackgroundDrawable(nomalBg);
                    }
                } else {

                }
                if (focusChange != null)
                    focusChange.onFocusChange(v, hasFocus);
            }
        });
    }

    public void setLableW(String source) {
        TextPaint paint = label.getPaint();
        int width = (int) Layout.getDesiredWidth(source, 0, source.length(),
                paint);
        android.view.ViewGroup.LayoutParams params = label.getLayoutParams();
        params.width = width;
        label.setLayoutParams(params);
    }

    public int getLableW(String source) {
        TextPaint paint = label.getPaint();
        return (int) Layout.getDesiredWidth(source, 0, source.length(), paint);
    }

    private void setClear(boolean clear) {
        this.mClear_ = clear;
    }

    /**
     * @return String
     * @brief 获取输入框中文本
     * @date 2014-3-12 上午9:58:42
     * @version : v1.00
     * @author MoRong
     * Update Date :
     * Update Author : MoRong
     */
    public String getText() {
        if (getEditText() == null) {
            return "";
        }
        return getEditText().getEditableText().toString();
    }

    public void setSelection() {
        if (getEditText() == null) {
            return;
        }
        CharSequence text = getEditText().getEditableText();
        if (text instanceof Spannable) {
            Spannable spanText = (Spannable) text;
            Selection.setSelection(spanText, text.length());
        }
    }

    public Editable getTextEditable() {
        return getEditText().getText();
    }

    public void setHit(String hit) {
        if (getEditText() != null) {
            getEditText().setHint(hit);
        }

    }

    public void setTextSize(int size) {
        if (getEditText() != null) {
            getEditText().setTextSize(size);
        }

    }

    /**
     * @param text
     * @return void
     * @brief 向输入框中填写文本内容
     * @date 2014-3-12 上午9:59:13
     * @version : v1.00
     * @author MoRong
     * Update Date :
     * Update Author : MoRong
     */
    public void setText(String text) {
        if (getEditText() != null) {
            isOnSetText = true;
            getEditText().setText(text);
        }
    }

    public void setLength(int length) {
        getEditText().setFilters(
                new InputFilter[]{new InputFilter.LengthFilter(length)});
    }

    /**
     * @return int
     * @brief 获得光标位置
     * @date 2014-3-12 上午10:03:25
     * @version : v1.00
     * @author MoRong
     * Update Date :
     * Update Author : MoRong
     */
    public int getSelectionStart() {
        return getEditText().getSelectionStart();
    }

    public int getSelectionEnd() {
        return getEditText().getSelectionEnd();
    }

    /**
     * @param position
     * @return void
     * @brief 设置光标位置
     * @date 2014-3-12 上午10:03:49
     * @version : v1.00
     * @author MoRong
     * Update Date :
     * Update Author : MoRong
     */
    public void setSelection(int position) {
        getEditText().setSelection(position);
    }

    /**
     * @param textVlue
     * @return void
     * @brief 设置文本并且保持光标位置在末尾
     * @date 2014-3-12 上午10:04:07
     * @version : v1.00
     * @author MoRong
     * Update Date :
     * Update Author : MoRong
     */
    public void setEndtion(String textVlue) {
        int position = textVlue.length();
        getEditText().setSelection(position);
    }

    /**
     * @param watcher
     * @return void
     * @brief 添加额外的TextWatcher方法
     * @date 2014-3-12 上午9:59:57
     * @version : v1.00
     * @author MoRong
     * Update Date :
     * Update Author : MoRong
     */
    public void addWatcher(TextWatcher watcher) {
        if (getEditText() != null) {
            getEditText().addTextChangedListener(watcher);
        }
    }

    //setOnEditorActionListener是给文本框添加编辑监听事件
    public void setActionListener(OnEditorActionListener actionListener) {
        getEditText().setOnEditorActionListener(actionListener);
    }

    @SuppressWarnings("deprecation")
    public void enabled(boolean enabled) {
        if (getEditText() != null) {
            getEditText().setEnabled(enabled);
            if (enabled) {
                layout.setBackgroundDrawable(nomalBg);
                if (mClear_ && getEditText().getText().length() > 0) {
                    clearbtn.setVisibility(View.VISIBLE);
                    if (mEditListener != null)
                        mEditListener.onEdit(true);
                } else {
                    clearbtn.setVisibility(View.GONE);
                    if (mEditListener != null)
                        mEditListener.onEdit(false);
                }
            } else {
                clearbtn.setVisibility(View.GONE);
                if (mEditListener != null)
                    mEditListener.onEdit(false);
                // layout.setBackgroundResource(R.drawable.input_bg_dis);
            }
        }
    }

    public void showErrNote() {
        errNote.setVisibility(VISIBLE);
    }

    public void hideErrNote() {
        errNote.setVisibility(GONE);
    }

    public void setLabelText(String text) {
        label.setText(text);
    }

    public void setSelectClearORImage(boolean clearImage) {
        this.clearImage = clearImage;
    }

    private void ininWatch() {
        getEditText().addTextChangedListener(new TextWatcher() {

            private String beforeStr = "";
            private int selectionEnd;

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

                selectionEnd = editText.getSelectionEnd();
                beforeStr = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (isOnSetText) {
                    isOnSetText = false;
                    editText.setSelection(s.length());
                    return;
                }

                if (maxlength > 0) {

                    try {

                        int curCharCount = editText.getText().toString().getBytes("gb2312").length;

                        if (curCharCount > maxlength && curCharCount > 0) {

                            int oldSelection = selectionEnd;

                            editText.setText(beforeStr);
                            editText.setSelection(oldSelection);
                        }

                    } catch (UnsupportedEncodingException e) {

                        e.printStackTrace();
                    }
                }

                if (!StringUtil.checkStringEmpty(curRegEx_)) {

                    Pattern p = Pattern.compile(curRegEx_);
                    Matcher m = p.matcher(s.toString());

                    String outStr = m.replaceAll("").trim();

                    if (!outStr.equals(s.toString()))
                        editText.setText(outStr);
                }

                if (mClear_ && getEditText().isEnabled()
                        && getEditText().getText().length() > 0) {
                    clearbtn.setVisibility(View.VISIBLE);
                    if (clearImage) {
                        rightIamge.setVisibility(View.GONE);
                    }
                    if (mEditListener != null)
                        mEditListener.onEdit(true);
                } else {
                    clearbtn.setVisibility(View.GONE);
                    if (clearImage) {
                        rightIamge.setVisibility(View.VISIBLE);
                    }
                    if (mEditListener != null)
                        mEditListener.onEdit(false);
                }
            }
        });
    }

    /**
     * BareFieldName : focusChange
     *
     * @return the focusChange
     */

    public FocusChange getFocusChange() {
        return focusChange;
    }

    /**
     * @param focusChange the focusChange to set
     */
    public void setFocusChange(FocusChange focusChange) {
        this.focusChange = focusChange;
    }

    //获取焦点或失去焦点时调用
    public interface FocusChange {
        void onFocusChange(View v, boolean isFocused);

    }

    public void setOnEditListener(OnEditListener mEditListener) {
        this.mEditListener = mEditListener;
    }

    public interface OnEditListener {
        void onEdit(boolean comList);
    }

    public void setEditTextClick(View.OnClickListener l) {
        if (getEditText() != null) {
            getEditText().setOnClickListener(l);
        }
    }

    public void setRightBtnClick(View.OnClickListener l) {
        if (rightBtn != null) {
            rightBtn.setOnClickListener(l);
        }
    }

    public void setRightIamgeBg(int id) {
        if (rightIamge != null) {
            rightIamge.setBackgroundResource(id);
        }
    }

    public void setRightIamgeClick(View.OnClickListener l) {
        if (rightIamge != null) {
            rightIamge.setOnClickListener(l);
        }
    }

    public void setInputType(String type) {
        getEditText().setInputType(MyInputType.valueOf(type).getType());
        if (type.equals("visible_password"))
            rightIamge.setBackgroundResource(R.drawable.eyeopen2x);
        if (type.equals("textPassword"))
            rightIamge.setBackgroundResource(R.drawable.eyeclose2x);
        setEndtion(getEditText().getText().toString());
    }

    public enum MyInputType {
        number("number", InputType.TYPE_CLASS_NUMBER), visible_password(
                "visible_password",
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD), textPassword(
                "textPassword", InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_VARIATION_PASSWORD), numberDecimal(
                "numberDecimal", InputType.TYPE_NUMBER_FLAG_DECIMAL
                | InputType.TYPE_CLASS_NUMBER), phone("phone",
                InputType.TYPE_CLASS_PHONE);

        private String typeName;
        private int type;

        MyInputType(String typeName, int type) {
            this.typeName = typeName;
            this.type = type;
        }

        public int getType() {
            return type;
        }

        public String getTypeName() {
            return typeName;
        }
    }

    public interface OnClik {
        void rightImageBtnOnClick();
    }

    private ClearClick clearClick;

    public interface ClearClick {
        void clear();
    }

    /**
     * 获取xml中属性length的 大小
     *
     * @return int
     * @brief
     * @date 2014-6-5 下午07:13:20
     * @author MoRong
     * Update Date :
     * Update Author : MoRong
     */
    public int getMaxLenth() {
        return maxlength;
    }

    public void setHorizontallyScrolling() {
        getEditText().setHorizontallyScrolling(false);
    }

    public EditText getEditText() {
        return editText;
    }

    public void setTextColor(int color) {
        if (editText != null)
            editText.setTextColor(color);
    }

    public void setHintTextColor(int color) {
        if (editText != null)
            editText.setHintTextColor(color);
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    public void setNomalBg(Drawable value) {
        nomalBg = value;
    }

    public void setSelectBg(Drawable value) {
        selectBg = value;
    }

    public RelativeLayout getInputLayout() {
        return inputLayout;
    }

    public Button getRightBtn() {
        return rightBtn;
    }

    public TextView getTextLeft() {
        return leftText;
    }

    public ImageView getClearbtn() {
        return clearbtn;
    }

    /**
     * editText 设置单行or 多行显示
     *
     * @return void
     * @brief
     * @date 2014-7-14 下午03:00:31
     * @author MoRong
     * Update Date :
     * Update Author : MoRong
     */
    public void setEditTextSingleLine(boolean trueorfalse) {
        this.editText.setSingleLine(trueorfalse);
    }

    /**
     * 更换软键盘的换行键为那种方式 及设置显示的文字
     *
     * @return void
     * @brief
     * @date 2014-7-14 下午02:59:43
     * @author MoRong
     * Update Date :
     * Update Author : MoRong
     */
    public void setIme(int imeOptions, String labText) {
        this.editText.setImeOptions(imeOptions);
        this.editText.setImeActionLabel(labText, imeOptions);

    }

    public ClearClick getClearClick() {
        return clearClick;
    }

    public void setClearClick(ClearClick clearClick) {
        this.clearClick = clearClick;
    }

    public void setEditTextTypeface() {
        // this.editText.setTypeface(TypefaceSingle.getInstance().arialTypeface());
    }

}

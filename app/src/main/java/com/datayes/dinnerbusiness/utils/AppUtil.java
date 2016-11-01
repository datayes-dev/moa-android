package com.datayes.dinnerbusiness.utils;

import android.view.View;

import com.datayes.baseapp.utils.StringUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * 所有项目相关的工具类方法放到这里
 *
 * Created by nianyi.yang on 2016/9/13.
 */
public class AppUtil {

    //时间戳格式化类，这是为了避免重复实例化
    private static SimpleDateFormat mSimpleDataFormat_;

    /**
     * 输入文本的正则过滤，字母、数字、符号
     */
    public static final String REG_EX_INPUT_STRING = "[^a-zA-Z0-9`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？+-]";


    /**
     * @author shenen.gao
     *  @brief 设置View是否可用的显示，其实就是把显示的透明度设置成了0.5
     *  @param enbale void      
     *  @throws  
     * @date 2015-12-14 下午2:39:08
     *     
     */
    public static final void setViewEnableView(View view, boolean enabled) {

        if (view != null) {
            view.setAlpha(enabled ? 1.0F : 0.5F);
            view.setEnabled(enabled);
        }
    }

    /**
     * @return String
     * @brief 字符串校验
     * @date 2014-7-28 下午2:07:01
     * @author morong
     * Update Date :
     * Update Author : morong
     */
    public static String checkS(String string) {

        return StringUtil.checkString(string);
    }


    /**
     *
     * shenen.gao
     *
     * 把时间戳转换成字符串时间
     *
     * @param millisecond 时间戳
     * @param formatType yyyyMMdd
     * @return 格式时间
     */
    public static synchronized String timestampToYearMonthDate(long millisecond, String formatType) {

        if (formatType != null && !formatType.isEmpty()) {

            if (mSimpleDataFormat_ == null)
                mSimpleDataFormat_ = new SimpleDateFormat(formatType);
            else
                mSimpleDataFormat_.applyPattern(formatType);

            Date date = null;
            date = new Date();
            date.setTime(millisecond);
            return mSimpleDataFormat_.format(date);
        }

        return "";
    }
}

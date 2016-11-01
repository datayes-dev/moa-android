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
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public abstract class ArrayListAdapter<T, M> extends BaseAdapter {

    protected List<T> mList;
    protected Context mContext;

    //是否是CheckBox
    protected boolean isCheckBoxMode_ = false;

    //当前选择的变化
    protected OnCheckBoxSelectChanged selectChange_;

    //当前选中的数据列表
    protected List<T> selectedInfos = new ArrayList<T>();


    public ArrayListAdapter(Context context) {
        this.mContext = context;
    }


    @Override
    public int getCount() {
        if (mList != null)
            return mList.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int position) {
        return mList == null ? null : mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    protected abstract void getView(int position, View convertView, M m,
                                    ViewGroup parent);

    @SuppressWarnings("unchecked")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        M m;
        if (convertView == null) {
            convertView = getConvertView();
            m = holderChildView(convertView);
            // convertView = LinearLayout.inflate(mContext,resource, null);
            convertView.setTag(m);
        } else
            m = (M) convertView.getTag();
        getView(position, convertView, m, parent);
        return convertView;

    }

    /**
     * @author shenen.gao
     *  @brief 刷新单个Item
     *  @param index
     *  @param viewGroup void      
     *  @throws  
     * @date 2016-3-9 下午4:43:15
     *     
     */
    public void updateItem(int index, ListView viewGroup) {

        if (viewGroup != null && index >= 0) {

            int firstVisiblePos = viewGroup.getFirstVisiblePosition();
            int lastVisiblePos = viewGroup.getLastVisiblePosition();
            int headCount = viewGroup.getHeaderViewsCount();

            if (firstVisiblePos <= index && index <= lastVisiblePos) {

                View childView = viewGroup.getChildAt(index - firstVisiblePos + headCount);

                if (childView != null)
                    getView(index, childView, viewGroup);
            }
        }
    }

    public void setList(List<T> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    public void setListExpectNofify(List<T> list) {
        this.mList = list;
    }

    public List<T> getList() {
        return mList == null ? new ArrayList<T>() : mList;
    }

    public void setList(T[] list) {
        ArrayList<T> arrayList = new ArrayList<T>(list.length);
        for (T t : list) {
            arrayList.add(t);
        }
        setList(arrayList);
    }

    /**
     * @author shenen.gao
     *  @brief 设置成为CheckBox模式
     *  @param value void      
     *  @throws  
     * @date 2015-12-17 下午8:32:20
     *     
     */
    public void setCheckBoxMode(boolean value) {

        isCheckBoxMode_ = value;
        this.notifyDataSetChanged();

        selectedInfos.clear();
    }

    /**
     * @author shenen.gao
     *  @brief 获取当前选中的数据
     *  @back_return List<InfomationSimpleData>      
     *  @throws  
     * @date 2015-12-17 下午8:39:19
     *     
     */
    public List<T> getCurSelectInfos() {

        return selectedInfos;
    }

    /**
     * @author shenen.gao
     *  @brief 反选数据
     *   void      
     *  @throws  
     * @date 2015-12-17 下午8:54:59
     *     
     */
    public void Selectoppositly() {

        if (isCheckBoxMode_) {

            List<T> allList = getList();

            for (T data : allList) {

                if (selectedInfos.contains(data)) {

                    selectedInfos.remove(data);

                } else {

                    selectedInfos.add(data);
                }
            }

            this.notifyDataSetChanged();

            if (selectChange_ != null)
                selectChange_.onClicked();
        }
    }

    /**
     * @author shenen.gao
     *  @brief 全选
     *   void      
     *  @throws  
     * @date 2015-12-17 下午8:55:29
     *     
     */
    public void selectAll() {

        if (isCheckBoxMode_) {

            selectedInfos.clear();
            selectedInfos.addAll(getList());

            this.notifyDataSetChanged();

            if (selectChange_ != null)
                selectChange_.onClicked();
        }
    }

    /**
     * @author shenen.gao
     *  @brief 全不选
     *   void      
     *  @throws  
     * @date 2015-12-17 下午8:56:40
     *     
     */
    public void unSelectAll() {

        selectedInfos.clear();
        this.notifyDataSetChanged();

        if (selectChange_ != null)
            selectChange_.onClicked();
    }

    /**
     * @author shenen.gao
     *  @brief 删除已选中的数据
     *   void      
     *  @throws  
     * @date 2015-12-17 下午9:23:40
     *     
     */
    public boolean deleteSelected() {

        if (!selectedInfos.isEmpty()) {

            for (T data : selectedInfos) {

                remove(data);
            }

            selectedInfos.clear();
            this.notifyDataSetChanged();

            if (selectChange_ != null)
                selectChange_.onClicked();

            return true;
        }

        return false;
    }

    /**
     * @author shenen.gao
     *  @brief 删除的操作
     *  @param data void      
     *  @throws  
     * @date 2016-4-13 上午10:39:34
     *     
     */
    protected boolean remove(T data) {

        if (mList != null)
            return mList.remove(data);

        return false;
    }

    protected void add(int index, T data) {

        if (mList != null)
            mList.add(index, data);
    }

    /**
     * @author shenen.gao
     *  @brief 是否已全选
     *  @back_return boolean      
     *  @throws  
     * @date 2015-12-17 下午9:07:07
     *     
     */
    public boolean isAllSelected() {

        return (selectedInfos.size() == getList().size()) && !selectedInfos.isEmpty();
    }

    public void setSelectChanged(OnCheckBoxSelectChanged value) {
        selectChange_ = value;
    }

    /**
     * @author shenen.gao
     * @version 1.0
     * @brief 咨询点击之后的回掉
     * @date 2015-12-18 下午1:42:08
     */
    public interface OnCheckBoxSelectChanged {
        void onClicked();
    }

    //初始化item view
    protected abstract View getConvertView();

    protected abstract M holderChildView(View convertView);

}

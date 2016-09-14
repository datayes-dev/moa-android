package com.datayes.dyoa.module.swipecard.activity.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.datayes.dyoa.R;
import com.datayes.dyoa.common.network.bean.TransactionListBean;
import com.datayes.dyoa.common.view.ArrayListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by datayes on 16/9/13.
 */
public class TradeHistoryAdapter extends ArrayListAdapter<TransactionListBean.TransactionBean,TradeHistoryAdapter.ViewHolder> {

    public TradeHistoryAdapter(Context context) {
        super(context);
    }

    @Override
    protected void getView(int position, View convertView, ViewHolder viewHolder, ViewGroup parent) {
        TransactionListBean.TransactionBean bean =  getList().get(position);
        String shopName = bean.getRestaurant_name();
        String moneyValue = String.valueOf(bean.getPrice());
        String dateValue = bean.getTime_stamp();
        viewHolder.mTvShopName.setText(shopName);
        viewHolder.mTvMoney.setText(moneyValue);
        viewHolder.mTvDate.setText(dateValue);
    }

    @Override
    protected View getConvertView() {
        return View.inflate(mContext, R.layout.item_trade_history, null);
    }

    @Override
    protected ViewHolder holderChildView(View convertView) {
        ViewHolder t = new ViewHolder(convertView);
        return t;
    }

    static class ViewHolder {
        @BindView(R.id.tv_shop_name)
        TextView mTvShopName;
        @BindView(R.id.tv_money)
        TextView mTvMoney;
        @BindView(R.id.tv_date)
        TextView mTvDate;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

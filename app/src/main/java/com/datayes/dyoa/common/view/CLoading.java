package com.datayes.dyoa.common.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.datayes.dyoa.R;

/**
 * Created by nianyi.yang on 2016/9/14.
 */
public class CLoading extends RelativeLayout {

    private ViewGroup rootView;

    private TextView mMsg;

    private RelativeLayout mPopup;

    private Context context;

    private Boolean isShowing = false;

    private String msg = "";

    private Integer resId = null;

    public CLoading(Context context) {
        this(context, null, 0);
    }

    public CLoading(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CLoading(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.com_loading, this, true);

        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            rootView = (ViewGroup) activity.findViewById(android.R.id.content);

            mMsg = (TextView) this.findViewById(R.id.msg);
            mPopup = (RelativeLayout) this.findViewById(R.id.popup);
        }
    }

    public void show() {
        if (!isShowing) {
            if (TextUtils.isEmpty(msg)) {
                mMsg.setVisibility(View.GONE);
            } else {
                mMsg.setVisibility(View.VISIBLE);
                mMsg.setText(msg);
            }

            if (null != resId) {
                mPopup.setBackgroundResource(resId);
            }

            rootView.addView(this);
            isShowing = true;
        }

    }

    public void hide() {
        if (isShowing) {
            rootView.removeView(this);
            isShowing = false;
        }
    }

    /**
     * Builder for arguments
     */
    public static class Builder {
        private Context context;

        private String msg;

        private Integer resId;

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder setMessage(String msg) {
            this.msg = msg;
            return this;
        }

        public Builder setBackground(@DrawableRes Integer resId) {
            this.resId = resId;
            return this;
        }

        public CLoading build() {
            CLoading progressView = new CLoading(this.context);
            progressView.context = this.context;
            progressView.msg = this.msg;
            progressView.resId = this.resId;
            return progressView;
        }
    }
}

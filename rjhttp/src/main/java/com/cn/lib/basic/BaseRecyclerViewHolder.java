package com.cn.lib.basic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cn.lib.util.GlideUtil;



/**
 * Created by admin on 2016/4/1.
 */
public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {
    private Context context;
    private SparseArray<View> views;

    public BaseRecyclerViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        this.views = new SparseArray<View>();
    }


    protected <T extends View> T retrieveView(int viewId) {
        if (itemView == null) {
            return null;
        }
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public <T extends View> T getChildView(int viewId) {
        return retrieveView(viewId);
    }

    public BaseRecyclerViewHolder setTextView(int viewId, SpannableStringBuilder value) {
        TextView textView = retrieveView(viewId);
        if (textView != null) {
            textView.setText(value);
            textView.setMovementMethod(LinkMovementMethod.getInstance());
        }
        return this;
    }


    public BaseRecyclerViewHolder setTextView(int viewId, String value) {
        TextView textView = retrieveView(viewId);
        if (textView != null)
            textView.setText(value);
        return this;
    }

    public BaseRecyclerViewHolder setTextColorResource(int viewId, int textColorRes) {
        TextView textView = retrieveView(viewId);
        if (textView != null)
            textView.setTextColor(context.getResources().getColor(textColorRes));
        return this;
    }

    public BaseRecyclerViewHolder setBackgroundColor(int viewId, int color) {
        View view = retrieveView(viewId);
        if (view != null)
            view.setBackgroundColor(color);
        return this;
    }

    public BaseRecyclerViewHolder setImageUrl(int viewId, String imgUrl) {
        ImageView imageView = retrieveView(viewId);
        if (imageView != null) {
            GlideUtil.INSTANCE.loadImage(context, imgUrl, imageView, GlideUtil.GlideEnum.SMALL_IMAGE);
        }
        return this;
    }

    public BaseRecyclerViewHolder setBackgroundResource(int viewId, int resId) {
        View view = retrieveView(viewId);
        if (view != null)
            view.setBackgroundResource(resId);
        return this;
    }


    public BaseRecyclerViewHolder setFocusable(int viewId, boolean flag) {
        View view = retrieveView(viewId);
        if (view != null)
            view.setFocusable(flag);
        return this;
    }

    public BaseRecyclerViewHolder setClickable(int viewId, boolean flag) {
        View view = retrieveView(viewId);
        if (view != null)
            view.setClickable(flag);
        return this;
    }


    public BaseRecyclerViewHolder setVisible(int viewId, boolean visible) {
        View view = retrieveView(viewId);
        if (view != null)
            view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public BaseRecyclerViewHolder setEnabled(int viewId, boolean enabled) {
        View view = retrieveView(viewId);
        if (view != null)
            view.setEnabled(enabled);
        return this;
    }

    public BaseRecyclerViewHolder setImageResource(int viewId, int imageRes) {
        ImageView imageView = retrieveView(viewId);
        if (imageView != null)
            imageView.setImageResource(imageRes);
        return this;
    }

    public BaseRecyclerViewHolder setOnFocusChangeListener(int viewId, View.OnFocusChangeListener listener) {
        View view = retrieveView(viewId);
        if (view != null)
            view.setOnFocusChangeListener(listener);
        return this;
    }

    public BaseRecyclerViewHolder addTextChangedListener(int viewId, TextWatcher textWatcher) {
        TextView view = retrieveView(viewId);
        if (view != null)
            view.addTextChangedListener(textWatcher);
        return this;
    }

    public BaseRecyclerViewHolder setOnClickListener(int viewId, View.OnClickListener onClickListener) {
        View view = retrieveView(viewId);
        if (view != null)
            view.setOnClickListener(onClickListener);
        return this;
    }

}

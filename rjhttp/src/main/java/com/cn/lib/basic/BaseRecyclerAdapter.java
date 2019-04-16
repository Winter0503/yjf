package com.cn.lib.basic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by admin on 2016/4/1.
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {
    private static final int ITEM_TYPE_HEADER = 0x1000;
    private static final int ITEM_TYPE_FOOTER = 0x2000;
    protected Context context;
    protected List<T> data;
    private int layoutResId = -1;
    private int onClickPosition = -1;
    private boolean isCancelSelectedState;
    private OnItemClickListener<T> onItemClickListener;
    private LinearLayout mHeaderLayout;
    private LinearLayout mFooterLayout;

    public BaseRecyclerAdapter(Context context, int layoutResId, boolean isCancelSelectedState) {
        this.context = context;
        this.layoutResId = layoutResId;
        this.isCancelSelectedState = isCancelSelectedState;
    }

    public BaseRecyclerAdapter(Context context, int layoutResId) {
        this(context, layoutResId, false);
    }

    public void setNewList(List<T> data) {
        this.data = data == null ? new ArrayList<T>() : new ArrayList<T>(data);
        notifyDataSetChanged();
    }

    public void setExtendList(List<? extends T> data) {
        this.data = data == null ? new ArrayList<T>() : new ArrayList<T>(data);
        notifyDataSetChanged();
    }

    public void addAll(List<T> data) {
        if (this.data == null) {
            this.data = new ArrayList<T>();
        }
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void set(T oldElem, T newElem) {
        if (oldElem == null || newElem == null) {
            return;
        }
        set(data.indexOf(oldElem), newElem);
    }

    public void set(int indexOf, T newElem) {
        if (data == null || newElem == null) return;
        if (data.size() < 1) {
            data.add(newElem);
        } else {
            data.set(indexOf, newElem);
        }
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        if (data != null && data.size() > 0 && data.size() > position) {
            return data.get(position);
        } else
            return null;
    }

    public void removeItem(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    public void removeItem(T item) {

        removeItem(data.indexOf(item));
    }


    public List<T> getData() {
        return data == null ? this.data = new ArrayList<T>() : data;
    }

    /**
     * @return 除去头布局和脚布局长度的真实数据长度
     */
    private int getRealItemCount() {
        return data == null ? 0 : data.size();
    }


    /**
     * 插入元素操作
     */
    public void insertItem(int position, T newElem) {
        if (data == null || newElem == null) return;
        data.add(position, newElem);
        notifyItemInserted(position);
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutResId <= 0) {
            return null;
        }
        if (viewType == ITEM_TYPE_HEADER) {
            return new HeadHolder(mHeaderLayout, context);
        }
        if (viewType == ITEM_TYPE_FOOTER) {
            return new FooterHolder(mFooterLayout, context);
        }
        View view = LayoutInflater.from(context).inflate(layoutResId, parent, false);
        return new BaseRecyclerViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(final BaseRecyclerViewHolder holder, int position) {
        if (isHeaderViewPos(position)) {
            return;
        }
        if (isFooterViewPos(position)) {
            return;
        }
        //除去头部的真实数据位置
        final int realPosition = position - getHeaderLayoutCount();
        convertView(holder, data.get(realPosition), realPosition);
        if (holder.itemView != null && onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickPosition != realPosition) {
                        onClickPosition = realPosition;
                        notifyDataSetChanged();
                        onItemClickListener.onItemClick(onClickPosition, data.get(realPosition));
                    } else if (isCancelSelectedState) {
                        onClickPosition = -1;
                        notifyDataSetChanged();
                        onItemClickListener.onItemClick(onClickPosition, data.get(realPosition));
                    }
                }
            });
        }
    }

    private boolean isHeaderViewPos(int position) {
        return position < getHeaderLayoutCount();
    }

    private boolean isFooterViewPos(int position) {
        return position >= getHeaderLayoutCount() + getRealItemCount();
    }

    public int addHeaderView(View header) {
        return this.addHeaderView(header, -1);
    }

    public int addHeaderView(View header, int index) {
        return addHeaderView(header, index, LinearLayout.VERTICAL);
    }

    public int addHeaderView(View header, int index, int orientation) {
        if (mHeaderLayout == null) {
            mHeaderLayout = new LinearLayout(header.getContext());
            if (orientation == LinearLayout.VERTICAL) {
                mHeaderLayout.setOrientation(LinearLayout.VERTICAL);
                mHeaderLayout.setLayoutParams(new RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
            } else {
                mHeaderLayout.setOrientation(LinearLayout.HORIZONTAL);
                mHeaderLayout.setLayoutParams(new RecyclerView.LayoutParams(WRAP_CONTENT, MATCH_PARENT));
            }
        }
        final int childCount = mHeaderLayout.getChildCount();
        if (index < 0 || index > childCount) {
            index = childCount;
        }
        mHeaderLayout.addView(header, index);
        return index;
    }

    public int addFooterView(View footer) {
        return addFooterView(footer, -1, LinearLayout.VERTICAL);
    }

    public int addFooterView(View footer, int index) {
        return addFooterView(footer, index, LinearLayout.VERTICAL);
    }

    public int addFooterView(View footer, int index, int orientation) {
        if (mFooterLayout == null) {
            mFooterLayout = new LinearLayout(footer.getContext());
            if (orientation == LinearLayout.VERTICAL) {
                mFooterLayout.setOrientation(LinearLayout.VERTICAL);
                mFooterLayout.setLayoutParams(new RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
            } else {
                mFooterLayout.setOrientation(LinearLayout.HORIZONTAL);
                mFooterLayout.setLayoutParams(new RecyclerView.LayoutParams(WRAP_CONTENT, MATCH_PARENT));
            }
        }
        final int childCount = mFooterLayout.getChildCount();
        if (index < 0 || index > childCount) {
            index = childCount;
        }
        mFooterLayout.addView(footer, index);
        if (mFooterLayout.getChildCount() == 1) {
            int position = getFooterViewPosition();
            if (position != -1) {
                notifyItemInserted(position);
            }
        }
        return index;
    }

    /*根据位置来返回不同的item类型*/
    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPos(position)) {
            return ITEM_TYPE_HEADER;
        } else if (isFooterViewPos(position)) {
            return ITEM_TYPE_FOOTER;
        }
        return position - getHeaderLayoutCount();
    }

    @Override
    public int getItemCount() {
        return getHeaderLayoutCount() + getFootersCount() + getRealItemCount();
    }

    public int getHeaderLayoutCount() {
        if (mHeaderLayout == null || mHeaderLayout.getChildCount() == 0) {
            return 0;
        }
        return 1;
    }

    public int getFootersCount() {
        if (mFooterLayout == null || mFooterLayout.getChildCount() == 0) {
            return 0;
        }
        return 1;
    }

    /**
     * 实现逻辑代码
     *
     * @param holder
     * @param t
     * @param realPosition
     */
    public abstract void convertView(BaseRecyclerViewHolder holder, T t, int realPosition);

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setDefaultSelectPosition(int onClickPosition) {
        this.onClickPosition = onClickPosition;
    }

    public int getFooterViewPosition() {
        return getHeaderLayoutCount() + data.size();
    }

    public interface OnItemClickListener<T> {
        void onItemClick(int position, T obj);
    }

    private class HeadHolder extends BaseRecyclerViewHolder {

        private HeadHolder(View itemView, Context context) {
            super(itemView, context);
        }
    }

    private class FooterHolder extends BaseRecyclerViewHolder {

        private FooterHolder(View itemView, Context context) {
            super(itemView, context);
        }
    }
}

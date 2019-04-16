package com.cn.lib.basic;

import android.content.Context;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/4/1.
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {
    protected static final int ITEM_TYPE_HEADER = 0x1000;
    protected static final int ITEM_TYPE_FOOTER = 0x2000;
    protected Context context;
    protected List<T> data;
    protected int layoutResId = -1;
    protected int onClickPosition = -1;
    protected boolean isCancelSelectedState;
    protected OnItemClickListener<T> onItemClickListener;
    protected SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    protected SparseArrayCompat<View> mFootViews = new SparseArrayCompat<>();

    public BaseRecyclerAdapter(Context context, int layoutResId, boolean isCancelSelectedState) {
        this.context = context;
        this.layoutResId = layoutResId;
        this.isCancelSelectedState = isCancelSelectedState;
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
     *
     * @param position
     * @param newElem
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
            return new HeadHolder(mHeaderViews.get(viewType), context);
        }
        if (viewType == ITEM_TYPE_FOOTER) {
            return new FooterHolder(mFootViews.get(viewType), context);
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
        final int realPosition = position - getHeadersCount();
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
        return position < getHeadersCount();
    }

    private boolean isFooterViewPos(int position) {
        return position >= getHeadersCount() + getRealItemCount();
    }

    public void addHeaderView(View view) {
        mHeaderViews.put(mHeaderViews.size() + ITEM_TYPE_HEADER, view);
    }

    public void addFootView(View view) {
        mFootViews.put(mFootViews.size() + ITEM_TYPE_FOOTER, view);
    }

    /*根据位置来返回不同的item类型*/
    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPos(position)) {
            return mHeaderViews.keyAt(position);
        } else if (isFooterViewPos(position)) {
            return mFootViews.keyAt(position - getHeadersCount() - getRealItemCount());
        }
        return position - getHeadersCount();
    }

    @Override
    public int getItemCount() {
        return getHeadersCount() + getFootersCount() + getRealItemCount();
    }

    public int getHeadersCount() {
        return mHeaderViews.size();
    }

    public int getFootersCount() {
        return mFootViews.size();
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

    public interface OnItemClickListener<T> {
        void onItemClick(int position, T obj);
    }

    protected class HeadHolder extends BaseRecyclerViewHolder {


        public HeadHolder(View itemView, Context context) {
            super(itemView, context);
        }
    }

    protected class FooterHolder extends BaseRecyclerViewHolder {


        public FooterHolder(View itemView, Context context) {
            super(itemView, context);
        }
    }
}

package com.cn.lib.basic

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import java.util.ArrayList

import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT

/**
 * Created by admin on 2016/4/1.
 */
abstract class BaseRecyclerAdapter<T> @JvmOverloads constructor(protected var context: Context, private val layoutResId: Int, private val isCancelSelectedState: Boolean = false) : RecyclerView.Adapter<BaseRecyclerViewHolder>() {
    protected var data: MutableList<T> = mutableListOf()
    private var onClickPosition = -1
    private var onItemClickListener: OnItemClickListener<T>? = null
    private val mHeaderLayout: LinearLayout by lazy {
        LinearLayout(context)
    }
    private val mFooterLayout: LinearLayout by lazy {
        LinearLayout(context)
    }

    /**
     * @return 除去头布局和脚布局长度的真实数据长度
     */
    private fun getRealItemCount(): Int {
        return data?.size ?: 0
    }

    fun getHeaderLayoutCount(): Int {
        return if (mHeaderLayout.childCount == 0) 0 else 1
    }

    fun getFooterLayoutCount(): Int {
        return if (mFooterLayout.childCount == 0) 0 else 1
    }

    fun getFooterViewPosition(): Int {
        return getHeaderLayoutCount() + data.size
    }

    fun setNewList(data: MutableList<T>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun addAll(data: List<T>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun clear() {
        this.data.clear()
        notifyDataSetChanged()
    }

    operator fun set(oldElem: T, newElem: T) {
        val index = data.indexOf(oldElem)
        set(index, newElem)
    }

    operator fun set(indexOf: Int = -1, newElem: T) {
        if (indexOf > 0) {
            this.data[indexOf] = newElem
        } else {
            this.data.add(newElem)
        }
        notifyDataSetChanged()
    }

    fun getItem(position: Int): T? {
        return data.let {
            if (it.size > position)
                it[position]
            else
                null
        }
    }

    fun removeItem(position: Int = -1) {
        if (position < data.size && position > -1) {
            this.data.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun removeItem(item: T) {
        val index = this.data.indexOf(item)
        if (index > -1) {
            removeItem(index)
        }
    }

    fun getList(): MutableList<T> {
        return data
    }

    /**
     * 插入元素操作
     */
    fun insertItem(position: Int, newElem: T) {
        this.data.add(position, newElem)
        notifyItemInserted(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder {
        if (layoutResId <= 0) {
            throw RuntimeException("layout resource id is null")
        }
        if (viewType == ITEM_TYPE_HEADER) {
            return HeadHolder(mHeaderLayout, context)
        }
        if (viewType == ITEM_TYPE_FOOTER) {
            return FooterHolder(mFooterLayout, context)
        }
        val view = LayoutInflater.from(context).inflate(layoutResId, parent, false)
        return BaseRecyclerViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: BaseRecyclerViewHolder, position: Int) {
        if (isHeaderViewPos(position)) {
            return
        }
        if (isFooterViewPos(position)) {
            return
        }
        //除去头部的真实数据位置
        val realPosition = position - getHeaderLayoutCount()
        convertView(holder, data[realPosition], realPosition)
        if (holder.itemView != null && onItemClickListener != null) {
            holder.itemView.setOnClickListener {
                if (onClickPosition != realPosition) {
                    onClickPosition = realPosition
                    notifyDataSetChanged()
                    onItemClickListener?.onItemClick(onClickPosition, data[realPosition])
                } else if (isCancelSelectedState) {
                    onClickPosition = -1
                    notifyDataSetChanged()
                    onItemClickListener?.onItemClick(onClickPosition, data[realPosition])
                }
            }
        }
    }

    private fun isHeaderViewPos(position: Int): Boolean {
        return position < getHeaderLayoutCount()
    }

    private fun isFooterViewPos(position: Int): Boolean {
        return position >= getHeaderLayoutCount() + getRealItemCount()
    }

    fun addHeaderView(header: View): Int {
        return this.addHeaderView(header, -1)
    }

    private fun addHeaderView(header: View, index: Int, orientation: Int = LinearLayout.VERTICAL): Int {
        var addIndex = index
        if (orientation == LinearLayout.VERTICAL) {
            mHeaderLayout.orientation = LinearLayout.VERTICAL
            mHeaderLayout.layoutParams = RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        } else {
            mHeaderLayout.orientation = LinearLayout.HORIZONTAL
            mHeaderLayout.layoutParams = RecyclerView.LayoutParams(WRAP_CONTENT, MATCH_PARENT)
        }
        val childCount = mHeaderLayout.childCount
        if (addIndex < 0 || addIndex > childCount) {
            addIndex = childCount
        }
        mHeaderLayout.addView(header, addIndex)
        return addIndex
    }

    @JvmOverloads
    fun addFooterView(footer: View, index: Int = -1, orientation: Int = LinearLayout.VERTICAL): Int {
        var addIndex = index
        if (orientation == LinearLayout.VERTICAL) {
            mFooterLayout.orientation = LinearLayout.VERTICAL
            mFooterLayout.layoutParams = RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        } else {
            mFooterLayout.orientation = LinearLayout.HORIZONTAL
            mFooterLayout.layoutParams = RecyclerView.LayoutParams(WRAP_CONTENT, MATCH_PARENT)
        }
        val childCount = mFooterLayout.childCount
        if (addIndex < 0 || addIndex > childCount) {
            addIndex = childCount
        }
        mFooterLayout.addView(footer, addIndex)
        if (mFooterLayout.childCount == 1) {
            val position = getFooterViewPosition()
            if (position != -1) {
                notifyItemInserted(position)
            }
        }
        return addIndex
    }

    fun clearFooterViews() {
        mFooterLayout.removeAllViews()
    }

    fun clearHeaderViews() {
        mHeaderLayout.removeAllViews()

    }

    /*根据位置来返回不同的item类型*/
    override fun getItemViewType(position: Int): Int {
        if (isHeaderViewPos(position)) {
            return ITEM_TYPE_HEADER
        } else if (isFooterViewPos(position)) {
            return ITEM_TYPE_FOOTER
        }
        return position - getHeaderLayoutCount()
    }

    override fun getItemCount(): Int {
        return getHeaderLayoutCount() + getFooterLayoutCount() + getRealItemCount()
    }

    /**
     * 实现逻辑代码
     *
     * @param holder
     * @param t
     * @param realPosition
     */
    abstract fun convertView(holder: BaseRecyclerViewHolder, t: T, realPosition: Int)

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener<T>) {
        this.onItemClickListener = onItemClickListener
    }

    fun setDefaultSelectPosition(onClickPosition: Int) {
        this.onClickPosition = onClickPosition
    }

    interface OnItemClickListener<T> {
        fun onItemClick(position: Int, obj: T)
    }

    private inner class HeadHolder(itemView: LinearLayout?, context: Context) : BaseRecyclerViewHolder(itemView, context)

    private inner class FooterHolder(itemView: View?, context: Context) : BaseRecyclerViewHolder(itemView, context)

    companion object {
        private val ITEM_TYPE_HEADER = 0x1000
        private val ITEM_TYPE_FOOTER = 0x2000
    }
}

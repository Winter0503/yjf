package cn.ygyg.yjf.dialog

import android.app.Dialog
import android.content.Context
import android.text.TextUtils
import android.util.ArrayMap
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView

import com.cn.lib.util.CommonUtil

import cn.ygyg.yjf.R
import cn.ygyg.yjf.utils.StringUtil


class DefaultPromptDialog private constructor(context: Context, private val titleStr: String, private val affirmStr: String, private val affirmColor: Int, private val cancelStr: String, private val contentStr: String, contentEnabled: Boolean, typeEnum: TypeEnum, private val dialogButtonListener: PromptDialogButtonListener?) : View.OnClickListener {

    private var tagMap: ArrayMap<Int, Any>? = null
    private val contentEnabled = true
    private val typeEnum = TypeEnum.BUTTON_HORIZONTAL


    private val dialog: Dialog?

    enum class TypeEnum {
        BUTTON_VERTICAL, BUTTON_HORIZONTAL
    }

    init {
        this.contentEnabled = contentEnabled
        this.typeEnum = typeEnum

        dialog = Dialog(context, R.style.prompt_dialog_style)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        // 加载自定义Dialog布局
        val inflater = LayoutInflater.from(context)
        var view: View? = null
        if (this.typeEnum == TypeEnum.BUTTON_HORIZONTAL) {
            view = inflater.inflate(R.layout.layout_prompt_dialog_horizontal, null, false)
        } else if (this.typeEnum == TypeEnum.BUTTON_VERTICAL) {
            view = inflater.inflate(R.layout.layout_prompt_dialog_vertical, null, false)
        }
        if (view != null) {
            dialog.setContentView(view)
        }
        if (this.typeEnum == TypeEnum.BUTTON_VERTICAL) {
            val dialogWindow = dialog.window
            if (dialogWindow != null) {
                dialogWindow.setGravity(Gravity.BOTTOM) //可设置dialog的位置
                dialogWindow.decorView.setPadding(0, 0, 0, 0) //消除边距

                val dm = DisplayMetrics()
                val manager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                manager?.defaultDisplay?.getMetrics(dm)
                val wlp = dialogWindow.attributes
                wlp.width = dm.widthPixels
                wlp.horizontalMargin = 10f
                wlp.flags = wlp.flags or WindowManager.LayoutParams.FLAG_DIM_BEHIND
                dialogWindow.attributes = wlp
            }

        }
        initViews()
    }


    private fun initViews() {
        val title = dialog!!.findViewById<TextView>(R.id.dialog_hint_title)
        val content = dialog.findViewById<TextView>(R.id.dialog_hint_content)
        val affirm = dialog.findViewById<TextView>(R.id.dialog_hint_affirm)
        val cancel = dialog.findViewById<TextView>(R.id.dialog_hint_cancel)
        val titleLayout = dialog.findViewById<View>(R.id.dialog_title_layout)
        if (StringUtil.isEmpty(titleStr)) {
            titleLayout.visibility = View.GONE
        }
        title.text = titleStr
        affirm.text = affirmStr
        if (affirmColor > 0) affirm.setTextColor(affirmColor)
        cancel.visibility = if (TextUtils.isEmpty(cancelStr)) View.GONE else View.VISIBLE
        dialog.findViewById<View>(R.id.view2).visibility = if (TextUtils.isEmpty(cancelStr)) View.GONE else View.VISIBLE
        cancel.text = cancelStr
        if (TextUtils.isEmpty(contentStr)) {
            content.visibility = View.GONE
        }
        content.text = contentStr
        content.isEnabled = contentEnabled
        if (contentEnabled) {
            content.setTextColor(affirm.textColors)
            content.setBackgroundResource(R.drawable.dialog_content_btn_selector)
        }
        affirm.setOnClickListener(this)
        cancel.setOnClickListener(this)
        content.setOnClickListener(this)
    }

    fun getTag(key: Int): Any {
        return tagMap!![key]
    }

    fun setTag(key: Int, tag: Any) {
        if (this.tagMap == null) {
            tagMap = ArrayMap()
        }
        tagMap!!.put(key, tag)
    }

    override fun onClick(v: View) {
        if (dialogButtonListener == null && CommonUtil.isFastDoubleClick) {
            return
        }
        when (v.id) {
            R.id.dialog_hint_content -> if (dialogButtonListener!!.clickContentButton(this)) {
                this.dismiss()
            }
            R.id.dialog_hint_cancel -> if (dialogButtonListener!!.clickNegativeButton(this)) {
                this.dismiss()
            }
            else -> if (dialogButtonListener!!.clickPositiveButton(this)) {
                this.dismiss()
            }
        }
    }

    fun show() {
        if (dialog != null && !dialog.isShowing) {
            dialog.show()
        }
    }

    fun dismiss() {
        if (dialog != null && dialog.isShowing) {
            dialog.dismiss()
        }
    }

    class Builder {
        private var title: String? = null
        private var content: String? = null
        private var cancel: String? = null
        private var affirm: String? = null
        private var affirmColor: Int = 0
        private var contentEnabled: Boolean = false
        private var mContext: Context? = null
        private var dialogButtonListener: PromptDialogButtonListener? = null
        private var typeEnum: TypeEnum? = TypeEnum.BUTTON_HORIZONTAL


        /**
         * 提示标题文本
         */
        fun setTitleText(title: String): DefaultPromptDialog.Builder {
            this.title = title
            return this
        }

        /**
         * 提示语文本(竖向时可以作为按钮使用)
         */
        fun setContentText(content: String): DefaultPromptDialog.Builder {
            this.content = content
            return this
        }

        /**
         * 右侧(第二个)按钮文本
         */
        fun setAffirmText(affirm: String): DefaultPromptDialog.Builder {
            this.affirm = affirm
            return this
        }

        /**
         * 右侧(第二个)按钮文本
         */
        fun setAffirmTextColor(color: Int): DefaultPromptDialog.Builder {
            this.affirmColor = color
            return this
        }

        /**
         * 左侧(第三个)按钮文本
         */
        fun setCancelText(cancel: String): DefaultPromptDialog.Builder {
            this.cancel = cancel
            return this
        }

        /**
         * 设置第一个文本是否可点击
         */
        fun setContentEnabled(contentEnabled: Boolean): DefaultPromptDialog.Builder {
            this.contentEnabled = contentEnabled
            return this
        }

        /**
         * Context
         */
        fun setContext(mContext: Context): DefaultPromptDialog.Builder {
            this.mContext = mContext
            return this
        }

        /**
         * 选择按钮布局方向
         *
         *
         * PromptDialogFragment.BUTTON_VERTICAL表示按钮布局纵向排列
         *
         *
         * PromptDialogFragment.BUTTON_HORIZONTAL表示按钮布局横向排列
         */
        fun setButtonOrientation(typeEnum: TypeEnum?): DefaultPromptDialog.Builder {
            this.typeEnum = typeEnum
            if (typeEnum == null) {
                this.typeEnum = TypeEnum.BUTTON_HORIZONTAL
            }

            return this
        }

        /**
         * 设置两个按钮的监听
         *
         * @param dialogButtonListener [PromptDialogButtonListener]
         */
        fun onPromptDialogButtonListener(dialogButtonListener: PromptDialogButtonListener): DefaultPromptDialog.Builder {
            this.dialogButtonListener = dialogButtonListener
            return this
        }

        fun build(): DefaultPromptDialog {
            return DefaultPromptDialog(mContext, title, affirm, affirmColor, cancel, content, contentEnabled,
                    typeEnum, dialogButtonListener)
        }

    }


    interface PromptDialogButtonListener {
        /**
         * 当布局为竖向且内容第一个按钮可点击时会调用该方法
         *
         * @return 返回true表示关闭对话框
         */
        fun clickContentButton(dialog: DefaultPromptDialog): Boolean

        /**
         * 右侧(第一个)按钮
         *
         * @return 返回true表示关闭对话框
         */
        fun clickPositiveButton(dialog: DefaultPromptDialog): Boolean

        /**
         * 左侧(第一个)按钮
         *
         * @return 返回true表示关闭对话框
         */
        fun clickNegativeButton(dialog: DefaultPromptDialog): Boolean
    }

    class DefaultPromptDialogButtonListener : DefaultPromptDialog.PromptDialogButtonListener {

        override fun clickContentButton(dialog: DefaultPromptDialog): Boolean {
            return true
        }

        override fun clickPositiveButton(dialog: DefaultPromptDialog): Boolean {
            return true
        }

        override fun clickNegativeButton(dialog: DefaultPromptDialog): Boolean {
            return true
        }
    }

    companion object {

        fun builder(): Builder {
            return Builder()
        }
    }

}

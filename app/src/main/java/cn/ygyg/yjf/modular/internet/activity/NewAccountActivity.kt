package cn.ygyg.yjf.modular.internet.activity

import android.view.View
import cn.ygyg.yjf.R
import cn.ygyg.yjf.modular.internet.contract.NewAccountActivityContract
import cn.ygyg.yjf.modular.internet.helper.ConfirmAccountPopupWindow
import cn.ygyg.yjf.modular.internet.helper.InquireAccountDialog
import cn.ygyg.yjf.modular.internet.presenter.NewAccountActivityPresenter
import cn.ygyg.yjf.utils.HeaderBuilder
import com.cn.lib.basic.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_new_account.*

class NewAccountActivity : BaseMvpActivity<NewAccountActivityContract.Presenter, NewAccountActivityContract.View>(),
        NewAccountActivityContract.View {
    override fun createPresenter(): NewAccountActivityContract.Presenter {
        return NewAccountActivityPresenter(this)
    }

    private val headerBuilder: HeaderBuilder by lazy { HeaderBuilder(this) }
    private val popupWindow: ConfirmAccountPopupWindow by lazy {
        ConfirmAccountPopupWindow(this).apply {
            setOnConformClick(View.OnClickListener { toActivity(NewAccountSuccessActivity::class.java) })
        }
    }
    private val dialog: InquireAccountDialog by lazy { InquireAccountDialog(this) }

    override fun getContentViewResId(): Int {
        return R.layout.activity_new_account
    }

    override fun initViews() {
        headerBuilder.setLeftImageRes(R.mipmap.back)
        headerBuilder.setTitle(R.string.activity_title_add_new_account)
    }

    override fun initListener() {
        next_step.setOnClickListener { v -> popupWindow.show(v) }
        input_account_help.setOnClickListener { dialog.show() }
    }
}

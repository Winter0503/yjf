package cn.ygyg.cloudpayment.modular.my.fragment

import android.content.Intent
import android.net.Uri
import android.view.View
import cn.ygyg.cloudpayment.R
import cn.ygyg.cloudpayment.dialog.DefaultPromptDialog
import cn.ygyg.cloudpayment.modular.login.activity.LoginActivity
import cn.ygyg.cloudpayment.modular.login.entity.LoginEntity
import cn.ygyg.cloudpayment.modular.my.contract.MyContract
import cn.ygyg.cloudpayment.modular.my.presenter.MyPresenter
import cn.ygyg.cloudpayment.modular.payments.activity.PaymentsHistoryActivity
import com.cn.lib.basic.BaseMvpFragment
import com.cn.lib.util.ActivityListUtil
import kotlinx.android.synthetic.main.fragment_my.*


class MyFragment : BaseMvpFragment<MyContract.Presenter, MyContract.View>(), MyContract.View {

    override fun createPresenter(): MyContract.Presenter = MyPresenter(this)

    override val contentViewResId: Int = R.layout.fragment_my


    override fun initViews(v: View) {

    }

    override fun initListener(v: View) {
        btn_recharge_history.setOnClickListener {
            toActivity(PaymentsHistoryActivity::class.java)
        }
        btn_customer_service.setOnClickListener {
            DefaultPromptDialog.builder()
                    .setContext(getViewContext())
                    .setAffirmText("呼叫")
                    .setCancelText("取消")
                    .setContentText("0317-20725341")
                    .onPromptDialogButtonListener(object : DefaultPromptDialog.DefaultPromptDialogButtonListener() {
                        override fun clickPositiveButton(dialog: DefaultPromptDialog): Boolean {
                            val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:0317-20725341"))
                            startActivity(dialIntent)
                            return super.clickPositiveButton(dialog)
                        }
                    })
                    .build()
                    .show()
        }
        btn_logout.setOnClickListener {
            DefaultPromptDialog.builder()
                    .setContext(getViewContext())
                    .setAffirmText("确认")
                    .setCancelText("取消")
                    .setContentText("确认退出吗？")
                    .setButtonOrientation(DefaultPromptDialog.TypeEnum.BUTTON_VERTICAL)
                    .onPromptDialogButtonListener(object : DefaultPromptDialog.DefaultPromptDialogButtonListener() {
                        override fun clickPositiveButton(dialog: DefaultPromptDialog): Boolean {
                            mPresenter?.logout()
                            return super.clickPositiveButton(dialog)
                        }
                    })
                    .build()
                    .show()
        }
    }

    override fun loaderData() {
        mPresenter?.loaderPageData()
    }

    override fun logoutSuccess() {
        toActivity(LoginActivity::class.java)
        ActivityListUtil.INSTANCE.finishAllActivity(false)
    }

    override fun loaderPageDataSuccess(entity: LoginEntity?) {
        entity?.let {
            tv_phone.text = entity.cellPhone
        }
        setHasLoadedOnce(true)
    }

}

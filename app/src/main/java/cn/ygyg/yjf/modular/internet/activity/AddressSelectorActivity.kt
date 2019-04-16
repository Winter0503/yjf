package cn.ygyg.yjf.modular.internet.activity

import cn.ygyg.yjf.R
import cn.ygyg.yjf.modular.internet.contract.AddressSelectorActivityContract
import cn.ygyg.yjf.modular.internet.presenter.AddressSelectorActivityPresenter
import cn.ygyg.yjf.utils.HeaderBuilder
import com.cn.lib.basic.BaseMvpActivity

class AddressSelectorActivity :
        BaseMvpActivity<AddressSelectorActivityContract.Presenter, AddressSelectorActivityContract.View>(),
        AddressSelectorActivityContract.View {
    override fun getContentViewResId(): Int = R.layout.activity_address_selector

    override fun createPresenter(): AddressSelectorActivityContract.Presenter = AddressSelectorActivityPresenter(this)

    override fun initViews() {
        HeaderBuilder(this).apply {
            setTitle(R.string.activity_title_address_select)
            setLeftImageRes(R.mipmap.back)
        }
    }
}

package cn.ygyg.yjf.modular.record.presenter

import cn.ygyg.yjf.modular.record.contract.RechargeRecordContract
import com.cn.lib.basic.BasePresenterImpl

/**
 * Created by Admin on 2019/4/17.
 */
class RechargeRecordPresenter(view: RechargeRecordContract.View):BasePresenterImpl<RechargeRecordContract.View>(view),RechargeRecordContract.Presenter {
}
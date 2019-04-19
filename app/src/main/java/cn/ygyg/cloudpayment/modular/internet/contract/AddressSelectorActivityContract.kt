package cn.ygyg.cloudpayment.modular.internet.contract

import android.util.ArrayMap
import cn.ygyg.cloudpayment.modular.internet.entity.AddressCityEntity
import com.cn.lib.basic.IBasePresenter
import com.cn.lib.basic.IBaseView

class AddressSelectorActivityContract {
    interface View : IBaseView {
        /**
         * 加载城市列表
         */
        fun onLoadCityListSuccess(response: ArrayList<AddressCityEntity>, titlePositionMap: ArrayMap<String, Int>)
    }

    interface Presenter : IBasePresenter<View> {
        /**
         * 加载城市列表
         */
        fun loadCityList()
    }
}

package cn.ygyg.yjf.modular.contract

import com.cn.lib.basic.IBasePresenter
import com.cn.lib.basic.IBaseView

/**
 * Created by Admin on 2019/4/13.
 */
class LoginContract{
    interface View: IBaseView{

    }

    interface Presenter: IBasePresenter<View>{

    }
}
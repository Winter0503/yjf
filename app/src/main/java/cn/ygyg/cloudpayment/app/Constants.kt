package cn.ygyg.cloudpayment.app

import cn.ygyg.cloudpayment.BuildConfig

object Constants {
    //7a8810e4f048e7fca97d7beb1ee6cef4
    object IntentKey {
        const val IS_SUCCESS = "isSuccess"
        const val FOR_RESULT = "forResult"
        const val USER_INFO = "userInfo"
        const val IS_LOGIN = "isLogin"
        const val DEVICE_CODE="deviceCode"
    }

    object WX {
        const val WEIXIN_APP_ID: String = "wx14581fdc873a1818"
        const val COMPANY_CODE:String = BuildConfig.COMPANY_CODE
    }
}

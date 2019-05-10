package cn.ygyg.cloudpayment.app

object Constants {
    const val DEF_WEI_XIN_APP_ID: String = "wx14581fdc873a1818"

    //7a8810e4f048e7fca97d7beb1ee6cef4
    object IntentKey {
        const val IS_SUCCESS = "isSuccess"
        const val AMOUNT = "amount"
        const val FOR_RESULT = "forResult"
        const val USER_INFO = "userInfo"
        const val IS_LOGIN = "isLogin"
        const val DEVICE_CODE = "deviceCode"
        const val CONTRACT_CODE = "contractCode"
        const val COMPANY_KEY = "companyCode"
        const val TOKEN_KEY = "Authorization"
        const val OPEN_ID = "openId"

    }

    enum class PaymentMethod(private val type: String) {
        ALI_PAY("A"), WX_PAY("Q");

        fun string(): String {
            return type
        }
    }
}

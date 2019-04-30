package cn.ygyg.cloudpayment.api

/**
 * Created by Admin on 2019/4/18.
 */
object UrlConstants {
    /**
     * 验证手机
     */
    const val valPhone = "customerApi/user/valPhone"
    /**
     * 获取验证码
     */
    const val captcha = "customerApi/user/captcha"
    /**
     * 注册
     */
    const val register = "customerApi/user/register"
    /**
     * 账号密码登录
     */
    const val login = "customerApi/user/login"
    /**
     * 验证码登录
     */
    const val captchalogin = "customerApi/user/captchaLogin"
    /**
     * 忘记密码修改密码
     */
    const val forgetPwd = "customerApi/user/forgetPwd"

    /**
     * 城市列表
     */
    const val cityList = "customerApi/city/list"

    /**
     * 城市公司列表
     */
    const val companyList = "customerApi/company/list"

    /**
     * 获取用户信息
     */
    const val getMemberInfo = "customerApi/openid/queryMember"
    /**
     * 微信登录获取openId
     */
    const val getToken = "customerApi/openid/getToken"
    /**
     * 绑定手机号码
     */
    const val bindPhone = "customerApi/device/bindPhone"

    /**
     * 获取物联网表 信息
     */
    const val getDevice = "customerApi/device/getDevice"
    /**
     * 绑定物联网表
     */
    const val bindDevice = "customerApi/device/bindMeter"
    /**
     * 绑定物联网表 列表
     */
    const val deviceList = "customerApi/device/list"

    /**
     * 解除绑定物联网表
     */
    const val unbind = "customerApi/device/unbind"
    /**
     * 解除绑定物联网表
     */
    const val createOrder = "api/customer/payment/createOrder"
    /**
     * 缴费
     */
    const val rechargeQuery = "api/customer/payment/rechargeQuery"

    /**
     * 刷新Token
     */
    const val refreshToken = ""
    /**
     * 获取厂家配置
     */
    const val getPaymentInformation = "customerApi/payment/getPaymentInformation"


}
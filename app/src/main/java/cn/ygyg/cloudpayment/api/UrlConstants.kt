package cn.ygyg.cloudpayment.api

/**
 * Created by Admin on 2019/4/18.
 */
object UrlConstants {
    /**
     * 验证手机
     */
    const val valPhone = "chaoscloud-customerapi/customerapi/user/valPhone"
    /**
     * 获取验证码
     */
    const val captcha = "chaoscloud-customerapi/customerapi/user/captcha"
    /**
     * 注册
     */
    const val register = "chaoscloud-customerapi/customerapi/user/register"
    /**
     * 账号密码登录
     */
    const val login = "chaoscloud-customerapi/customerapi/user/login"
    /**
     * 验证码登录
     */
    const val captchalogin = "chaoscloud-customerapi/customerapi/user/captchaLogin"
    /**
     * 忘记密码修改密码
     */
    const val forgetPwd = "chaoscloud-customerapi/customerapi/user/forgetPwd"

    /**
     * 城市列表
     */
    const val cityList = "chaoscloud-customerapi/customerapi/city/list"

    /**
     * 城市公司列表
     */
    const val companyList = "chaoscloud-customerapi/customerapi/company/list"

    /**
     * 获取用户信息
     */
    const val getMemberInfo = "chaoscloud-customerapi/customerapi/user/getMemberInfo"
    /**
     * 微信登录获取openId
     */
    const val getToken = "chaoscloud-customerapi/api/weixinmp/weixin/getToken"
    /**
     * 微信登录后获取用户信息
     */
    const val getUserInfo = "chaoscloud-customerapi/api/weixinmp/weixin/getUserInfo"


    /**
     * 获取物联网表 信息
     */
    const val getDevice = "chaoscloud-customerapi/customerApi/app/device/getDevice"
    /**
     * 绑定物联网表
     */
    const val bindDevice = "chaoscloud-customerapi/customerApi/app/device/bind"
    /**
     * 绑定物联网表 列表
     */
    const val deviceList = "chaoscloud-customerapi/customerApi/app/device/list"

    /**
     * 解除绑定物联网表
     */
    const val unbind = "chaoscloud-customerapi/customerApi/app/device/unbind"


}
package cn.ygyg.cloudpayment.utils

import android.content.Context
import android.content.pm.PackageInfo
import cn.ygyg.cloudpayment.app.MyApplication

/**
 * Created by Admin on 2019/4/19.
 */
object WXUtil {
    fun isWXAppInstalled(context: Context): Boolean {
        val api = MyApplication.getApplication().mWxApi
        if (api.isWXAppInstalled) {
            return true
        } else {
            val packageManager = context.packageManager
            val pInfo: List<PackageInfo>? = packageManager.getInstalledPackages(0)
            pInfo?.forEach {
                if ("com.tencent.mm" == it.packageName) {
                    return true
                }
            }
            return false
        }
    }
}
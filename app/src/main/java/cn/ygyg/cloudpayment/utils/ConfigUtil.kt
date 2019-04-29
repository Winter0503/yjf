package cn.ygyg.cloudpayment.utils

import cn.ygyg.cloudpayment.app.Constants
import java.io.Serializable

object ConfigUtil {
    private var configEntity: ConfigEntity? = null

    fun saveConfig(configEntity: ConfigEntity) {
        SharePreUtil.saveBeanByFastJson(Constants.IntentKey.COMPANY_KEY, configEntity)
        this.configEntity = configEntity
    }

    fun getConfigEntity(): ConfigEntity? {
        return configEntity
    }

    private fun checkEmpty() {
        if (configEntity == null) {
            configEntity = SharePreUtil.getBeanByFastJson<ConfigEntity>(Constants.IntentKey.COMPANY_KEY)
        }
    }

    fun clear() {
        configEntity = null
    }

    data class ConfigEntity(
            var companyCode: String = ""
    ) : Serializable
}


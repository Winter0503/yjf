package cn.ygyg.cloudpayment.modular.internet.entity

import cn.ygyg.cloudpayment.modular.internet.vm.CompanyVM
import java.io.Serializable

data class CompanyListResponseEntity(
        val list: ArrayList<CompanyEntity>
) : Serializable {

    data class CompanyEntity(
            val cityCode: String,
            val cityId: Int,
            val cityName: String,
            val companyCode: String,
            val companyName: String,
            val createDate: String,
            val createUser: String,
            val deleted: Boolean,
            val enabled: Boolean,
            val groupCode: String,
            val groupId: Int,
            val groupName: String,
            val id: Long,
            val modifiedDate: String,
            val modifiedUser: String,
            val remark: String
    ) : CompanyVM, Serializable {
        override fun companyName(): String {
            return if (companyName.isEmpty()) "" else companyName
        }

        override fun companyId(): Long {
            return id
        }
    }
}


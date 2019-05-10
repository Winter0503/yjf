package cn.ygyg.cloudpayment.modular.payments.entity

import cn.ygyg.cloudpayment.modular.payments.vm.HistoryVM
import java.io.Serializable

class HistoryPageResponseEntity : Serializable {
    var isFirstPage = true
    var isLastPage = true
    var list: ArrayList<PageData>? = null

    class PageData : Serializable, HistoryVM {
        var allinoneTradeNo: String? = null
        var communityCode: String? = null
        var communityId: Int? = null
        var companyCode: String? = null
        var companyId: Int? = null
        var contractCode: String? = null
        var contractId: Int? = null
        var createDate: String? = null
        var createUser: String? = null
        var customerCode: String? = null
        var customerId: String? = null
        var customerMobile: String? = null
        var customerName: String? = null
        var customerTypeCode: String? = null
        var customerTypeId: Int? = null
        var customerTypeName: String? = null
        var departmentCode: String? = null
        var enabled: String? = null
        var hostName: String? = null
        var invoiceNo: String? = null
        var lastSum: Double? = null
        var meterCode: String? = null
        var meterId: Int? = null
        var meterType: String? = null
        var metersTypeCode: String? = null
        var metersTypeId: Int? = null
        var modifiedDate: String? = null
        var operationFrom: String? = null
        var operationFromCN: String? = null
        var orderCode: String? = null
        var parentId: String? = null
        var parentSum: Double? = null
        var paymentMethod: String? = null
        var paymentMethodCN: String? = null
        var paymentSeq: String? = null
        var paymentType: String? = null
        var paymentTypeCN: String? = null
        var premiseCode: String? = null
        var premiseName: String? = null
        var ptradeClass: String? = null
        var rechargeSeq: String? = null
        var rechargeTradeId: String? = null
        var rechargedSum: Double? = null
        var refundReason: String? = null
        var remark: String? = null
        var syncAllinone: Boolean? = null
        var thirdPartyPaymentSeq: String? = null
        var tradeClass: String? = null
        var tradeClassCN: String? = null
        var uid: Int? = null

        override fun userName(): String {
            return if (customerName.isNullOrEmpty()) "" else customerName!!
        }

        override fun payAmount(): String {
            return ""
        }

        override fun payState(): String {
            return if (paymentTypeCN.isNullOrEmpty()) "" else paymentTypeCN!!
        }

        override fun accountCode(): String {
            return if (meterCode.isNullOrEmpty()) "" else meterCode!!

        }

        override fun payMode(): String {
            return if (paymentMethodCN.isNullOrEmpty()) "" else paymentMethodCN!!
        }

        override fun payTime(): String {
            return ""
        }
    }
}

package cn.ygyg.cloudpayment.api

import android.util.Base64
import com.cn.lib.retrofit.network.entity.HttpParamEntity
import com.cn.lib.retrofit.network.interceptor.RequestParamInterceptor
import java.security.KeyFactory
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher

class ParamInterceptor : RequestParamInterceptor {

    private val publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAp72DvNhc6p5daTujEtvsh2w0++P8XJNgc/4yeL4JXjLy4Y1bXmGdn97ykQy0LbRoiKXqziNTS9nuCvj/Hs5sdxr03DZOpI/eHSty6/4HzS7PXZg8/9pQTSf081oJIAuE8zI5tMeMTTi1LLLR16APJB+5ckWCgUlGEenPrT83zbS+SMGa4tmV/3RUTHMNJsPr06BPJTWN3PAS2qy0QUwCV0BjaIWT2bmL2aYRd0OqnnSvS99Is+HZUjB98ik6m0m7YnFPWFwAUR2c7koc/wOrUfKvZE7P375/q7EQTLNnalkPvLM4ZpOthkMf8oxFgOLWycEtCNbmovW0eIuNmA+UcwIDAQAB"

    override fun intercept(params: HttpParamEntity): HttpParamEntity {
        if (!params.isParamsEmpty()) {
            val paramMap = params.paramMap
            for (mutableEntry in paramMap) {
                val value = mutableEntry.value
                val key = mutableEntry.key
                paramMap[key] = encrypt(value, publicKey)
            }
        }
        return params
    }

    /**
     * RSA公钥加密
     *
     * @param str
     *            加密字符串
     * @param publicKey
     *            公钥
     * @return 密文
     * @throws Exception
     *             加密过程中的异常信息
     */
    fun encrypt(str: String, publicKey: String): String {
        //base64编码的公钥
        val decoded = Base64.decode(publicKey, DEFAULT_BUFFER_SIZE)
        val pubKey = KeyFactory.getInstance("RSA").generatePublic(X509EncodedKeySpec(decoded))
        //RSA加密
        val cipher = Cipher.getInstance("RSA")
        cipher.init(Cipher.ENCRYPT_MODE, pubKey)
        return Base64.encodeToString(cipher.doFinal(str.toByteArray()), DEFAULT_BUFFER_SIZE)
    }

}
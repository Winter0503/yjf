package cn.ygyg.yjf.util

import android.content.Context
import android.text.TextUtils
import java.util.regex.Pattern

/**
 * Created by admin on 2017/12/15/015.
 */

object StringUtil {

    /**
     * 判断输入的是否是号码
     */
    fun isPhoneNumber(num: String): Boolean {
        var num = num
        if (TextUtils.isEmpty(num) || num.length < 3 || num.length > 11) {
            return false
        }
        num = formatNumber(num)
        if (!TextUtils.isEmpty(num)) {
            if (num.startsWith("1") && num.length == 11 && checkCellPhone(num)) {
                return true
            }
            if (!isOrder(num) && !isSame(num)) {
                return true
            }
        }
        return false
    }

    /**
     * 格式化号码
     */
    fun formatNumber(number: String): String {
        var number = number
        if (!TextUtils.isEmpty(number)) {
            try {
                if (number.startsWith("86")) {
                    number = number.substring(2)
                }
                if (number.startsWith("+86") || number.startsWith("086")) {
                    number = number.substring(3)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            number = number.replace(" ".toRegex(), "")
            number = number.replace("-".toRegex(), "")
            number = number.replace("_".toRegex(), "")
        } else {
            number = ""
        }
        return number
    }


    /**
     * 判断是否有顺序
     */
    private fun isOrder(str: String): Boolean {
        var orderStr = ""
        for (i in 33..126) {
            orderStr += Character.toChars(i)[0]
        }
        return if (!TextUtils.isEmpty(str) && !str.matches("(\\d)+".toRegex())) {
            false
        } else orderStr.contains(str)
    }

    /**
     * 判断是否相同
     */
    private fun isSame(str: String): Boolean {
        var regex = ""
        try {
            if (!TextUtils.isEmpty(str)) {
                regex = str.substring(0, 1) + "{" + str.length + "}"
                return str.matches(regex.toRegex())
            }
        } catch (e: Exception) {
            return false
        }

        return false
    }

    /**
     * 验证电话号码
     *
     * @param mobile
     * @return
     */
    fun checkCellPhone(mobile: String): Boolean {
        val regex = "(\\+\\d+)?1[345789]\\d{9}$"
        return Pattern.matches(regex, mobile)
    }


    /**
     * 判断字符串是不是全是中文
     *
     * @param str
     * @return
     */
    fun checkAllChinese(str: String): Boolean {
        val cTemp = str.toCharArray()
        for (i in 0 until str.length) {
            if (!isChinese(cTemp[i])) {
                return false
            }
        }
        return true
    }

    /**
     * 判断字符是不是汉字
     *
     * @param c
     * @return
     */
    fun isChinese(c: Char): Boolean {
        val ub = Character.UnicodeBlock.of(c)
        return ub === Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub === Character.UnicodeBlock
                .CJK_COMPATIBILITY_IDEOGRAPHS || ub === Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
    }

    /**
     * 根据string.xml资源格式化字符串
     *
     * @param context
     * @param resource
     * @param args
     * @return
     */
    fun formatResourceString(context: Context, resource: Int, vararg args: Any): String? {
        val str = context.resources.getString(resource)
        return if (TextUtils.isEmpty(str)) {
            null
        } else String.format(str, *args)
    }

    /**
     * 判断字符串是否是数字
     *
     * @param str
     * @return
     */
    fun isNumeric(str: String): Boolean {
        if (TextUtils.isEmpty(str) || str.length == 0) {
            return false
        }
        val pattern = Pattern.compile("[0-9]*")
        return pattern.matcher(str).matches()
    }

    /**
     * 字符串截取方法
     */
    fun subStringForName(arg: String, subLen: Int): String {
        val sb = StringBuffer()
        var len = 0
        var item: String? = null
        var i = 0
        while (i < arg.length && len < subLen) {
            item = arg.substring(i, i + 1)
            sb.append(item)
            if (Pattern.matches("([/d/D]*)", item)) {
                len = len + 2
            } else {
                len++
            }
            i++
        }
        return sb.toString() + if (arg.length > subLen) "..." else ""
    }

    /**
     * 将价格字符串是 [null] 的替换成 “0”
     *
     * @param str
     * @return
     */
    fun nullToEmpty(str: String): String {
        return if ("null" == str || TextUtils.isEmpty(str)) {
            "0.00"
        } else str
    }

    fun IsEmptyOrNullString(s: String?): Boolean {
        return s == null || s.trim { it <= ' ' }.length == 0
    }

    /**
     * 格式化小数最后无用的0，模板类使用
     * 例如输入1.010 则输出1.01
     */
    fun formatZeroAndDot(s: String): String {
        var s = s
        if (TextUtils.isEmpty(s)) {
            return ""
        }
        if (s.indexOf(".") > 0) {
            s = s.replace("0+?$".toRegex(), "")//去掉多余的0
            s = s.replace("[.]$".toRegex(), "")//如最后一位是.则去掉
        }
        return s
    }


}
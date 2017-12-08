package cn.imrhj.cowlevel.utils

/**
 * Created by rhj on 2017/12/8.
 */

object StringUtil {
    fun isEmpty(string: String?): Boolean {
        return string?.isEmpty() ?: true
    }

    fun isBlank(string: String?): Boolean {
        return string?.isBlank() ?: true
    }

    fun isNotEmpty(string: String?): Boolean {
        return !isEmpty(string)
    }

    fun isNotBlank(string: String?): Boolean {
        return !isBlank(string)
    }
}


package cn.imrhj.cowlevel.utils

/**
 * Created by rhj on 2017/12/8.
 */

object StringUtils {
    fun isEmpty(string: CharSequence?): Boolean {
        return string?.isEmpty() ?: true
    }

    fun isBlank(string: CharSequence?): Boolean {
        return string?.isBlank() ?: true
    }

    fun isNotEmpty(string: CharSequence?): Boolean {
        return !isEmpty(string)
    }

    fun isNotBlank(string: CharSequence?): Boolean {
        return !isBlank(string)
    }
}


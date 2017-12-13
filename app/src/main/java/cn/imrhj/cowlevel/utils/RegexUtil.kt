package cn.imrhj.cowlevel.utils

import java.util.regex.Pattern

/**
 * Created by rhj on 12/12/2017.
 */
object RegexUtil {

    private val REGEX_EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$"

    fun isMail(mail: CharSequence?): Boolean {
        return isMatch(REGEX_EMAIL, mail)
    }

    fun isMatch(regex: String, input: CharSequence?): Boolean {
        return input != null && input.isNotBlank() && Pattern.matches(regex, input)
    }
}
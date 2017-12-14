package cn.imrhj.cowlevel.utils

/**
 * Created by rhj on 2017/12/9.
 */
object CollectionUtils {
    fun <E> isEmpty(list: Collection<E>?): Boolean {
        return list?.isEmpty() ?: true
    }

    fun <E> isNotEmpty(list: Collection<E>?): Boolean {
        return !isEmpty(list)
    }

}
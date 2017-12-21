package cn.imrhj.cowlevel.consts

/**
 * Created by rhj on 21/12/2017.
 */

object CowLinks {
    private val URL_BASE_SHARE_LINK = "https://cowlevel.net/sharelink/"
    fun getShareLink(id: Int?): String {
        return URL_BASE_SHARE_LINK + id
    }
}

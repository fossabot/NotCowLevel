package cn.imrhj.cowlevel.network.model

/**
 * Created by rhj on 2017/12/7.
 */
data class MergedTagModel(
        val id: Int? = 0,
        val name: String? = null,
        val pic: String? = null,
        val content: String? = null,
        val is_follow: Int? = 0,
        val has_collect: Int? = 0
)

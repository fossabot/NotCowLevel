package cn.imrhj.cowlevel.network.model.feed

import cn.imrhj.cowlevel.network.model.BaseModel

/**
 * Created by rhj on 2017/12/4.
 * name : 云海冲浪人
 * avatar : https://pic1.cdncl.net/user/avatar/cdab5b9d4a4255674acb31b57aa7e3ee.jpg
 * intro : 基督徒，日本创业中，游戏开发者。
 * url_slug : acaciaforjesus
 * is_follow : 0
 * is_follow_by : 0
 * is_follow_both : 0
 * is_block : 0
 */
data class VotersModel(
        val name: String? = null,
        val avatar: String? = null,
        val intro: String? = null,
        val url_slug: String? = null,
        val is_follow: Int = 0,
        val is_follow_by: Int = 0,
        val is_follow_both: Int = 0,
        val is_block: Int = 0
) : BaseModel()
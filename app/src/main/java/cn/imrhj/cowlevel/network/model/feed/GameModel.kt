package cn.imrhj.cowlevel.network.model.feed

import cn.imrhj.cowlevel.network.model.BaseModel
import cn.imrhj.cowlevel.network.model.common.ContentModel
import cn.imrhj.cowlevel.network.model.TagModel

/**
 * Created by rhj on 2017/12/4.
 * url_slug : Starcraft_II_Legacy_of_The_Void
 * title : 星际争霸2 虚空之遗 tarcraft II：Legacy of The Void
 * comment_count : 18
 * pic : https://pic1.cdncl.net/game/user_upload/741/06ab363baec24394e05cedbc21d181db.jpg
 * discover : https://pic1.cdncl.net/game/833/0d0bfa103b51a913b2c3485073d518f9.jpg
 * is_follow : 0
 * has_collect : 0
 * content :
 *
 *暴雪RTS大作星际争霸2最后一部资料片。15.11.10已发布，在这部资料片里你将知晓星灵和萨尔那加的的命运，以及见证这部延续十八年的科幻史诗大剧的最终结局。
 * neat_content : {"desc":"暴雪RTS大作星际争霸2最后一部资料片。15.11.10已发布，在这部资料片里你将知晓星灵和萨尔那加的的命运，以及见证这部延续十八年的科幻史诗大剧的最终结局。","thumb":""}
 * platform_support_list : [{"id":183,"name":"Windows"}]
 * game_publish_date_show : 2015年11月10日
 * game_publish_date_show_pre : 发行于
 * tags : [{"id":58,"name":"即时战略","pic":"https://pic1.cdncl.net/user/yoge/common_pic/a2cdd0ef2589ed4c7033909afcd5e7d8.png"},{"id":31,"name":"策略","pic":"https://pic1.cdncl.net/tag/31/809d83971b884a444e3b1d97fd8b60a8.png"}]
 * has_played : 0
 * chinese_title : 星际争霸2 虚空之遗
 */
data class GameModel(
        val url_slug: String? = null,
        val title: String? = null,
        val comment_count: Int = 0,
        val pic: String? = null,
        val discover: String? = null,
        val is_follow: Int = 0,
        val has_collect: Int = 0,
        val content: String? = null,
        val neat_content: ContentModel? = null,
        val game_publish_date_show: String? = null,
        val game_publish_date_show_pre: String? = null,
        val has_played: Int = 0,
        val chinese_title: String? = null,
        val platform_support_list: List<PlatformSupportModel>? = null,
        val tags: List<TagModel>? = null
) : BaseModel()
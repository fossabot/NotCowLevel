package cn.imrhj.cowlevel.network.model

import cn.imrhj.cowlevel.consts.ItemTypeEnum

/**
 * Created by rhj on 2017/12/4.
 */

data class UserModel(
        val name: String? = null,
        val url_slug: String? = null,
        val avatar: String? = null,
        val cover: String? = null,
        val intro: String? = null,
        val level: Int = 0,
        val following_count: Int = 0,
        val follower_count: Int = 0,
        val recommend_count: Int = 0,
        val wish_count: Int = 0,
        val played_count: Int = 0,
        val comment_count: Int = 0,
        val vote_count: Int = 0,
        val comment_voted_count: Int = 0,
        val answer_voted_count: Int = 0,
        val photo_voted_count: Int = 0,
        val article_voted_count: Int = 0,
        val video_voted_count: Int = 0,
        val question_count: Int = 0,
        val answer_count: Int = 0,
        val article_count: Int = 0,
        val user_bio: String? = null,
        val invite_code_total: Int = 0,
        val invite_code_remain: Int = 0,
        val has_apply_code: Int = 0,
        val is_follow: Int = 0,
        val is_follow_by: Int = 0,
        val is_follow_both: Int = 0,
        val is_block: Int = 0,
        val is_pro: Int = 0,
        val total_voted_count: Int = 0,
        val pro_games: List<*>? = null,
        val invite_code_count: Int = 0,
        val draft_list_count: Int = 0,
        val followed_question_count: Int = 0,
        val followed_tag_count: Int = 0,
        val followed_game_count: Int = 0,
        val collected_game_count: Int = 0,
        val created_game_count: Int = 0,
        val bind_status: BindStatus? = null,
        val is_curator: Int = 0,
        val is_developer: Int = 0,
        val video_count: Int = 0,
        val inbox_count: Int = 0,
        var token: String? = null,
        val collection_count: Int = 0,
        val user_site: UserSiteModel? = null,
        val image_count: Int = 0,
        val tag_count: Int = 0,
        val feed_count: Int = 0
) : BaseModel() {
    override fun getType(): ItemTypeEnum {
        return ItemTypeEnum.TYPE_USER
    }
}

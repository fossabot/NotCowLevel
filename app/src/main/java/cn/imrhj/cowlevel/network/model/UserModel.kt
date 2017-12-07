package cn.imrhj.cowlevel.network.model

/**
 * Created by rhj on 2017/12/4.
 * name : 南瓜
 * url_slug : blogprofile
 * avatar : https://pic1.cdncl.net/user/blogprofile/common_pic/6b0bcb9d94f87b87ec942e34f95c9e2f.jpg
 * cover : https://pic1.cdncl.net/user/blogprofile/common_pic/b4771fa8932fbace458c336dcd198d77.jpg
 * intro : 励志学好英语
 * level : 1
 * following_count : 171
 * follower_count : 37
 * recommend_count : 1
 * played_count : 12
 * comment_count : 12
 * vote_count : 1
 * comment_voted_count : 15
 * answer_voted_count : 18
 * photo_voted_count : 0
 * article_voted_count : 0
 * question_count : 4
 * answer_count : 23
 * article_count : 0
 * user_bio :
 *
 *近期想要自己做一款游戏试试看，开始策划！
 * invite_code_total : 1
 * invite_code_remain : 0
 * is_follow : 0
 * is_follow_by : 0
 * is_follow_both : 0
 * pro_games : []
 * is_pro : 0
 * total_voted_count : 33
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
        val played_count: Int = 0,
        val comment_count: Int = 0,
        val vote_count: Int = 0,
        val comment_voted_count: Int = 0,
        val answer_voted_count: Int = 0,
        val photo_voted_count: Int = 0,
        val article_voted_count: Int = 0,
        val question_count: Int = 0,
        val answer_count: Int = 0,
        val article_count: Int = 0,
        val user_bio: String? = null,
        val invite_code_total: Int = 0,
        val invite_code_remain: Int = 0,
        val is_follow: Int = 0,
        val is_follow_by: Int = 0,
        val is_follow_both: Int = 0,
        val is_block: Int = 0,
        val is_pro: Int = 0,
        val total_voted_count: Int = 0,
        val pro_games: List<*>? = null
)
